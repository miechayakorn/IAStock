package ia.servlet;

import ia.jpa.model.Categorys;
import ia.jpa.model.Items;
import ia.jpa.model.Summarize;
import ia.jpa.model.SummarizePK;
import ia.jpa.model.Years;
import ia.jpa.model.controller.CategorysJpaController;
import ia.jpa.model.controller.ItemsJpaController;
import ia.jpa.model.controller.SummarizeJpaController;
import ia.jpa.model.controller.YearsJpaController;
import ia.jpa.model.controller.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.PrintWriter;
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

public class InsertServlet extends HttpServlet {

    @PersistenceUnit(unitName = "IAStockPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String itemId = request.getParameter("itemId");
        String itemName = request.getParameter("itemName");
        String price = request.getParameter("price");
        String unit = request.getParameter("unit");
        String categoryParam = request.getParameter("category");

        HttpSession session = request.getSession(false);

        if (itemId != null) {
            ItemsJpaController itemJpa = new ItemsJpaController(utx, emf);
            CategorysJpaController categoryJpa = new CategorysJpaController(utx, emf);
            SummarizeJpaController summarizeJpa = new SummarizeJpaController(utx, emf);
            YearsJpaController yearJpa = new YearsJpaController(utx, emf);

            Years yearSession = yearJpa.findYears((String) session.getAttribute("year"));
            Items item = new Items(itemId, itemName, Double.parseDouble(price), unit, yearSession);
            
            try {
                itemJpa.create(item);
                Categorys category = new Categorys(categoryParam, itemId, (String) session.getAttribute("year"));
                categoryJpa.create(category);
                Summarize summarize = new Summarize(itemId, (String) session.getAttribute("year"), 0, 0, 0);
                summarizeJpa.create(summarize);

            } catch (RollbackFailureException ex) {
                Logger.getLogger(InsertServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(InsertServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        getServletContext().getRequestDispatcher("/Insert.jsp").forward(request, response);

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
