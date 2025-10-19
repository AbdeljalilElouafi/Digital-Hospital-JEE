<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 16/10/2025
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Réserver une Consultation</title></head>
<body>
<h2>Réserver une Consultation</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="patient" method="post">
    <input type="hidden" name="action" value="reserver"/>

    <label for="departement">Département:</label><br/>
    <select id="departement" name="departementId" required>
        <option value="">-- Choisir --</option>
        <c:forEach var="d" items="${departements}">
            <option value="${d.idDepartement}">${d.nom}</option>
        </c:forEach>
    </select><br/><br/>

    <label>Docteur (ID):</label><br/>
    <input type="number" name="docteurId" required/><br/><br/>

    <label>Date:</label><br/>
    <input type="date" name="date" required/><br/><br/>

    <label>Heure:</label><br/>
    <input type="time" name="heure" required/><br/><br/>

    <input type="submit" value="Réserver"/>
</form>

<p><a href="patient">← Retour</a></p>
</body>
</html>
