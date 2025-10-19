package com.digitalhospital.servlet;

import com.digitalhospital.model.*;
import com.digitalhospital.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equalsIgnoreCase(action)) {
            handleLogin(request, response);
        } else if ("register".equalsIgnoreCase(action)) {
            handleRegister(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        try {
            Personne user = authService.login(email, motDePasse);
            HttpSession session = request.getSession();
            session.setAttribute("userConnecte", user);

            if (user instanceof Admin) {
                response.sendRedirect("admin");
            } else if (user instanceof Docteur) {
                response.sendRedirect("docteur");
            } else {
                response.sendRedirect("patient");
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        Double poids = Double.valueOf(request.getParameter("poids"));
        Double taille = Double.valueOf(request.getParameter("taille"));

        try {
            authService.registerPatient(nom, prenom, email, motDePasse, poids, taille);
            response.sendRedirect("auth?action=login");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
        }
    }
}
