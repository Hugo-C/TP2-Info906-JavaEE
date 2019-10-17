<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Accueil</title>
	</head>
	<body>
	  <h2>Enregistrer un colis</h2><br/>
	  <form method="get" action="AddPackageServlet">
		  Poids : <input type="number" name="weight" min=0.0 step="0.01" required><br/>
		  Valeur : <input type="number" name="val" min=0.0 step="0.01" required><br/><br/>
		  <h4>Origine :</h4>
		  latitude : <input type="number" name="latOrigin" step="0.01" required><br/>
		  longitude : <input type="number" name="longOrigin" step="0.01" required><br/>
		  emplacement : <input type="text" name="localityOrigin" required><br/><br/>
		  <h4>Destination :</h4>
		  latitude : <input type="number" name="latDest" step="0.01" required><br/>
		  longitude : <input type="number" name="longDest" step="0.01" required><br/>
		  emplacement : <input type="text" name="localityDest" required><br/><br/>
		  <input type="submit" name="valider"  value="Enregistrer">
	  </form>

	  <h2>Suivre un colis</h2><br/>
	  <form method="get" action="ShowPackageServlet">
		  Identifiant : <input type="number" name="id" min=1 required><br/><br/>
		  <input type="submit" name="valider"  value="Suivre">
	  </form>

      <br/>
      <a href="updatePackage.jsp" >Mettre à jour l'acheminement d'un colis</a>
	</body>
</html>