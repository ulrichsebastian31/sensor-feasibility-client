<%
	response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
	String newLocn = "www/com.astrium.faceo.HomePage/HomePage.html";
	response.setHeader("Location",newLocn);
%>

