<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <div id="global">
        <form:form commandName="transformation" action="/grazer/transformation/save?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
            <fieldset>
                <legend>Add a file</legend>
                <div class="form-group">
                    <label for="label">Transformation Label: </label>
                    <form:input class="form-control" id="label" path="label" cssErrorClass="error" required="required"/>
                    <form:errors path="label" cssClass="error" />
                </div>
                <div class="form-group">
                    <label for="description">Description: </label>
                    <form:input class="form-control" id="description" path="description" required="required"/>
                </div>
                <div class="form-group">
                    <label for="file">Transformation File: </label>
                    <input class="form-control" type="file" name="files" accept=".graphml" required="required"/>
                </div>
                <div class="form-group">    
                    <label for="file">Pattern File: </label>
                    <input class="form-control" type="file" name="files" accept=".graphml" required="required"/>
                </div>
                <div id="buttons">
                    <input class="btn btn-default" id="reset" type="reset" tabindex="4">
                    <input class="btn btn-default" id="submit" type="submit" tabindex="5" value="Add File">
                </div>
            </fieldset>
        </form:form>
        
        <h3>File List</h3>
        <c:if test="${!empty fileList}">
            <table class="table table-striped]">
                <thead>
                    <tr>
                        <th>Label</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${fileList}" var="files">
                        <tr> 
                            <td> ${files.label} </td>
                            <td> ${files.description} </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</html>