<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 17/10/2025
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Départements</title></head>
<body>
<h2>Gestion des Départements</h2>

<form action="admin" method="post">
    <input type="hidden" name="action" value="addDepartement"/>
    <label>Nom du département:</label>
    <input type="text" name="nom" required/>
    <input type="submit" value="Ajouter"/>
</form>

<h3>Liste des départements</h3>
<c:forEach var="d" items="${departements}">
    <p>${d.nom}</p>
</c:forEach>

<c:forEach var="d" items="${departements}">
    <form action="admin" method="post" style="margin-bottom: 10px;">
        <input type="hidden" name="idDepartement" value="${d.idDepartement}"/>
        <input type="text" name="nom" value="${d.nom}" required/>
        <button type="submit" name="action" value="updateDepartement">Modifier</button>
        <button type="submit" name="action" value="deleteDepartement">Supprimer</button>
    </form>
</c:forEach>

<p><a href="admin">← Retour Dashboard</a></p>
</body>
</html>

