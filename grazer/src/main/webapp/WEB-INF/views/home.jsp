<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
// Get the modal
var modal = document.getElementById('myModal');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>

<!-- Trigger the modal with a button -->
<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Sign In</button>

<!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Sign In</h4>
        </div>
        <div class="modal-body">
          <form action="#">
            <div>
              <label><b>Email</b></label>
		      <input type="text" placeholder="Enter Email" name="email" required>
		
		      <label><b>Password</b></label>
		      <input type="password" placeholder="Enter Password" name="psw" required>
		    </div>
		    <br/>
		    <div>
			  <button type="submit">Sign In</button>
			  <button type="button" onclick="document.getElementById('myModal').style.display='none'" >Cancel</button>
			</div>
          </form>
        </div>
      </div>
      
    </div>
  </div>
<h2>People mentioned in the Embryo Project</h2>
<div>Total: ${fn:length(concepts)}</div>
<div class="list-group">
<c:forEach items="${concepts}" var="concept">
<a href="<c:url value="/person/${concept.id}" />" class="list-group-item">
${concept.word}
</a>
</c:forEach>
</div>