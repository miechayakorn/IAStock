package ia.servlet;

import ia.jpa.model.History;
import ia.jpa.model.Items;
import ia.jpa.model.Years;
import ia.jpa.model.controller.HistoryJpaController;
import ia.jpa.model.controller.ItemsJpaController;
import ia.jpa.model.controller.YearsJpaController;
import ia.jpa.model.controller.exceptions.NonexistentEntityException;
import ia.jpa.model.controller.exceptions.RollbackFailureException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

public class StockAddServlet extends HttpServlet {

    @PersistenceUnit(unitName = "IAStockPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessionYear = request.getSession(false);
        if (sessionYear == null) {
            response.sendRedirect("Home");
            return;
        } else if (sessionYear.getAttribute("year") == null) {
            response.sendRedirect("Home");
            return;
        }
        String add = request.getParameter("add");
        String id = request.getParameter("id");
        String annotation = request.getParameter("annotation");

        YearsJpaController yearJpa = new YearsJpaController(utx, emf);
        Years yearSearch = yearJpa.findYears((String) sessionYear.getAttribute("year"));
        List<Items> itemsYear = yearSearch.getItemsList();

        if (add != null && id != null) {
            ItemsJpaController itemJpa = new ItemsJpaController(utx, emf);
            HistoryJpaController historyJpa = new HistoryJpaController(utx, emf);

            for (Items items : itemsYear) {
                if (items.getItemid().equals(id)) {
                    int addInt = Integer.parseInt(add);
                    int itemTotal = items.getItemtotal() + addInt;
                    items.setItemtotal(itemTotal);
                    try {
                        itemJpa.edit(items);
                        History history = new History(items, items.getPrice(), addInt, "add", new Date(), yearSearch);
                        if (annotation != null) {
                            history.setAnnotation(annotation);
                        }
                        historyJpa.create(history);
                        System.out.println("-----------------------------------Success");
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(StockAddServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(StockAddServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(StockAddServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    response.sendRedirect("Stock_add");
                    return;
                }
            }
        }
        request.setAttribute("items", itemsYear);
        getServletContext().getRequestDispatcher("/Stock_add.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
