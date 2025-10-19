<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 16/10/2025
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Connexion</title></head>
<body>
<h2>Connexion</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="auth" method="post">
    <input type="hidden" name="action" value="login"/>
    <label>Email:</label><br/>
    <input type="email" name="email" required/><br/><br/>
    <label>Mot de passe:</label><br/>
    <input type="password" name="motDePasse" required/><br/><br/>
    <input type="submit" value="Se connecter"/>
</form>

<p>Pas de compte ? <a href="auth?action=register">Créer un compte</a></p>
</body>
</html>

