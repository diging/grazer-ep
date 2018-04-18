<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    
    <link rel="stylesheet" href="<c:url value="/resources/font-awesome-4.6.3/css/font-awesome.min.css" />" />
    <link href="https://fonts.googleapis.com/css?family=Dosis" rel="stylesheet"> 
    
    <title>EP Grazer</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="<c:url value="/resources/bootstrap/assets/css/ie10-viewport-bug-workaround.css" />" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/bootstrap/grid.css" />" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="<c:url value="/resources/bootstrap/assets/js/ie8-responsive-file-warning.js" />"></script><![endif]-->
    <script src="<c:url value="/resources/bootstrap/assets/js/ie-emulation-modes-warning.js" />"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
 	<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/bootstrap/js/main.js" />"></script>
	
	<!-- Autocomplete function -->
	<script type="text/javascript">
        var root = "${pageContext.servletContext.contextPath}";
    </script>
	<script src="<c:url value="/resources/js/searchConcepts.js" />"></script>
	<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/jquery-ui.css" />
	
	<c:set var="googleTracking" value="${google.tracking.id}" />
	<c:if test="${not empty googleTracking }">
	<!-- Google Analytics -->
	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=${google.tracking.id}"></script>
	<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());
	
	  gtag('config', '${google.tracking.id}');
	</script>
	</c:if>
  </head>

  <body>
  <div style="opacity: 0.1; position: absolute; top: 10px; left: 10px; ">
    <img src="<c:url value="/resources/images/ep-logo.gif" />" />
  </div>
    <div class="container" style="padding-bottom: 150px;">

      <div class="page-header">
      <nav>
          <ul class="nav nav-pills pull-right">
              <li role="presentation">
          		<a href="<c:url value="/" />" >Home</a>
              </li>
          	
          	  <sec:authorize access="isAuthenticated()">
          	  <li role="presentation">
         	 	<form action="<c:url value="/logout" />" method="POST">
         	 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  				<button class="btn-link" type="submit" title="Logout"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</button>
         	 	</form>
         	  </li>
              </sec:authorize>
          </ul>
          
        </nav>
        
        <h1><a class="appName" href="<c:url value="/" />">EP Grazer</a></h1>   
      </div>
      
      
	  <c:if test="${show_alert}" >
	  <div class="alert alert-${alert_type}" role="alert">${alert_msg}</div>
	  </c:if>
      <tiles:insertAttribute name="content" />

    </div> <!-- /container -->
    
    <footer class="footer">
      <div class="container">
      
        <div class="row">
        <div class="col-md-12">
		<hr style="margin-bottom: 25px;">
		<p class="text-muted pull-left">
		
	    <p class="text-muted">
	    Version: ${buildNumber}
        </p>
        </div>
        </div>
      </div>
    </footer>
    

     </body>
</html>
