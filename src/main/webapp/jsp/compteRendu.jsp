<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 17/10/2025
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Compte rendu consultation</title></head>
<body>
<h2>Rédiger un compte rendu</h2>

<form action="docteur" method="post">
    <input type="hidden" name="action" value="compteRendu"/>
    <input type="hidden" name="id" value="${consultation.idConsultation}"/>

    <p>
        <strong>Patient:</strong> ${consultation.patient.nom} ${consultation.patient.prenom}<br/>
        <strong>Date:</strong> ${consultation.date} à ${consultation.heure}
    </p>

    <label>Compte rendu:</label><br/>
    <textarea name="compteRendu" rows="6" cols="50" required></textarea><br/><br/>

    <input type="submit" value="Enregistrer"/>
</form>

<p><a href="docteur">← Retour au planning</a></p>
</body>
</html>

