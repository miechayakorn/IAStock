/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.jpa.model.controller;

import ia.jpa.model.History;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ia.jpa.model.Items;
import ia.jpa.model.Years;
import ia.jpa.model.controller.exceptions.NonexistentEntityException;
import ia.jpa.model.controller.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Acer_E5
 */
public class HistoryJpaController implements Serializable {

    public HistoryJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(History history) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Items itemid = history.getItemid();
            if (itemid != null) {
                itemid = em.getReference(itemid.getClass(), itemid.getItemid());
                history.setItemid(itemid);
            }
            Years yearstock = history.getYearstock();
            if (yearstock != null) {
                yearstock = em.getReference(yearstock.getClass(), yearstock.getYearstock());
                history.setYearstock(yearstock);
            }
            em.persist(history);
            if (itemid != null) {
                itemid.getHistoryList().add(history);
                itemid = em.merge(itemid);
            }
            if (yearstock != null) {
                yearstock.getHistoryList().add(history);
                yearstock = em.merge(yearstock);
            }
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

    public void edit(History history) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            History persistentHistory = em.find(History.class, history.getHistoryid());
            Items itemidOld = persistentHistory.getItemid();
            Items itemidNew = history.getItemid();
            Years yearstockOld = persistentHistory.getYearstock();
            Years yearstockNew = history.getYearstock();
            if (itemidNew != null) {
                itemidNew = em.getReference(itemidNew.getClass(), itemidNew.getItemid());
                history.setItemid(itemidNew);
            }
            if (yearstockNew != null) {
                yearstockNew = em.getReference(yearstockNew.getClass(), yearstockNew.getYearstock());
                history.setYearstock(yearstockNew);
            }
            history = em.merge(history);
            if (itemidOld != null && !itemidOld.equals(itemidNew)) {
                itemidOld.getHistoryList().remove(history);
                itemidOld = em.merge(itemidOld);
            }
            if (itemidNew != null && !itemidNew.equals(itemidOld)) {
                itemidNew.getHistoryList().add(history);
                itemidNew = em.merge(itemidNew);
            }
            if (yearstockOld != null && !yearstockOld.equals(yearstockNew)) {
                yearstockOld.getHistoryList().remove(history);
                yearstockOld = em.merge(yearstockOld);
            }
            if (yearstockNew != null && !yearstockNew.equals(yearstockOld)) {
                yearstockNew.getHistoryList().add(history);
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
                Integer id = history.getHistoryid();
                if (findHistory(id) == null) {
                    throw new NonexistentEntityException("The history with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            History history;
            try {
                history = em.getReference(History.class, id);
                history.getHistoryid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The history with id " + id + " no longer exists.", enfe);
            }
            Items itemid = history.getItemid();
            if (itemid != null) {
                itemid.getHistoryList().remove(history);
                itemid = em.merge(itemid);
            }
            Years yearstock = history.getYearstock();
            if (yearstock != null) {
                yearstock.getHistoryList().remove(history);
                yearstock = em.merge(yearstock);
            }
            em.remove(history);
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

    public List<History> findHistoryEntities() {
        return findHistoryEntities(true, -1, -1);
    }

    public List<History> findHistoryEntities(int maxResults, int firstResult) {
        return findHistoryEntities(false, maxResults, firstResult);
    }

    private List<History> findHistoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(History.class));
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

    public History findHistory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(History.class, id);
        } finally {
            em.close();
        }
    }

    public Long findSumQuantity(Items itemid, String type, Years year) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("History.findSumQuantity");
            query.setParameter("itemid", itemid);
            query.setParameter("type", type);
            query.setParameter("year", year);
            return (Long) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public int getHistoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<History> rt = cq.from(History.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
