<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>People mentioned in the Embryo Project</h2>
<div>Total: ${fn:length(concepts)}</div>
<div class="list-group">
<c:forEach items="${concepts}" var="concept">
<a href="<c:url value="/concept/${concept.id}" />" class="list-group-item">
${concept.word}
</a>
</c:forEach>
</div>

