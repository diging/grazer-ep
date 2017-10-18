<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="headerwrap">
	<div class="container">
		<div class="row centered">
			<div class="col-lg-12">
				<c:if test="${not empty successmsg }">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						${ successmsg }
					</div>
				</c:if>
				<c:if test="${not empty error}">
				<div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert"
                            aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        Your login attempt was not successful, try again.<br /> 
                    </div>
				</c:if>
			</div>

		</div>
	</div>
	<!--/ .container -->
</div>
<!--/ #headerwrap -->

<div>Test..</div>