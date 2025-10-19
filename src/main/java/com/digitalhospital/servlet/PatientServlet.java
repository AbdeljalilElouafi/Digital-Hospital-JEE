package com.digitalhospital.servlet;

import com.digitalhospital.model.*;
import com.digitalhospital.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/patient")
public class PatientServlet extends HttpServlet {
    private final ConsultationService consultationService = new ConsultationService();
    private final DocteurService docteurService = new DocteurService();
    private final DepartementService departementService = new DepartementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Personne user = (Personne) session.getAttribute("userConnecte");

        if (user == null || !(user instanceof Patient)) {
            response.sendRedirect("auth?action=login");
            return;
        }

        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            // show dashboard
            List<Consultation> consultations = consultationService.findByPatient((Patient) user);
            request.setAttribute("consultations", consultations);
            request.getRequestDispatcher("/jsp/patientDashboard.jsp").forward(request, response);
        } else if ("reserver".equalsIgnoreCase(action)) {
            List<Departement> departements = departementService.findAll();
            request.setAttribute("departements", departements);
            request.getRequestDispatcher("/jsp/reservation.jsp").forward(request, response);
        } else if ("logout".equalsIgnoreCase(action)) {
            session.invalidate();
            response.sendRedirect("auth?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Patient patient = (Patient) session.getAttribute("userConnecte");

        if (patient == null) {
            response.sendRedirect("auth?action=login");
            return;
        }

        String action = request.getParameter("action");

        if ("reserver".equalsIgnoreCase(action)) {
            Long docteurId = Long.parseLong(request.getParameter("docteurId"));
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            LocalTime heure = LocalTime.parse(request.getParameter("heure"));

            try {
                consultationService.reserverConsultation(patient.getIdPersonne(), docteurId, date, heure);
                response.sendRedirect("patient");
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                List<Departement> departements = departementService.findAll();
                request.setAttribute("departements", departements);
                request.getRequestDispatcher("/jsp/reservation.jsp").forward(request, response);
            }
        } else if ("annuler".equalsIgnoreCase(action)) {
            Long consultationId = Long.parseLong(request.getParameter("id"));
            consultationService.annulerConsultation(consultationId);
            response.sendRedirect("patient");
        }
    }
}
