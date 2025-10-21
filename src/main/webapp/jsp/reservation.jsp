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
<head>
    <title>Réserver une Consultation</title>
    <script>
        function loadDocteurs(depId) {
            depId = depId.trim();
            console.log("Selected departement ID:", depId);

            if (!depId) {
                console.warn("Aucun département sélectionné");
                return;
            }

            const docteurSelect = document.getElementById("docteur");
            docteurSelect.innerHTML = "<option>Chargement...</option>";

            fetch(`patient?action=getDocteursByDepartement&departementId=\${encodeURIComponent(depId)}`)

                    .then(res => {
                    if (!res.ok) throw new Error("Erreur HTTP " + res.status);
                    return res.json();
                })
                .then(data => {
                    console.log("Doctors JSON:", data);
                    data.forEach(d => {
                        console.log("Doctor item:", d);
                        docteurSelect.innerHTML += "<option value='" + d.id + "'>Dr. " + d.prenom + " " + d.nom + "</option>";
                    });
                })

                    .catch(err => {
                    console.error(err);
                    docteurSelect.innerHTML = "<option>Aucun docteur trouvé</option>";
                });
        }



        function loadDisponibilites() {
            const docteurId = document.getElementById("docteur").value.trim();
            if (!docteurId) {
                console.warn("Aucun docteur sélectionné");
                return;
            }

            console.log("Selected docteur ID:", docteurId);

            const dateSelect = document.getElementById("date");
            const timeSelect = document.getElementById("heure");
            dateSelect.innerHTML = "";
            timeSelect.innerHTML = "";

            fetch("patient?action=getDisponibilitesByDocteur&docteurId=" + encodeURIComponent(docteurId))
                .then(res => {
                    if (!res.ok) throw new Error("Erreur HTTP " + res.status);
                    return res.json();
                })
                .then(data => {
                    console.log("Disponibilités received:", data);
                    const dates = [...new Set(data.map(d => d.split(" ")[0]))];
                    dateSelect.innerHTML = "<option value=''>-- Choisir une date --</option>";
                    dates.forEach(d => {
                        dateSelect.innerHTML += "<option value='" + d + "'>" + d + "</option>";
                    });

                    dateSelect.onchange = () => {
                        const selectedDate = dateSelect.value;
                        const heures = data
                            .filter(s => s.startsWith(selectedDate))
                            .map(s => s.split(" ")[1]);
                        timeSelect.innerHTML = "<option value=''>-- Choisir une heure --</option>";
                        heures.forEach(h => {
                            timeSelect.innerHTML += "<option value='" + h + "'>" + h + "</option>";
                        });
                    };
                })
                .catch(err => {
                    console.error(err);
                    dateSelect.innerHTML = "<option value=''>Erreur de chargement</option>";
                });
        }


    </script>
</head>
<body>
<h2>Réserver une Consultation</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="patient" method="post">
    <input type="hidden" name="action" value="reserver"/>

    <label>Département:</label><br/>
    <select id="departement" name="departementId" onchange="loadDocteurs(this.value)" required>
        <option value="">-- Choisir --</option>
        <c:forEach var="d" items="${departements}">
            <option value="${d.idDepartement}">${d.nom}</option>
        </c:forEach>
    </select><br/><br/>

    <label>Docteur:</label><br/>
    <select id="docteur" name="docteurId" onchange="loadDisponibilites()" required>
        <option value="">-- Choisir un docteur --</option>
    </select><br/><br/>

    <label>Date:</label><br/>
    <select id="date" name="date" required></select><br/><br/>

    <label>Heure:</label><br/>
    <select id="heure" name="heure" required></select><br/><br/>

    <input type="submit" value="Réserver"/>
</form>

<p><a href="patient">← Retour</a></p>
</body>
</html>

