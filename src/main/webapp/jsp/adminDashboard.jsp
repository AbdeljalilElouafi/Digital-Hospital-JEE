<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 17/10/2025
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Tableau de bord Admin</title></head>
<body>
<h2>Bienvenue, Administrateur ${sessionScope.userConnecte.nom}</h2>

<nav>
    <a href="admin?action=departements">Gérer Départements</a> |
    <a href="admin?action=docteurs">Gérer Docteurs</a> |
    <a href="admin?action=salles">Gérer Salles</a> |
    <a href="admin?action=logout">Déconnexion</a>
</nav>

<h3>Supervision des Consultations</h3>

<c:if test="${empty consultations}">
    <p>Aucune consultation trouvée.</p>
</c:if>

<c:forEach var="c" items="${consultations}">
    <div>
        <strong>${c.date} ${c.heure}</strong><br/>
        Patient: ${c.patient.nom} ${c.patient.prenom}<br/>
        Docteur: Dr. ${c.docteur.nom} (${c.docteur.specialite})<br/>
        Statut: ${c.statut}<br/>
        <hr/>
    </div>
</c:forEach>
</body>
</html>

