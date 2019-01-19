package ia.servlet;

import ia.jpa.model.History;
import ia.jpa.model.Items;
import ia.jpa.model.Years;
import ia.jpa.model.controller.HistoryJpaController;
import ia.jpa.model.controller.ItemsJpaController;
import ia.jpa.model.controller.YearsJpaController;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author MieChayakorn
 */
public class PrintStockServlet extends HttpServlet {

    @PersistenceUnit(unitName = "IAStockPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HistoryJpaController historyJpa = new HistoryJpaController(utx, emf);
        YearsJpaController yearJpa = new YearsJpaController(utx, emf);
        ItemsJpaController itemJpa = new ItemsJpaController(utx, emf);
        
        HttpSession session = request.getSession(false);

        
        
        Years yearSearch = yearJpa.findYears((String) session.getAttribute("year"));
        List<Items> itemYears = yearSearch.getItemsList();
        for (Items itemYear : itemYears) {
            System.out.println(historyJpa.findSumQuantity(itemYear, "add", yearSearch));
        }
        
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
