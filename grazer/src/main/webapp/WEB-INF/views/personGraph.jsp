<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach items="${graph.edges}" var="edge">
<li class="list-group-item">
<c:choose>
    <c:when test="${fn:contains(alternativeIdsString, edge.sourceNode.uri)}">
        <strong>${edge.sourceNode.label}</strong>
    </c:when>
    <c:when test="${edge.sourceNode.type == 'http://www.digitalhps.org/types/TYPE_986a7cc9-c0c1-4720-b344-853f08c136ab' }" >
        <a href="<c:url value="/person/${edge.sourceNode.conceptId}" />">${edge.sourceNode.label}</a>
    </c:when>
    <c:otherwise>
        ${edge.sourceNode.label}
    </c:otherwise>
</c:choose>
 ${edge.label}
<c:choose>
    <c:when test="${fn:contains(alternativeIdsString, edge.targetNode.uri)}">
        <strong>${edge.targetNode.label}</strong>
    </c:when>
    <c:when test="${edge.targetNode.type == 'http://www.digitalhps.org/types/TYPE_986a7cc9-c0c1-4720-b344-853f08c136ab' }" >
        <a href="<c:url value="/person/${edge.targetNode.conceptId}" />">${edge.targetNode.label}</a>
    </c:when>
    <c:otherwise>
        ${edge.targetNode.label}
    </c:otherwise>
</c:choose> 
<c:if test="${not empty edge.startTime}">
    starting in ${edge.startTime}
</c:if>
<c:if test="${not empty edge.endTime}">
    ending in ${edge.endTime}
</c:if>
<c:if test="${not empty edge.occurred}">
    in ${edge.occurred}
</c:if>
    found in <a href="${edge.sourceUri}" target="_blank"><i class="fa fa-globe" aria-hidden="true"></i> ${edge.sourceUri}</a>.
</li>
</c:forEach>