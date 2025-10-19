<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 16/10/2025
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Inscription Patient</title></head>
<body>
<h2>Créer un compte patient</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="auth" method="post">
    <input type="hidden" name="action" value="register"/>
    <label>Nom:</label><br/>
    <input type="text" name="nom" required/><br/>
    <label>Prénom:</label><br/>
    <input type="text" name="prenom" required/><br/>
    <label>Email:</label><br/>
    <input type="email" name="email" required/><br/>
    <label>Mot de passe:</label><br/>
    <input type="password" name="motDePasse" required/><br/>
    <label>Poids (kg):</label><br/>
    <input type="number" step="0.1" name="poids"/><br/>
    <label>Taille (m):</label><br/>
    <input type="number" step="0.01" name="taille"/><br/><br/>
    <input type="submit" value="Créer le compte"/>
</form>

<p>Déjà inscrit ? <a href="auth?action=login">Se connecter</a></p>
</body>
</html>

