<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
//# sourceURL=getPersons.js
    $(function() {
    	   getPersons();
    });

    function getPersons() {
        $.ajax({
            url : '<c:url value="/persons?pollUrl=${pollUrl}" />',
            type : "GET",
            success : function(result) {
                if (result == null || result.length == 0) {
                    setTimeout(getPersons, 3000);
                } else {
                    $("#spinner").hide();
                    $("#count").text(result.length);
                    result.forEach(function(element) {
                    	   var a = $("<a>");
                    	   a.attr('href', 'concept/' + element.id);
                    	   a.attr('class', 'list-group-item');
                    	   a.text(element.word);
                    	   $("#personList").append(a);
                    });
                }
            }
        });
    }
</script>

<h2>People mentioned in the Embryo Project</h2>

<ul id="graphList" class="list-group">
<div id="spinner"><div class="fa fa-spinner fa-spin"></div> Loading people... Hang tight, this might take a few minutes.</div>
</ul>

<div>Total: <span id="count"></span></div>
<div id="personList" class="list-group">

</div>