/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.jpa.model.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ia.jpa.model.Items;
import ia.jpa.model.Summarize;
import ia.jpa.model.SummarizePK;
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
public class SummarizeJpaController implements Serializable {

    public SummarizeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Summarize summarize) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (summarize.getSummarizePK() == null) {
            summarize.setSummarizePK(new SummarizePK());
        }
        summarize.getSummarizePK().setYearstock(summarize.getYears().getYearstock());
        summarize.getSummarizePK().setItemid(summarize.getItems().getItemid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Items items = summarize.getItems();
            if (items != null) {
                items = em.getReference(items.getClass(), items.getItemid());
                summarize.setItems(items);
            }
            Years years = summarize.getYears();
            if (years != null) {
                years = em.getReference(years.getClass(), years.getYearstock());
                summarize.setYears(years);
            }
            em.persist(summarize);
            if (items != null) {
                items.getSummarizeList().add(summarize);
                items = em.merge(items);
            }
            if (years != null) {
                years.getSummarizeList().add(summarize);
                years = em.merge(years);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSummarize(summarize.getSummarizePK()) != null) {
                throw new PreexistingEntityException("Summarize " + summarize + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Summarize summarize) throws NonexistentEntityException, RollbackFailureException, Exception {
        summarize.getSummarizePK().setYearstock(summarize.getYears().getYearstock());
        summarize.getSummarizePK().setItemid(summarize.getItems().getItemid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Summarize persistentSummarize = em.find(Summarize.class, summarize.getSummarizePK());
            Items itemsOld = persistentSummarize.getItems();
            Items itemsNew = summarize.getItems();
            Years yearsOld = persistentSummarize.getYears();
            Years yearsNew = summarize.getYears();
            if (itemsNew != null) {
                itemsNew = em.getReference(itemsNew.getClass(), itemsNew.getItemid());
                summarize.setItems(itemsNew);
            }
            if (yearsNew != null) {
                yearsNew = em.getReference(yearsNew.getClass(), yearsNew.getYearstock());
                summarize.setYears(yearsNew);
            }
            summarize = em.merge(summarize);
            if (itemsOld != null && !itemsOld.equals(itemsNew)) {
                itemsOld.getSummarizeList().remove(summarize);
                itemsOld = em.merge(itemsOld);
            }
            if (itemsNew != null && !itemsNew.equals(itemsOld)) {
                itemsNew.getSummarizeList().add(summarize);
                itemsNew = em.merge(itemsNew);
            }
            if (yearsOld != null && !yearsOld.equals(yearsNew)) {
                yearsOld.getSummarizeList().remove(summarize);
                yearsOld = em.merge(yearsOld);
            }
            if (yearsNew != null && !yearsNew.equals(yearsOld)) {
                yearsNew.getSummarizeList().add(summarize);
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
                SummarizePK id = summarize.getSummarizePK();
                if (findSummarize(id) == null) {
                    throw new NonexistentEntityException("The summarize with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SummarizePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Summarize summarize;
            try {
                summarize = em.getReference(Summarize.class, id);
                summarize.getSummarizePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The summarize with id " + id + " no longer exists.", enfe);
            }
            Items items = summarize.getItems();
            if (items != null) {
                items.getSummarizeList().remove(summarize);
                items = em.merge(items);
            }
            Years years = summarize.getYears();
            if (years != null) {
                years.getSummarizeList().remove(summarize);
                years = em.merge(years);
            }
            em.remove(summarize);
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

    public List<Summarize> findSummarizeEntities() {
        return findSummarizeEntities(true, -1, -1);
    }

    public List<Summarize> findSummarizeEntities(int maxResults, int firstResult) {
        return findSummarizeEntities(false, maxResults, firstResult);
    }

    private List<Summarize> findSummarizeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Summarize.class));
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

    public Summarize findSummarize(SummarizePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Summarize.class, id);
        } finally {
            em.close();
        }
    }

    public int getSummarizeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Summarize> rt = cq.from(Summarize.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
