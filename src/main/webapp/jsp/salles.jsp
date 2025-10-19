<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 17/10/2025
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Salles</title></head>
<body>
<h2>Gestion des Salles</h2>

<form action="admin" method="post">
    <input type="hidden" name="action" value="addSalle"/>
    <label>Nom Salle:</label><input type="text" name="nomSalle" required/><br/>
    <label>Capacité:</label><input type="number" name="capacite" required/><br/>

    <label>Département:</label>
    <select name="departementId" required>
        <c:forEach var="d" items="${departements}">
            <option value="${d.idDepartement}">${d.nom}</option>
        </c:forEach>
    </select><br/><br/>

    <input type="submit" value="Ajouter"/>
</form>

<h3>Liste des salles</h3>
<c:forEach var="s" items="${salles}">
    <div>${s.nomSalle} (capacité: ${s.capacite}) - ${s.departement.nom}</div>
</c:forEach>


<c:forEach var="s" items="${salles}">
    <form action="admin" method="post" style="margin-bottom: 10px;">
        <input type="hidden" name="idSalle" value="${s.idSalle}"/>

        <input type="text" name="nomSalle" value="${s.nomSalle}" required/>
        <input type="number" name="capacite" value="${s.capacite}" required/>

        <select name="departementId" required>
            <c:forEach var="d" items="${departements}">
                <option value="${d.idDepartement}" <c:if test="${d.idDepartement == s.departement.idDepartement}">selected</c:if>>${d.nom}</option>
            </c:forEach>
        </select>

        <button type="submit" name="action" value="updateSalle">Modifier</button>
        <button type="submit" name="action" value="deleteSalle">Supprimer</button>
    </form>
</c:forEach>


<p><a href="admin">← Retour Dashboard</a></p>
</body>
</html>

