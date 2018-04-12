<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="<c:url value="/resources/js/cytoscape.min.js" />"></script>
<script src="<c:url value="/resources/js/cytoscape-layouts/cytoscape-cose-bilkent.js" />"></script>
<script type="text/javascript">
    var highlightSize = "50px";
    var nodeSize = "15px";
    var url = '<c:url value="/persons/network" />';
    var hrefLocation = "concept/";
    var animate = true;
</script>
<script src="<c:url value="/resources/js/graphDisplay.js" />"></script>

<style>
    #personList {
        overflow-y: auto;
        height: 70vh;
    }
</style>

<h2>People mentioned in the Embryo Project</h2>

<div class="row">
    <div class="col-md-5">
        <div>Total: <span id="count">${count}</span></div>
        <div id="personList" class="list-group">
            <c:forEach items="${concepts}" var="concept">
                <a href="concept/${concept.id}" class="list-group-item person-entry" data-concept-id="${concept.id}">${concept.word}</a>
            </c:forEach>
        </div>
    </div>
    <div class="col-md-7 graph-outer" id="graph">
        <div id="network" class="graph" style="min-width: 500px; min-height: 500px;">
            <div id="spinner" class="text-center">
                <div class="fa fa-spinner fa-spin"></div> 
                Loading graph...
            </div>
        </div>
    </div>
</div>
<div class="col-md-12" id="array"></div>