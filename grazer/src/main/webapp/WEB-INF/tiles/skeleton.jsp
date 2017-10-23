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
    <script src="<c:url value="/resources/js/jquery-alert.js" />"></script>
  </head>

  <body data-spy="scroll" data-offset="0" data-target="#navigation">
    <tiles:importAttribute name="currentPage" scope="request" />

    <div id="navigation" class="navbar navbar-default">
      <div class="container-fluid">
        <div>
          <div class="navbar-collapse collapse navbar-right">
            <ul class="nav navbar-nav">
              <tiles:insertAttribute name="navigation" />
            </ul>
          </div>
          <!--/.nav-collapse -->
          <sec:authorize access="isAuthenticated()">
            <div class="container">
              <div class="row" style="padding-top: 15px;">
                <div class="col-md-10">
                  Welcome <i class="fa fa-user"></i><span style="margin-left: 5px;"><sec:authentication property="principal.username" /></span>!
                </div>
                <div class="col-md-2">
                  <form action="<c:url value='/logout' />" method='POST' class="pull-right">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div>
                      <button type="submit" class="btn btn-link" style="color:#800000"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </sec:authorize>
          <sec:authorize access="not isAuthenticated()">
            <form name='f' action="<c:url value='/login' />" method='POST' class="navbar-form navbar-right">
              <div class="form-group">
                <input type="text" class="form-control input-sm" name="username" placeholder="Username">
              </div>
              <div class="form-group">
                <input type="password" class="form-control input-sm" name="password" placeholder="Password">
              </div>
              <div class="form-group">
                <button type="submit" class="btn btn-primary btn-sm">Sign In</button>
              </div>
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
          </sec:authorize>
        </div>
      </div>
    </div>
    <div id="headerwrap">
      <div class="container">
        <div class="row centered">
          <div class="col-lg-12">
            <script>console.log("Hi how are you");</script>
            <c:choose>
            <c:when test="${not empty error }">
            <!--<c:if test="${not empty error}">-->
              <div class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert"
                  aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
                Your login attempt was not successful, try again.<br /> Caused :
                  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
              </div>
            <!--</c:if>-->
            </c:when>
            </c:choose>
          </div>
        </div>
      </div>
      <!--/ .container -->
    </div>
    <!--/ #headerwrap -->
    
    
    <c:choose>
      <c:when test="${show_success_alert}">
        <div class="row" style="margin-top: 20px;">
				<div class="col-md-offset-1 col-md-10">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						${success_alert_msg}
					</div>
				</div>
			</div>
		</c:when>
		<c:when test="${show_error_alert}">
			<div class="row" style="margin-top: 20px;">
				<div class="col-md-offset-1 col-md-10">
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						${error_alert_msg}
					</div>
				</div>
			</div>
		</c:when>
		<c:when test="${show_info_alert}">
			<div class="row" style="margin-top: 20px;">
				<div class="col-md-offset-1 col-md-10">
					<div class="alert alert-info">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						${info_alert_msg}
					</div>
				</div>
			</div>
		</c:when>
	</c:choose>

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
          </ul>
        </nav>
        
        <h1><a class="appName" href="<c:url value="/" />">EP Grazer</a></h1>   
      </div>
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
            </p>
          </div>
        </div>
      </div>
    </footer> 
  </body>
</html>
