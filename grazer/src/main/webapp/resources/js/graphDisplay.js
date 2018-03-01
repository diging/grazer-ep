$(document).ready(function() {
    var cy;
    $.ajax({
        url : url,
        type : "GET",
        success : function(result) {
            if (result == null || result.length == 0) {
                $("#spinner").hide();
                $("#network").append("Sorry, no network to display.")
            } else {
                $("#spinner").hide();
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
                    window.location.href = hrefLocation + this.data('id');
                })
                
                cy.ready(function() {
                    $(".person-entry").hover(highlightPersonInGraph, removeHighlight);
                });
            }
        },
        error: function() {
            $("#spinner").hide();
            $("#network").append("Sorry, could not load network.")
        }
    });
    
    function highlightPersonInGraph() {
        var id = $(this).data("concept-id");
        var node = cy.getElementById(id);
        if(animate == true) {
            cy.animate({
                fit: {
                    eles: node,
                    padding: 230,
                }
            });
        }
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