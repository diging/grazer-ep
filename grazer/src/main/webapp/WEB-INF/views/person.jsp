<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<h2>${concept.word}</h2>

<p>
${concept.description}
</p>

<ul>
<c:forEach items="${graph.edges}" var="edge">
<li>${edge.sourceNode.label} - ${edge.label} - ${edge.targetNode.label} </li>
</c:forEach>
</ul>