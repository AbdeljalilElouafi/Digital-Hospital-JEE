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
<head><title>Docteurs</title></head>
<body>
<h2>Gestion des Docteurs</h2>

<form action="admin" method="post">
    <input type="hidden" name="action" value="addDocteur"/>

    <label>Nom:</label><input type="text" name="nom" required/><br/>
    <label>Prénom:</label><input type="text" name="prenom" required/><br/>
    <label>Email:</label><input type="email" name="email" required/><br/>
    <label>Mot de passe:</label><input type="password" name="motDePasse" required/><br/>
    <label>Spécialité:</label><input type="text" name="specialite" required/><br/>

    <label>Département:</label>
    <select name="departementId" required>
        <c:forEach var="d" items="${departements}">
            <option value="${d.idDepartement}">${d.nom}</option>
        </c:forEach>
    </select><br/><br/>

    <input type="submit" value="Ajouter"/>
</form>

<h3>Liste des docteurs</h3>
<c:forEach var="doc" items="${docteurs}">
    <div>
        Dr. ${doc.nom} ${doc.prenom} (${doc.specialite}) - ${doc.departement.nom}
    </div>
</c:forEach>

<p><a href="admin">← Retour Dashboard</a></p>
</body>
</html>

