/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.jpa.model.controller;

import ia.jpa.model.Categorys;
import ia.jpa.model.CategorysPK;
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
 * @author Acer_E5
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
        if (categorys.getCategorysPK() == null) {
            categorys.setCategorysPK(new CategorysPK());
        }
        categorys.getCategorysPK().setItemid(categorys.getItems().getItemid());
        categorys.getCategorysPK().setYearstock(categorys.getYears().getYearstock());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Items items = categorys.getItems();
            if (items != null) {
                items = em.getReference(items.getClass(), items.getItemid());
                categorys.setItems(items);
            }
            Years years = categorys.getYears();
            if (years != null) {
                years = em.getReference(years.getClass(), years.getYearstock());
                categorys.setYears(years);
            }
            em.persist(categorys);
            if (items != null) {
                items.getCategorysList().add(categorys);
                items = em.merge(items);
            }
            if (years != null) {
                years.getCategorysList().add(categorys);
                years = em.merge(years);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCategorys(categorys.getCategorysPK()) != null) {
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
        categorys.getCategorysPK().setItemid(categorys.getItems().getItemid());
        categorys.getCategorysPK().setYearstock(categorys.getYears().getYearstock());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categorys persistentCategorys = em.find(Categorys.class, categorys.getCategorysPK());
            Items itemsOld = persistentCategorys.getItems();
            Items itemsNew = categorys.getItems();
            Years yearsOld = persistentCategorys.getYears();
            Years yearsNew = categorys.getYears();
            if (itemsNew != null) {
                itemsNew = em.getReference(itemsNew.getClass(), itemsNew.getItemid());
                categorys.setItems(itemsNew);
            }
            if (yearsNew != null) {
                yearsNew = em.getReference(yearsNew.getClass(), yearsNew.getYearstock());
                categorys.setYears(yearsNew);
            }
            categorys = em.merge(categorys);
            if (itemsOld != null && !itemsOld.equals(itemsNew)) {
                itemsOld.getCategorysList().remove(categorys);
                itemsOld = em.merge(itemsOld);
            }
            if (itemsNew != null && !itemsNew.equals(itemsOld)) {
                itemsNew.getCategorysList().add(categorys);
                itemsNew = em.merge(itemsNew);
            }
            if (yearsOld != null && !yearsOld.equals(yearsNew)) {
                yearsOld.getCategorysList().remove(categorys);
                yearsOld = em.merge(yearsOld);
            }
            if (yearsNew != null && !yearsNew.equals(yearsOld)) {
                yearsNew.getCategorysList().add(categorys);
                yearsNew = em.merge(yearsNew);
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
                CategorysPK id = categorys.getCategorysPK();
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

    public void destroy(CategorysPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categorys categorys;
            try {
                categorys = em.getReference(Categorys.class, id);
                categorys.getCategorysPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categorys with id " + id + " no longer exists.", enfe);
            }
            Items items = categorys.getItems();
            if (items != null) {
                items.getCategorysList().remove(categorys);
                items = em.merge(items);
            }
            Years years = categorys.getYears();
            if (years != null) {
                years.getCategorysList().remove(categorys);
                years = em.merge(years);
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

    public Categorys findCategorys(CategorysPK id) {
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
