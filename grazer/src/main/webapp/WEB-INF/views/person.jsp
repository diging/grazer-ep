<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<h2>${concept.word}</h2>

<p>
${concept.description}
</p>

<c:forEach items="${graph.nodes}" var="node">
${node.label}
</c:forEach>