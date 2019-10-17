<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Affichage Mesure</title>
	</head>
	<body>
	<h2>Suivi du colis</h2><br/>
	  <p>
		 Identifiant : ${ package.id } <br/>
		 Poids : ${ package.weight } <br/>
		 Valeur : ${ package.value } <br/>
         Etat : ${ package.state } <br/><br/>
		 <h4>Origine :</h4>
		 Latitude : ${ package.origin.latitude } <br/>
		 Longitude : ${ package.origin.longitude } <br/>
		 Emplacement : ${ package.origin.name } <br/><br/>
	     <h4>Position actuelle :</h4>
		 Latitude : ${ package.currentPosition.latitude } <br/>
		 Longitude : ${ package.currentPosition.longitude } <br/>
		 Emplacement : ${ package.currentPosition.name } <br/><br/>
	     <h4>Destination :</h4>
		 Latitude : ${ package.destination.latitude } <br/>
		 Longitude : ${ package.destination.longitude } <br/>
		 Emplacement : ${ package.destination.name } <br/><br/>
	  </p>
	  <a href="index.xhtml" >Revenir à la page d'accueil</a>
	</body>
</html>