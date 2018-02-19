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

<script src="<c:url value="/resources/js/cytoscape.min.js" />"></script>
<script src="<c:url value="/resources/js/cytoscape-layouts/cytoscape-cose-bilkent.js" />"></script>
<script>
//# sourceURL=graph.js
$(document).ready(function() {
    var cy;
    var highlightSize = "20px";
    var nodeSize = "10px";
    
    $.ajax({
        url : '<c:url value="/concept/${concept.id}/network1" />',
        type : "GET",
        success : function(result) {
            if (result == null || result.length == 0) {
                $("#spinner1").hide();
                $("#network").append("Sorry, no network to display.")
            } else {
                $("#spinner1").hide();
                data = JSON.stringify(result);
                cy = cytoscape({
                    container: $('#network'),
                    elements: result,
                    style: [ // the stylesheet for the graph
                        {
                            selector: 'node',
                            style: {
                                'background-color': '#7bafa6',
                                'width': nodeSize,
                                'height': nodeSize,
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
	            
                cy.on('tap', 'node', function(){
                    window.location.href = this.data('id');
                })
	            
                cy.ready(function() {
                    $(".person-entry").hover(highlightPersonInGraph, removeHighlight);
                });
            }
        },
        error: function() {
            $("#spinner1").hide();
            $("#network").append("Sorry, could not load network.")
        }
    });
	
    function highlightPersonInGraph() {
        var id = $(this).data("concept-id");
        var node = cy.getElementById(id);
        node.animate({
            css: { 'width': highlightSize, 'height' : highlightSize},
        });		
    }
    
    function removeHighlight() {
        var id = $(this).data("concept-id");
        var node = cy.getElementById(id);
        node.animate({
            css: { 'width': nodeSize, 'height' : nodeSize}
        });
    }
})

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


<div class="col-md-12">
    <ul id="graphList" class="list-group">
        <div id="spinner"><div class="fa fa-spinner fa-spin"></div> Loading relationships... Hang tight, this might take a few minutes.</div>
    </ul>
</div>

<div class="col-md-6">
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

<div class="col-md-6">
    <div id="network" style="min-width: 200px; min-height: 200px;"></div>
    <div id="spinner1" class="text-center"><div class="fa fa-spinner fa-spin"></div> Loading graph...</div>
</div>
