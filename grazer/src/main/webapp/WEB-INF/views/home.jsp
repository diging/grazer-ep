<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>People mentioned in the Embryo Project</h2>

<div>Total: <span id="count"></span></div>
<div id="personList" class="list-group">
    <c:forEach items="${concepts}" var="concept">
    <a href="concept/${concept.id}" class="list-group-item">${concept.word}</a>
    </c:forEach>
</div>