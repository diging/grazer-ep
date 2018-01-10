<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
//# sourceURL=getGraph.js
	$(function() {
	    getGraph();
	});

	function getGraph() {
		$.ajax({
			url : '<c:url value="/concept/${concept.id}/graph" />',
			type : "GET",
			success : function(result) {
				if (result == null || result.trim() == '') {
					$("#spinner").hide();
					$("#graphList").append("Sorry, there are no relationships yet.")
				} else {
					var list = $.parseHTML(result);
					$("#spinner").hide();
					$("#graphList").append(list);
				}
			}
		});
	}
</script>

<h2>${concept.word}</h2>

<p>${concept.description}</p>

<div>
	<c:forEach var="listVar" items="${concept.getEqualTo() }"> 
		<c:if test="${not empty listVar }">
	    		<a href="${listVar}" target="_blank"> <i class="fa fa-external-link" aria-hidden="true"></i>       ${listVar} </a><br/>
		</c:if>
	</c:forEach>
</div>
<br/>

<div class="col-md-8">
<ul id="graphList" class="list-group">
<div id="spinner"><div class="fa fa-spinner fa-spin"></div> Loading relationships... Hang tight, this might take a few minutes.</div>
</ul>
</div>

<div class="col-md-4">
<div class="panel panel-default">
  <div class="panel-heading">Wikidata Statements</div>
  <div class="panel-body">
    <c:if test="${wikidata_error}">
    <div class="alert alert-danger" role="alert">An error occurred retrieving statements from Wikidata.</div>
    </c:if>
    
    <c:if test="${not wikidata_error}">
    <c:if test="${empty wikipedia}">
    <div class="alert alert-warning" role="alert">Could not find any statements. This could either mean that there are no statements in Wikidata or Conceptpower has no Wikipedia link for the concept.</div>
    </c:if>
    <ul>
	    <c:forEach var="statement" items="${wikipedia}">
		<li><strong>${statement.predicate.label}</strong>: ${statement.object.label}</li>
		</c:forEach>
	</ul>
	</c:if>
  </div>
</div>

</div>

