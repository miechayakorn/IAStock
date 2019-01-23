/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.jpa.model.controller;

import ia.jpa.model.Categorys;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ia.jpa.model.Items;
import ia.jpa.model.Years;
import ia.jpa.model.controller.exceptions.NonexistentEntityException;
import ia.jpa.model.controller.exceptions.PreexistingEntityException;
import ia.jpa.model.controller.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author User
 */
public class CategorysJpaController implements Serializable {

    public CategorysJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categorys categorys) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Items itemid = categorys.getItemid();
            if (itemid != null) {
                itemid = em.getReference(itemid.getClass(), itemid.getItemid());
                categorys.setItemid(itemid);
            }
            Years yearstock = categorys.getYearstock();
            if (yearstock != null) {
                yearstock = em.getReference(yearstock.getClass(), yearstock.getYearstock());
                categorys.setYearstock(yearstock);
            }
            em.persist(categorys);
            if (itemid != null) {
                itemid.getCategorysList().add(categorys);
                itemid = em.merge(itemid);
            }
            if (yearstock != null) {
                yearstock.getCategorysList().add(categorys);
                yearstock = em.merge(yearstock);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCategorys(categorys.getCategory()) != null) {
                throw new PreexistingEntityException("Categorys " + categorys + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categorys categorys) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categorys persistentCategorys = em.find(Categorys.class, categorys.getCategory());
            Items itemidOld = persistentCategorys.getItemid();
            Items itemidNew = categorys.getItemid();
            Years yearstockOld = persistentCategorys.getYearstock();
            Years yearstockNew = categorys.getYearstock();
            if (itemidNew != null) {
                itemidNew = em.getReference(itemidNew.getClass(), itemidNew.getItemid());
                categorys.setItemid(itemidNew);
            }
            if (yearstockNew != null) {
                yearstockNew = em.getReference(yearstockNew.getClass(), yearstockNew.getYearstock());
                categorys.setYearstock(yearstockNew);
            }
            categorys = em.merge(categorys);
            if (itemidOld != null && !itemidOld.equals(itemidNew)) {
                itemidOld.getCategorysList().remove(categorys);
                itemidOld = em.merge(itemidOld);
            }
            if (itemidNew != null && !itemidNew.equals(itemidOld)) {
                itemidNew.getCategorysList().add(categorys);
                itemidNew = em.merge(itemidNew);
            }
            if (yearstockOld != null && !yearstockOld.equals(yearstockNew)) {
                yearstockOld.getCategorysList().remove(categorys);
                yearstockOld = em.merge(yearstockOld);
            }
            if (yearstockNew != null && !yearstockNew.equals(yearstockOld)) {
                yearstockNew.getCategorysList().add(categorys);
                yearstockNew = em.merge(yearstockNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = categorys.getCategory();
                if (findCategorys(id) == null) {
                    throw new NonexistentEntityException("The categorys with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categorys categorys;
            try {
                categorys = em.getReference(Categorys.class, id);
                categorys.getCategory();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categorys with id " + id + " no longer exists.", enfe);
            }
            Items itemid = categorys.getItemid();
            if (itemid != null) {
                itemid.getCategorysList().remove(categorys);
                itemid = em.merge(itemid);
            }
            Years yearstock = categorys.getYearstock();
            if (yearstock != null) {
                yearstock.getCategorysList().remove(categorys);
                yearstock = em.merge(yearstock);
            }
            em.remove(categorys);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categorys> findCategorysEntities() {
        return findCategorysEntities(true, -1, -1);
    }

    public List<Categorys> findCategorysEntities(int maxResults, int firstResult) {
        return findCategorysEntities(false, maxResults, firstResult);
    }

    private List<Categorys> findCategorysEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categorys.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Categorys findCategorys(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categorys.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategorysCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categorys> rt = cq.from(Categorys.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
