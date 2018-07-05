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
<!-- 第一步：给html元素加样式  easyui-组件名称
	 第二步：通过data-options设置属性
 -->
<body>
	<a class="easyui-menubutton" data-options="menu:'#mm',iconCls:'icon-help'">控制面板</a>
	<div id="mm">
		<div onclick="updatePwd()">修改密码</div>
		<div onclick="about()">关于系统</div>
		<div class="menu-sep"></div>
		<div onclick="logout()">退出系统</div>
	</div>
	<script type="text/javascript">
		function updatePwd(){
			alert("update")
		}
		function about(){
			//error,question,info,warning.
			$.messager.alert('系统信息','速运快递v1.1','info');
		}
		function logout(){
			$.messager.confirm('系统信息','速运快递v1.1',function(r){
// 				alert(r);
				if(r){
					location.href="logout.action";
				}
			});
		}
		
		$.messager.show({  	
			  title:'系统信息',  	
			  msg:'欢迎【admin】登陆',  	
			  timeout:5000,  	
			  showType:'slide'  
			});  


	</script>
</body>
</html>