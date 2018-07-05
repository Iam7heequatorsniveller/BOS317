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
	第三步：调用组件方法。方法名作为参数
 -->
<body class="easyui-layout">
	<div data-options="region:'north',border:true" style="height: 100px" title="xx系统">北部区域</div>
	<div data-options="region:'south',border:true" style="height: 100px">南部区域</div>
	<div data-options="region:'west',border:true" style="width: 250px">
		<!-- 制作折叠面板 -->
		<div class="easyui-accordion" data-options="fit:true">
			<div data-options="title:'基本菜单'">
				<input type="button" value="点我" id="myBtn">
				<script type="text/javascript">
					$("#myBtn").click(function(){
						
						//判断子面板是否存在
						var r = $("#myTabs").tabs("exists","用户管理");
						if(r){
							$("#myTabs").tabs("select","用户管理");
						}else{
							//调用tabs组件方法；给中心区域选项卡面板添加子面板
							$("#myTabs").tabs("add",{
								title:"用户管理",
								content:'用户信息',
								iconCls:'icon-save'
							})
						}
					})
				</script>
			</div>
			<div data-options="title:'系统菜单'"></div>
			<div data-options="title:'其他菜单'"></div>
		</div>
	</div>
<!-- 	<div data-options="region:'east',border:true" style="width: 230px">东部区域</div> -->
	<div data-options="region:'center',border:true">
		<!-- 制作选项卡面板 -->
		<div id="myTabs" class="easyui-tabs" data-options="fit:true">
			<div data-options="title:'系统设置',closable:true,iconCls:'icon-edit'">
			content
			</div>
			<div data-options="title:'其他设置'"></div>
		</div>
	</div>
</body>
</html>