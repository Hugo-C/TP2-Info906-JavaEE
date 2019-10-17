<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Mise à jour d'un colis | Bonne journée</title>
	</head>
	<body>
	  <h2>Modifier un colis</h2><br/>
	  <form method="get" action="UpdatePackageServlet">
		  ID: <input type="number" name="id" required><br/>
		  Etat: <input list="states" type="text" name="state"><br/>
		  <datalist id="states">
			  <option value="REGISTERING">
			  <option value="WAITING">
			  <option value="TRANSIT">
			  <option value="BLOCKED">
			  <option value="DELIVERED">
		  </datalist>
		  <h4>Nouvelle position :</h4>
		  latitude: <input type="number" name="latitude" step="0.01" required><br/>
		  longitude: <input type="number" name="longitude" step="0.01" required><br/>
		  emplacement: <input type="text" name="name" required><br/><br/>
		  <input type="submit" name="valider"  value="Enregistrer">
	  </form>
	  <br/>
	  <a href="index.jsp">Retour à l'acceuil</a>
	</body>
</html>