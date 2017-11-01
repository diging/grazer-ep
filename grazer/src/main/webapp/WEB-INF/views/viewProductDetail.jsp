<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Save File</title>
</head>
<body>
<div id="global">
    <h4>The file has been saved.</h4>
        <h5>Details:</h5>
        File Label: ${product.label}<br/>
        Description: ${product.description}<br/>
        <p>Following files are uploaded successfully.</p>
        <ol>
        <c:forEach items="${product.files}" var="file">
            <li>${file.originalFilename}</li>
        </c:forEach>
        </ol>
</div>
</body>
</html>