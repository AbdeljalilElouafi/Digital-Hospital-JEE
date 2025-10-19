<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 17/10/2025
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Espace Docteur</title></head>
<body>
<h2>Bonjour Dr. ${sessionScope.userConnecte.nom} ${sessionScope.userConnecte.prenom}</h2>

<p><a href="docteur?action=logout">Se déconnecter</a></p>
<h3>Planning des consultations</h3>

<c:if test="${empty consultations}">
    <p>Aucune consultation prévue.</p>
</c:if>

<c:forEach var="c" items="${consultations}">
    <div style="margin-bottom: 10px;">
        <p>
            <strong>Patient:</strong> ${c.patient.nom} ${c.patient.prenom}<br/>
            <strong>Date:</strong> ${c.date} à ${c.heure}<br/>
            <strong>Statut:</strong> ${c.statut}<br/>
        </p>

        <c:choose>
            <c:when test="${c.statut == 'RESERVEE'}">
                <form action="docteur" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="valider"/>
                    <input type="hidden" name="id" value="${c.idConsultation}"/>
                    <input type="submit" value="Valider"/>
                </form>

                <form action="docteur" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="refuser"/>
                    <input type="hidden" name="id" value="${c.idConsultation}"/>
                    <input type="submit" value="Refuser"/>
                </form>
            </c:when>

            <c:when test="${c.statut == 'VALIDEE'}">
                <a href="docteur?action=compteRendu&id=${c.idConsultation}">Ajouter un compte rendu</a>
            </c:when>

            <c:when test="${c.statut == 'TERMINEE'}">
                <p><strong>Compte rendu:</strong> ${c.compteRendu}</p>
            </c:when>

            <c:otherwise>
                <em>Consultation annulée.</em>
            </c:otherwise>
        </c:choose>
    </div>
    <hr/>
</c:forEach>
</body>
</html>

