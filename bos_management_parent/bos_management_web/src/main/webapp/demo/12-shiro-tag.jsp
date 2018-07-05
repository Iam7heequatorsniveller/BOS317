<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<shiro:authenticated>
	认证后可见<br>
</shiro:authenticated>

<shiro:hasPermission name="abc">
    有abc权限可见<br>
</shiro:hasPermission>

<shiro:hasRole name="xyz">
  有xyz角色可见<br>
</shiro:hasRole>

<shiro:hasAnyRoles name="admin,xyz">
	有admin或者xyz可见
</shiro:hasAnyRoles>

</body>
</html>