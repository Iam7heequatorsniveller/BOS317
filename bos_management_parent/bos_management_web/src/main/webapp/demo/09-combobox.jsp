<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<select name="city">
		<option value="1">北京</option>
		<option value="2">上海</option>
		<option value="3">广州</option>
	</select>
	<hr>
	<select name="city" class="easyui-combobox"  style="width:200px;">
		<option value="1">北京</option>
		<option value="2">上海</option>
		<option value="3">广州</option>
	</select>
	<hr>
	<input id="cc" name="cityName" class="easyui-combobox" name="dept" data-options="valueField:'id',textField:'name1',url:'../data/city.json'" />
</body>
</html>