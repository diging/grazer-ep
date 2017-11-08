<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Upload File Form</title>
</head>
<body>
    <div id="global">
        <form:form commandName="transformation" action="save-transformation?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
            <fieldset>
                <legend>Add a file</legend>
                <p>
                    <label for="label">Transformation Label: </label>
                    <form:input id="label" path="label" cssErrorClass="error" required="required"/>
                    <form:errors path="label" cssClass="error" />
                </p>
                <p>
                    <label for="description">Description: </label>
                    <form:input id="description" path="description" required="required"/>
                </p>
                <p>
                    <label for="file">Transformation File: </label>
                    <input type="file" name="files" accept=".graphml" required="required"/>
                </p>
                <p>
                    <label for="file">Pattern File: </label>
                    <input type="file" name="files" required="required"/>
                </p>
                <p id="buttons">
                    <input id="reset" type="reset" tabindex="4">
                    <input id="submit" type="submit" tabindex="5" value="Add File">
                </p>
            </fieldset>
        </form:form>
    </div>
</body>
</html>