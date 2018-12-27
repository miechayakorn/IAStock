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
import java.util.ArrayList;
import java.util.List;
import ia.jpa.model.History;
import ia.jpa.model.Years;
import ia.jpa.model.controller.exceptions.IllegalOrphanException;
import ia.jpa.model.controller.exceptions.NonexistentEntityException;
import ia.jpa.model.controller.exceptions.PreexistingEntityException;
import ia.jpa.model.controller.exceptions.RollbackFailureException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Acer_E5
 */
public class YearsJpaController implements Serializable {

    public YearsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Years years) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (years.getItemsList() == null) {
            years.setItemsList(new ArrayList<Items>());
        }
        if (years.getHistoryList() == null) {
            years.setHistoryList(new ArrayList<History>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Items> attachedItemsList = new ArrayList<Items>();
            for (Items itemsListItemsToAttach : years.getItemsList()) {
                itemsListItemsToAttach = em.getReference(itemsListItemsToAttach.getClass(), itemsListItemsToAttach.getItemid());
                attachedItemsList.add(itemsListItemsToAttach);
            }
            years.setItemsList(attachedItemsList);
            List<History> attachedHistoryList = new ArrayList<History>();
            for (History historyListHistoryToAttach : years.getHistoryList()) {
                historyListHistoryToAttach = em.getReference(historyListHistoryToAttach.getClass(), historyListHistoryToAttach.getHistoryid());
                attachedHistoryList.add(historyListHistoryToAttach);
            }
            years.setHistoryList(attachedHistoryList);
            em.persist(years);
            for (Items itemsListItems : years.getItemsList()) {
                Years oldYearstockOfItemsListItems = itemsListItems.getYearstock();
                itemsListItems.setYearstock(years);
                itemsListItems = em.merge(itemsListItems);
                if (oldYearstockOfItemsListItems != null) {
                    oldYearstockOfItemsListItems.getItemsList().remove(itemsListItems);
                    oldYearstockOfItemsListItems = em.merge(oldYearstockOfItemsListItems);
                }
            }
            for (History historyListHistory : years.getHistoryList()) {
                Years oldYearstockOfHistoryListHistory = historyListHistory.getYearstock();
                historyListHistory.setYearstock(years);
                historyListHistory = em.merge(historyListHistory);
                if (oldYearstockOfHistoryListHistory != null) {
                    oldYearstockOfHistoryListHistory.getHistoryList().remove(historyListHistory);
                    oldYearstockOfHistoryListHistory = em.merge(oldYearstockOfHistoryListHistory);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findYears(years.getYearstock()) != null) {
                throw new PreexistingEntityException("Years " + years + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Years years) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Years persistentYears = em.find(Years.class, years.getYearstock());
            List<Items> itemsListOld = persistentYears.getItemsList();
            List<Items> itemsListNew = years.getItemsList();
            List<History> historyListOld = persistentYears.getHistoryList();
            List<History> historyListNew = years.getHistoryList();
            List<String> illegalOrphanMessages = null;
            for (Items itemsListOldItems : itemsListOld) {
                if (!itemsListNew.contains(itemsListOldItems)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Items " + itemsListOldItems + " since its yearstock field is not nullable.");
                }
            }
            for (History historyListOldHistory : historyListOld) {
                if (!historyListNew.contains(historyListOldHistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain History " + historyListOldHistory + " since its yearstock field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Items> attachedItemsListNew = new ArrayList<Items>();
            for (Items itemsListNewItemsToAttach : itemsListNew) {
                itemsListNewItemsToAttach = em.getReference(itemsListNewItemsToAttach.getClass(), itemsListNewItemsToAttach.getItemid());
                attachedItemsListNew.add(itemsListNewItemsToAttach);
            }
            itemsListNew = attachedItemsListNew;
            years.setItemsList(itemsListNew);
            List<History> attachedHistoryListNew = new ArrayList<History>();
            for (History historyListNewHistoryToAttach : historyListNew) {
                historyListNewHistoryToAttach = em.getReference(historyListNewHistoryToAttach.getClass(), historyListNewHistoryToAttach.getHistoryid());
                attachedHistoryListNew.add(historyListNewHistoryToAttach);
            }
            historyListNew = attachedHistoryListNew;
            years.setHistoryList(historyListNew);
            years = em.merge(years);
            for (Items itemsListNewItems : itemsListNew) {
                if (!itemsListOld.contains(itemsListNewItems)) {
                    Years oldYearstockOfItemsListNewItems = itemsListNewItems.getYearstock();
                    itemsListNewItems.setYearstock(years);
                    itemsListNewItems = em.merge(itemsListNewItems);
                    if (oldYearstockOfItemsListNewItems != null && !oldYearstockOfItemsListNewItems.equals(years)) {
                        oldYearstockOfItemsListNewItems.getItemsList().remove(itemsListNewItems);
                        oldYearstockOfItemsListNewItems = em.merge(oldYearstockOfItemsListNewItems);
                    }
                }
            }
            for (History historyListNewHistory : historyListNew) {
                if (!historyListOld.contains(historyListNewHistory)) {
                    Years oldYearstockOfHistoryListNewHistory = historyListNewHistory.getYearstock();
                    historyListNewHistory.setYearstock(years);
                    historyListNewHistory = em.merge(historyListNewHistory);
                    if (oldYearstockOfHistoryListNewHistory != null && !oldYearstockOfHistoryListNewHistory.equals(years)) {
                        oldYearstockOfHistoryListNewHistory.getHistoryList().remove(historyListNewHistory);
                        oldYearstockOfHistoryListNewHistory = em.merge(oldYearstockOfHistoryListNewHistory);
                    }
                }
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
                String id = years.getYearstock();
                if (findYears(id) == null) {
                    throw new NonexistentEntityException("The years with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Years years;
            try {
                years = em.getReference(Years.class, id);
                years.getYearstock();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The years with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Items> itemsListOrphanCheck = years.getItemsList();
            for (Items itemsListOrphanCheckItems : itemsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Years (" + years + ") cannot be destroyed since the Items " + itemsListOrphanCheckItems + " in its itemsList field has a non-nullable yearstock field.");
            }
            List<History> historyListOrphanCheck = years.getHistoryList();
            for (History historyListOrphanCheckHistory : historyListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Years (" + years + ") cannot be destroyed since the History " + historyListOrphanCheckHistory + " in its historyList field has a non-nullable yearstock field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(years);
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

    public List<Years> findYearsEntities() {
        return findYearsEntities(true, -1, -1);
    }

    public List<Years> findYearsEntities(int maxResults, int firstResult) {
        return findYearsEntities(false, maxResults, firstResult);
    }

    private List<Years> findYearsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Years.class));
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

    public Years findYears(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Years.class, id);
        } finally {
            em.close();
        }
    }

    public int getYearsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Years> rt = cq.from(Years.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
