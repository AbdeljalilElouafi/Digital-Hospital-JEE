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

        else if ("getDocteursByDepartement".equalsIgnoreCase(action)) {
            String depIdStr = request.getParameter("departementId");
            if (depIdStr == null || depIdStr.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing departementId");
                return;
            }

            Long depId = Long.parseLong(depIdStr);
            List<Docteur> docteurs = docteurService.findByDepartement(depId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < docteurs.size(); i++) {
                Docteur d = docteurs.get(i);
                json.append(String.format("{\"id\":%d,\"nom\":\"%s\",\"prenom\":\"%s\"}",
                        d.getIdPersonne(), d.getNom(), d.getPrenom()));
                if (i < docteurs.size() - 1) json.append(",");
            }
            json.append("]");
            response.getWriter().write(json.toString());
            return;
        } else if ("getDisponibilitesByDocteur".equalsIgnoreCase(action)) {
            String docIdStr = request.getParameter("docteurId");
            if (docIdStr == null || docIdStr.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing docteurId");
                return;
            }

            Long docteurId = Long.parseLong(docIdStr);
            List<String> dispos = consultationService.getDisponibilitesByDocteur(docteurId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < dispos.size(); i++) {
                json.append("\"").append(dispos.get(i)).append("\"");
                if (i < dispos.size() - 1) json.append(",");
            }
            json.append("]");
            response.getWriter().write(json.toString());
            return;
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
