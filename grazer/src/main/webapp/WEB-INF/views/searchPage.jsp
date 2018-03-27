<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<script>
$(document).ready(function() {
    //attach autocomplete
    $("#tagQuery").autocomplete({
        minLength: 1,
        delay: 500,
        //define callback to format results
        source: function (request, response) {
            $.getJSON("/searchPage/getTags", request, function(result) {
                response($.map(result, function(item) {
                    return {
                        // following property gets displayed in drop down
                        label: item.word,
                        // following property gets entered in the textbox
                        value: item.word,
                        // following property is added for our own use
                        //tag_url: "http://" + window.location.host + "/concept/" + item.id
                    }
                }));
            });
        },

        //define select handler
        select : function(event, ui) {
            if (ui.item) {
                event.preventDefault();
                $("#selected_tags span").append('<a href=" + ui.item.tag_url + " target="_blank">'+ ui.item.label +'</a>');
                //$("#tagQuery").value = $("#tagQuery").defaultValue
	                var defValue = $("#tagQuery").prop('defaultValue');
	                $("#tagQuery").val(defValue);
                $("#tagQuery").blur();
                return false;
            }
        }
    });
});
</script>

<script type="text/javascript">
	function inputFocus(i){
		if(i.value==i.defaultValue){ i.value=""; i.style.color="#000"; }
	}
	function inputBlur(i){
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
	