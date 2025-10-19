package com.digitalhospital.servlet;

import com.digitalhospital.model.*;
import com.digitalhospital.service.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final DepartementService departementService = new DepartementService();
    private final DocteurService docteurService = new DocteurService();
    private final SalleService salleService = new SalleService();
    private final ConsultationService consultationService = new ConsultationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Personne user = (Personne) session.getAttribute("userConnecte");

        if (user == null || !(user instanceof Admin)) {
            response.sendRedirect("auth?action=login");
            return;
        }

        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            // Dashboard: list consultations
            List<Consultation> consultations = consultationService.findAll();
            request.setAttribute("consultations", consultations);
            request.getRequestDispatcher("/jsp/adminDashboard.jsp").forward(request, response);

        } else if ("departements".equalsIgnoreCase(action)) {
            List<Departement> deps = departementService.findAll();
            request.setAttribute("departements", deps);
            request.getRequestDispatcher("/jsp/departements.jsp").forward(request, response);

        } else if ("docteurs".equalsIgnoreCase(action)) {
            List<Docteur> docs = docteurService.findAll();
            List<Departement> deps = departementService.findAll();
            request.setAttribute("docteurs", docs);
            request.setAttribute("departements", deps);
            request.getRequestDispatcher("/jsp/docteurs.jsp").forward(request, response);

        } else if ("salles".equalsIgnoreCase(action)) {
            List<Salle> salles = salleService.listerToutesLesSalles();
            List<Departement> deps = departementService.findAll();
            request.setAttribute("salles", salles);
            request.setAttribute("departements", deps);
            request.getRequestDispatcher("/jsp/salles.jsp").forward(request, response);

        } else if ("logout".equalsIgnoreCase(action)) {
            session.invalidate();
            response.sendRedirect("auth?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("addDepartement".equalsIgnoreCase(action)) {
            String nom = request.getParameter("nom");
            Departement dep = new Departement();
            dep.setNom(nom);
            try {
                departementService.ajouterDepartement(dep.getNom());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            response.sendRedirect("admin?action=departements");

        } else if ("addDocteur".equalsIgnoreCase(action)) {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String email = request.getParameter("email");
            String mdp = request.getParameter("motDePasse");
            String specialite = request.getParameter("specialite");
            Long depId = Long.parseLong(request.getParameter("departementId"));

            Departement dep = departementService.findById(depId);
            Docteur docteur = new Docteur();
            docteur.setNom(nom);
            docteur.setPrenom(prenom);
            docteur.setEmail(email);
            docteur.setMotDePasse(mdp);
            docteur.setSpecialite(specialite);
            docteur.setDepartement(dep);

            docteurService.save(docteur);
            response.sendRedirect("admin?action=docteurs");

        } else if ("addSalle".equalsIgnoreCase(action)) {
            String nomSalle = request.getParameter("nomSalle");
            Integer capacite = Integer.parseInt(request.getParameter("capacite"));
            Long depId = Long.parseLong(request.getParameter("departementId"));

            Departement dep = departementService.findById(depId);
            Salle salle = new Salle();
            salle.setNomSalle(nomSalle);
            salle.setCapacite(capacite);
            salle.setDepartement(dep);

            try {
                salleService.ajouterSalle(salle.getNomSalle(), salle.getCapacite(), salle.getDepartement());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            response.sendRedirect("admin?action=salles");
        }
    }
}
