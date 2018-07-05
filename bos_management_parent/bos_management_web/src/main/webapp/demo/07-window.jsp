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
第一步：给表单校验输入项将样式 validatebox,numberbox-当输入值不合法显示警告提示信息<br>
第二步：设置表单校验规则  <br>
1、非空校验 通过data-options="required:true"  <br>
2、其他校验规则：email，url,length，remote ;data-options 通过validType指定<br>
第三步：调用form方法validate方法验证表单中输入项是否合法<br>
<body>
	<div class="easyui-window"  style="width:600px;height:400px" title="用户添加" data-options="collapsible:false">
		<form id="userForm" action="xx.action" method="post">
			<input type="text" name="username" class="easyui-validatebox" data-options="required:true,validType:'length[3,6]'" >
			<input type="text" name="age" class="easyui-numberbox" data-options="required:true"><br>
			<input value="保存" id="save" type="button">
			<script type="text/javascript">
				$("#save").click(function(){
					var r = $("#userForm").form("validate");
					if(r){
						$("#userForm").submit();
					}
				})
			</script>
		</form>
	</div>
</body>
</html>