<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<!-- errorMessages.jsp -->
<logic:messagesPresent>
  <div class="errors">
    <html:messages id="error">
      <c:out value="${error}"/><br/>
    </html:messages>
  </div>  
  <br/>
</logic:messagesPresent>

<logic:messagesPresent message="true">
  <div class="messages">
    <html:messages id="message" message="true">
      <c:out value="${message}"/><br/>
    </html:messages>
  </div>
  <br/>
</logic:messagesPresent>
<!-- /errorMessages.jsp -->
