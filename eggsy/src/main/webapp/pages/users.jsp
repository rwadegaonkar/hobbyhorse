<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p>Non Deleted Users are:</p>
 
<c:forEach var="user" items="${classRoom.users}">
	${user.username} - ${user.name}<br/>
</c:forEach>