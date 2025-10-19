<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 16/10/2025
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Espace Patient</title></head>
<body>
<h2>Bienvenue, ${sessionScope.userConnecte.prenom} ${sessionScope.userConnecte.nom}</h2>

<p><a href="patient?action=reserver">Réserver une consultation</a></p>
<p><a href="patient?action=logout">Se déconnecter</a></p>

<h3>Historique des consultations</h3>

<c:if test="${empty consultations}">
    <p>Aucune consultation trouvée.</p>
</c:if>

<c:forEach var="c" items="${consultations}">
    <div style="margin-bottom: 10px;">
        <p>
            <strong>Date:</strong> ${c.date} à ${c.heure}<br/>
            <strong>Docteur:</strong> ${c.docteur.nom} ${c.docteur.prenom}<br/>
            <strong>Statut:</strong> ${c.statut}<br/>
            <c:if test="${c.compteRendu != null}">
                <strong>Compte rendu:</strong> ${c.compteRendu}<br/>
            </c:if>
        </p>

        <c:if test="${c.statut == 'RESERVEE'}">
            <form action="patient" method="post" style="display:inline;">
                <input type="hidden" name="action" value="annuler"/>
                <input type="hidden" name="id" value="${c.idConsultation}"/>
                <input type="submit" value="Annuler"/>
            </form>
        </c:if>
    </div>
    <hr/>
</c:forEach>
</body>
</html>

