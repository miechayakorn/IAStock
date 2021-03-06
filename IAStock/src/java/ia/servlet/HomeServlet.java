package ia.servlet;

import ia.jpa.model.Years;
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

public class HomeServlet extends HttpServlet {

    @PersistenceUnit(unitName = "IAStockPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            request.getSession(true);
        } else {
            if (session.getAttribute("year") != null) {
                response.sendRedirect("Dashboard");
                return;
            }
        }

        String year = request.getParameter("year");
        YearsJpaController yearJPA = new YearsJpaController(utx, emf);
        List<Years> yearTotals = yearJPA.findYearsEntities();

        request.setAttribute("yearTotals", yearTotals);

        if (year != null) {
            session.setAttribute("year", year);
            response.sendRedirect("Dashboard");
            return;
        }
        String[] uris = request.getRequestURI().split("/");
        String uri = "/" + uris[2];
        request.setAttribute("parameter", uri);

        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
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
