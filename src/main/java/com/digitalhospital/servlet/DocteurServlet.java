package com.digitalhospital.servlet;

import com.digitalhospital.model.*;
import com.digitalhospital.service.ConsultationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/docteur")
public class DocteurServlet extends HttpServlet {
    private final ConsultationService consultationService = new ConsultationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Personne user = (Personne) session.getAttribute("userConnecte");

        if (user == null || !(user instanceof Docteur)) {
            response.sendRedirect("auth?action=login");
            return;
        }

        Docteur docteur = (Docteur) user;
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            // Show planning
            List<Consultation> consultations = consultationService.findByDocteur(docteur);
            request.setAttribute("consultations", consultations);
            request.getRequestDispatcher("/jsp/docteurDashboard.jsp").forward(request, response);

        } else if ("compteRendu".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            Consultation consultation = consultationService.findById(id);
            request.setAttribute("consultation", consultation);
            request.getRequestDispatcher("/jsp/compteRendu.jsp").forward(request, response);

        } else if ("logout".equalsIgnoreCase(action)) {
            session.invalidate();
            response.sendRedirect("auth?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Docteur docteur = (Docteur) session.getAttribute("userConnecte");

        if (docteur == null) {
            response.sendRedirect("auth?action=login");
            return;
        }

        String action = request.getParameter("action");

        if ("valider".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            consultationService.validerConsultation(id);
            response.sendRedirect("docteur");

        } else if ("refuser".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            consultationService.refuserConsultation(id);
            response.sendRedirect("docteur");

        } else if ("compteRendu".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            String cr = request.getParameter("compteRendu");
            consultationService.ajouterCompteRendu(id, cr);
            response.sendRedirect("docteur");
        }
    }
}
