<%@page import="javax.sql.RowSet"%>
<%@page import="com.a4i.util.DBUtil"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
</head>
<body>
	<%
		Connection connection = DBUtil.getConnection();
		
		RowSet rowset = DBUtil.getRef("demo(?)", new String[] {null}, DBUtil.getConnection());
		while (rowset.next()) {
			out.println(rowset.getString("actor_id") + "|" + rowset.getString("first_name") + "|" + rowset.getString("last_name") + "<br />");
		}
		
		connection.close();
	%>
</body>
</html>