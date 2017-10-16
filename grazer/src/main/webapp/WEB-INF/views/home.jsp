<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="<c:url value="/resources/js/cytoscape.min.js" />"></script>
<script src="<c:url value="/resources/js/cytoscape-layouts/cytoscape-cose-bilkent.js" />"></script>
<script>
//# sourceURL=graph.js
$(document).ready(function() {
	$.ajax({
        url : '<c:url value="/persons/network" />',
        type : "GET",
        success : function(result) {
            if (result == null || result.length == 0) {
                	$("#spinner").hide();
                $("#network").append("Sorry, no network to display.")
            } else {
            	   $("#spinner").hide();
                data = JSON.stringify(result);
	            var cy = cytoscape({
	            		container: $('#network'),
	            		elements: result,
	            		style: [ // the stylesheet for the graph
	            		    {
	            		      selector: 'node',
	            		      style: {
	            		        'background-color': '#7bafa6',
	            		        'width': '15px',
	            		        'height': '15px',
	            		        'label': 'data(label)'
	            		      }
	            		    },
	            		    {
	            		      selector: 'edge',
	            		      style: {
	            		        'width': 2,
	            		        'line-color': '#b0c7c3',
	            		        'target-arrow-color': '#b0c7c3',
	            		        'target-arrow-shape': 'triangle'
	            		      }
	            		    }
	            		  ],

	            		  layout: {
	            		    name: 'cose-bilkent',
	            		    nodeDimensionsIncludeLabels: true,
	            		  }
	            	});
	            //$("#array").text(data);
            	}
        },
        error: function() {
        	   $("#spinner").hide();
            $("#network").append("Sorry, could not load network.")
        }
    });
	
})

</script>

<h2>People mentioned in the Embryo Project</h2>

<div class="row">
<div class="col-md-5">
<div>Total: <span id="count">${count}</span></div>
<div id="personList" class="list-group">
    <c:forEach items="${concepts}" var="concept">
    <a href="concept/${concept.id}" class="list-group-item">${concept.word}</a>
    </c:forEach>
</div>
</div>

<div class="col-md-7">
<div id="network" style="min-width: 500px; min-height: 500px;">
<div id="spinner" class="text-center"><div class="fa fa-spinner fa-spin"></div> Loading graph...</div>
</div>
</div>
</div>
<div class="col-md-12" id="array"></div>