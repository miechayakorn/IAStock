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
import ia.jpa.model.Years;
import ia.jpa.model.Summarize;
import java.util.ArrayList;
import java.util.List;
import ia.jpa.model.Categorys;
import ia.jpa.model.History;
import ia.jpa.model.Items;
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
public class ItemsJpaController implements Serializable {

    public ItemsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Items items) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (items.getSummarizeList() == null) {
            items.setSummarizeList(new ArrayList<Summarize>());
        }
        if (items.getCategorysList() == null) {
            items.setCategorysList(new ArrayList<Categorys>());
        }
        if (items.getHistoryList() == null) {
            items.setHistoryList(new ArrayList<History>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Years yearstock = items.getYearstock();
            if (yearstock != null) {
                yearstock = em.getReference(yearstock.getClass(), yearstock.getYearstock());
                items.setYearstock(yearstock);
            }
            List<Summarize> attachedSummarizeList = new ArrayList<Summarize>();
            for (Summarize summarizeListSummarizeToAttach : items.getSummarizeList()) {
                summarizeListSummarizeToAttach = em.getReference(summarizeListSummarizeToAttach.getClass(), summarizeListSummarizeToAttach.getSummarizePK());
                attachedSummarizeList.add(summarizeListSummarizeToAttach);
            }
            items.setSummarizeList(attachedSummarizeList);
            List<Categorys> attachedCategorysList = new ArrayList<Categorys>();
            for (Categorys categorysListCategorysToAttach : items.getCategorysList()) {
                categorysListCategorysToAttach = em.getReference(categorysListCategorysToAttach.getClass(), categorysListCategorysToAttach.getCategorysPK());
                attachedCategorysList.add(categorysListCategorysToAttach);
            }
            items.setCategorysList(attachedCategorysList);
            List<History> attachedHistoryList = new ArrayList<History>();
            for (History historyListHistoryToAttach : items.getHistoryList()) {
                historyListHistoryToAttach = em.getReference(historyListHistoryToAttach.getClass(), historyListHistoryToAttach.getHistoryid());
                attachedHistoryList.add(historyListHistoryToAttach);
            }
            items.setHistoryList(attachedHistoryList);
            em.persist(items);
            if (yearstock != null) {
                yearstock.getItemsList().add(items);
                yearstock = em.merge(yearstock);
            }
            for (Summarize summarizeListSummarize : items.getSummarizeList()) {
                Items oldItemsOfSummarizeListSummarize = summarizeListSummarize.getItems();
                summarizeListSummarize.setItems(items);
                summarizeListSummarize = em.merge(summarizeListSummarize);
                if (oldItemsOfSummarizeListSummarize != null) {
                    oldItemsOfSummarizeListSummarize.getSummarizeList().remove(summarizeListSummarize);
                    oldItemsOfSummarizeListSummarize = em.merge(oldItemsOfSummarizeListSummarize);
                }
            }
            for (Categorys categorysListCategorys : items.getCategorysList()) {
                Items oldItemsOfCategorysListCategorys = categorysListCategorys.getItems();
                categorysListCategorys.setItems(items);
                categorysListCategorys = em.merge(categorysListCategorys);
                if (oldItemsOfCategorysListCategorys != null) {
                    oldItemsOfCategorysListCategorys.getCategorysList().remove(categorysListCategorys);
                    oldItemsOfCategorysListCategorys = em.merge(oldItemsOfCategorysListCategorys);
                }
            }
            for (History historyListHistory : items.getHistoryList()) {
                Items oldItemidOfHistoryListHistory = historyListHistory.getItemid();
                historyListHistory.setItemid(items);
                historyListHistory = em.merge(historyListHistory);
                if (oldItemidOfHistoryListHistory != null) {
                    oldItemidOfHistoryListHistory.getHistoryList().remove(historyListHistory);
                    oldItemidOfHistoryListHistory = em.merge(oldItemidOfHistoryListHistory);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findItems(items.getItemid()) != null) {
                throw new PreexistingEntityException("Items " + items + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Items items) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Items persistentItems = em.find(Items.class, items.getItemid());
            Years yearstockOld = persistentItems.getYearstock();
            Years yearstockNew = items.getYearstock();
            List<Summarize> summarizeListOld = persistentItems.getSummarizeList();
            List<Summarize> summarizeListNew = items.getSummarizeList();
            List<Categorys> categorysListOld = persistentItems.getCategorysList();
            List<Categorys> categorysListNew = items.getCategorysList();
            List<History> historyListOld = persistentItems.getHistoryList();
            List<History> historyListNew = items.getHistoryList();
            List<String> illegalOrphanMessages = null;
            for (Summarize summarizeListOldSummarize : summarizeListOld) {
                if (!summarizeListNew.contains(summarizeListOldSummarize)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Summarize " + summarizeListOldSummarize + " since its items field is not nullable.");
                }
            }
            for (Categorys categorysListOldCategorys : categorysListOld) {
                if (!categorysListNew.contains(categorysListOldCategorys)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Categorys " + categorysListOldCategorys + " since its items field is not nullable.");
                }
            }
            for (History historyListOldHistory : historyListOld) {
                if (!historyListNew.contains(historyListOldHistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain History " + historyListOldHistory + " since its itemid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (yearstockNew != null) {
                yearstockNew = em.getReference(yearstockNew.getClass(), yearstockNew.getYearstock());
                items.setYearstock(yearstockNew);
            }
            List<Summarize> attachedSummarizeListNew = new ArrayList<Summarize>();
            for (Summarize summarizeListNewSummarizeToAttach : summarizeListNew) {
                summarizeListNewSummarizeToAttach = em.getReference(summarizeListNewSummarizeToAttach.getClass(), summarizeListNewSummarizeToAttach.getSummarizePK());
                attachedSummarizeListNew.add(summarizeListNewSummarizeToAttach);
            }
            summarizeListNew = attachedSummarizeListNew;
            items.setSummarizeList(summarizeListNew);
            List<Categorys> attachedCategorysListNew = new ArrayList<Categorys>();
            for (Categorys categorysListNewCategorysToAttach : categorysListNew) {
                categorysListNewCategorysToAttach = em.getReference(categorysListNewCategorysToAttach.getClass(), categorysListNewCategorysToAttach.getCategorysPK());
                attachedCategorysListNew.add(categorysListNewCategorysToAttach);
            }
            categorysListNew = attachedCategorysListNew;
            items.setCategorysList(categorysListNew);
            List<History> attachedHistoryListNew = new ArrayList<History>();
            for (History historyListNewHistoryToAttach : historyListNew) {
                historyListNewHistoryToAttach = em.getReference(historyListNewHistoryToAttach.getClass(), historyListNewHistoryToAttach.getHistoryid());
                attachedHistoryListNew.add(historyListNewHistoryToAttach);
            }
            historyListNew = attachedHistoryListNew;
            items.setHistoryList(historyListNew);
            items = em.merge(items);
            if (yearstockOld != null && !yearstockOld.equals(yearstockNew)) {
                yearstockOld.getItemsList().remove(items);
                yearstockOld = em.merge(yearstockOld);
            }
            if (yearstockNew != null && !yearstockNew.equals(yearstockOld)) {
                yearstockNew.getItemsList().add(items);
                yearstockNew = em.merge(yearstockNew);
            }
            for (Summarize summarizeListNewSummarize : summarizeListNew) {
                if (!summarizeListOld.contains(summarizeListNewSummarize)) {
                    Items oldItemsOfSummarizeListNewSummarize = summarizeListNewSummarize.getItems();
                    summarizeListNewSummarize.setItems(items);
                    summarizeListNewSummarize = em.merge(summarizeListNewSummarize);
                    if (oldItemsOfSummarizeListNewSummarize != null && !oldItemsOfSummarizeListNewSummarize.equals(items)) {
                        oldItemsOfSummarizeListNewSummarize.getSummarizeList().remove(summarizeListNewSummarize);
                        oldItemsOfSummarizeListNewSummarize = em.merge(oldItemsOfSummarizeListNewSummarize);
                    }
                }
            }
            for (Categorys categorysListNewCategorys : categorysListNew) {
                if (!categorysListOld.contains(categorysListNewCategorys)) {
                    Items oldItemsOfCategorysListNewCategorys = categorysListNewCategorys.getItems();
                    categorysListNewCategorys.setItems(items);
                    categorysListNewCategorys = em.merge(categorysListNewCategorys);
                    if (oldItemsOfCategorysListNewCategorys != null && !oldItemsOfCategorysListNewCategorys.equals(items)) {
                        oldItemsOfCategorysListNewCategorys.getCategorysList().remove(categorysListNewCategorys);
                        oldItemsOfCategorysListNewCategorys = em.merge(oldItemsOfCategorysListNewCategorys);
                    }
                }
            }
            for (History historyListNewHistory : historyListNew) {
                if (!historyListOld.contains(historyListNewHistory)) {
                    Items oldItemidOfHistoryListNewHistory = historyListNewHistory.getItemid();
                    historyListNewHistory.setItemid(items);
                    historyListNewHistory = em.merge(historyListNewHistory);
                    if (oldItemidOfHistoryListNewHistory != null && !oldItemidOfHistoryListNewHistory.equals(items)) {
                        oldItemidOfHistoryListNewHistory.getHistoryList().remove(historyListNewHistory);
                        oldItemidOfHistoryListNewHistory = em.merge(oldItemidOfHistoryListNewHistory);
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
                String id = items.getItemid();
                if (findItems(id) == null) {
                    throw new NonexistentEntityException("The items with id " + id + " no longer exists.");
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
            Items items;
            try {
                items = em.getReference(Items.class, id);
                items.getItemid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The items with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Summarize> summarizeListOrphanCheck = items.getSummarizeList();
            for (Summarize summarizeListOrphanCheckSummarize : summarizeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Items (" + items + ") cannot be destroyed since the Summarize " + summarizeListOrphanCheckSummarize + " in its summarizeList field has a non-nullable items field.");
            }
            List<Categorys> categorysListOrphanCheck = items.getCategorysList();
            for (Categorys categorysListOrphanCheckCategorys : categorysListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Items (" + items + ") cannot be destroyed since the Categorys " + categorysListOrphanCheckCategorys + " in its categorysList field has a non-nullable items field.");
            }
            List<History> historyListOrphanCheck = items.getHistoryList();
            for (History historyListOrphanCheckHistory : historyListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Items (" + items + ") cannot be destroyed since the History " + historyListOrphanCheckHistory + " in its historyList field has a non-nullable itemid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Years yearstock = items.getYearstock();
            if (yearstock != null) {
                yearstock.getItemsList().remove(items);
                yearstock = em.merge(yearstock);
            }
            em.remove(items);
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

    public List<Items> findItemsEntities() {
        return findItemsEntities(true, -1, -1);
    }

    public List<Items> findItemsEntities(int maxResults, int firstResult) {
        return findItemsEntities(false, maxResults, firstResult);
    }

    private List<Items> findItemsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Items.class));
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

    public Items findItems(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Items.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Items> rt = cq.from(Items.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
