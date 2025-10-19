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

        // ----- DEPARTEMENTS -----
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

        } else if ("updateDepartement".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(request.getParameter("idDepartement"));
            String nom = request.getParameter("nom");
            Departement dep = departementService.findById(id);
            if (dep != null) {
                try {
                    departementService.modifierDepartement(dep.getIdDepartement(), nom);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            response.sendRedirect("admin?action=departements");

        } else if ("deleteDepartement".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(request.getParameter("idDepartement"));
            departementService.supprimerDepartement(id);
            response.sendRedirect("admin?action=departements");

            // ----- DOCTEURS -----
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

        } else if ("updateDocteur".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(request.getParameter("idDocteur"));
            Docteur docteur = docteurService.findById(id);
            if (docteur != null) {
                docteur.setNom(request.getParameter("nom"));
                docteur.setPrenom(request.getParameter("prenom"));
                docteur.setEmail(request.getParameter("email"));
                docteur.setSpecialite(request.getParameter("specialite"));
                Long depId = Long.parseLong(request.getParameter("departementId"));
                docteur.setDepartement(departementService.findById(depId));
                docteurService.update(docteur);
            }
            response.sendRedirect("admin?action=docteurs");

        } else if ("deleteDocteur".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(request.getParameter("idDocteur"));
            docteurService.delete(id);
            response.sendRedirect("admin?action=docteurs");

            // ----- SALLES -----
        } else if ("addSalle".equalsIgnoreCase(action)) {
            String nomSalle = request.getParameter("nomSalle");
            Integer capacite = Integer.parseInt(request.getParameter("capacite"));
            Long depId = Long.parseLong(request.getParameter("departementId"));
            Departement dep = departementService.findById(depId);

            Salle salle = new Salle();
            try {
                salleService.ajouterSalle(nomSalle, capacite, dep);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            response.sendRedirect("admin?action=salles");

        } else if ("updateSalle".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(request.getParameter("idSalle"));
            Salle salle = salleService.findById(id);
            if (salle != null) {
                String nomSalle = request.getParameter("nomSalle");
                Integer capacite = Integer.valueOf(request.getParameter("capacite"));
                Long depId = Long.parseLong(request.getParameter("departementId"));
                salle.setDepartement(departementService.findById(depId));
                try {
                    salleService.modifierSalle(id, nomSalle, capacite);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            response.sendRedirect("admin?action=salles");

        } else if ("deleteSalle".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(request.getParameter("idSalle"));
            salleService.supprimerSalle(id);
            response.sendRedirect("admin?action=salles");
        }
    }

}
