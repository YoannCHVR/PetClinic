<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>

<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>

    <h2>peterinarian Information</h2>

    <table class="table table-striped" style="width:600px;">
        <tr>
            <th>Name</th>
            <td><c:out value="${pet.name}"/></td>
        </tr>
         <tr>
            <th>Memos</th>
            <td> <c:forEach var="memo" items="${pet.memos}">
                    <c:out value="${memo.description}"/> - <joda:format value="${memo.date}" pattern="yyyy-MM-dd"/>
                    <br>
                </c:forEach> 
                <c:if test="${pet.nrOfMemos == 0}">none</c:if> 
           </td>
          </tr>
            
        <tr>
            <th> </th>
            <td>
            	<spring:url value="{petId}/memos/new.html" var="addUrl">
                    <spring:param name="petId" value="${pet.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(addUrl)}"  class="btn btn-success">Add New Memo</a></td>
                
        </tr>
         
    </table>
    
     
    
        
       
        
    

   
      
   

    <jsp:include page="../fragments/footer.jsp"/>

</div>

</body>

</html>