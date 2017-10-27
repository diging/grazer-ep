<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
     
    <title>File Manager</title>
 
    <!-- Bootstrap core CSS -->
    <link href="<c:url value="resources/dist/css/bootstrap.min.css" />" rel="stylesheet">
 
    <!-- Custom styles for this template -->
    <link href="resources/navbar.css" rel="stylesheet">
 
     
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
 
  <body>
 
    <div class="container">
       
      <div class="panel panel-default">
          <!-- Default panel contents -->
          <div class="panel-heading">Files</div>
         
          <!-- Table -->
          <table class="table">
            <tr>
            	    <th>Transformation Label</th>
            	    <th>Description</th>
                <th>Pattern File</th>
                <th>Transformation File</th>
                <th>Uploader</th>
                <th>Date</th>
                <th>Upload</th>
            </tr>
            <tr>
                <td>Label</td>
                <td>Description</td>
                <td><a title="Download file" href="">Pat.txt</a></td>
                <td><a title="Download file" href="">Tra.txt</a></td>
                <td>Admin</td>
                <td>May 3, 2015</td>
                <td><a title="Upload new version"><span class="glyphicon glyphicon-cloud-upload"></span></a></td>
            </tr>
          </table>
      </div>
 
    </div> <!-- /container -->
 
 
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="resources/dist/js/bootstrap.min.js"></script>
  </body>
</html>