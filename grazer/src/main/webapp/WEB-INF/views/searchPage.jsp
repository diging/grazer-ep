<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>

<script>
$(document).ready(function() {
    $("#tagQuery").autocomplete({
        minLength: 1,
        //define callback to format results
        source: function (request, response) {
            $.getJSON("getTags", request, function(result) {
                response($.map(result, function(item) {
                    return {
                        label: item.word,
                        value: item.word,
                        tag_url: "http://localhost:8081/grazer/concept/" + item.id
                    }
                }));
            });
        },
        //define select handler
        select : function(event, ui) {
            if (ui.item) {
                event.preventDefault();
                $("#selected_tags span").append('<a href=" + ui.item.tag_url + " target="_blank">'+ ui.item.label +'</a>');
                var defValue = $("#tagQuery").prop('defaultValue');
	            $("#tagQuery").val(defValue);
                $("#tagQuery").blur();
                return false;
            }
        }
    });
});

    function inputFocus(i) {
        if(i.value==i.defaultValue){ i.value=""; i.style.color="#000"; }
    }

    function inputBlur(i) {
        if(i.value==""){ i.value=i.defaultValue; i.style.color="#848484"; }
    }
</script>

<div id="find_keyword">
    <div class="ui-widget">
        <input id="tagQuery" type="text" name="tagQuery" value="" onFocus="inputFocus(this)" onBlur="inputBlur(this)">
    </div>
</div>

<div id="selected_tags">
    <span></span>
</div>