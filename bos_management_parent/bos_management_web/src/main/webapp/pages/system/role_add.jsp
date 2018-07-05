<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>角色添加</title>
		<!-- 导入jquery核心类库 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
		<!-- 导入easyui类库 -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/ext/portal.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/ext/jquery.portal.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/ext/jquery.cookie.js"></script>
		<script src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<!-- 导入ztree类库 -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				
				var count = 0;//换行计数器
				//第一步：以checkbox展示权限数据
				$.post("${pageContext.request.contextPath}/permissionAction_findAll.action",null,function(data){
					for(var i=0;i<data.length;i++){
						count++;
						var id = data[i].id;
						var name = data[i].name;
						var content = '<input type="checkbox" name="permissionIds" id="'+id+'" value="'+id+'" /><label for="'+id+'">'+name+'</label>';
						if(count==5){
							$("#permissionTD").append(content+"<br>");
							count = 0;
						}else{
							$("#permissionTD").append(content);
						}
					}
				})
				
				
				// 第二步：展示菜单数据 ；授权树初始化
				var setting = {
					data : {
						key : {
							title : "t"
						},
						simpleData : {
							enable : true   //使用ztree简单数据格式
						}
					},
					check : {
						enable : true  // zTree 的节点上是否显示 checkbox 
					}
				};
				
				$.ajax({
// 					url : '${pageContext.request.contextPath}/menuAction_findAll.action',
					url : '${pageContext.request.contextPath}/menuAction_findAllBySimple.action',
					type : 'POST',
					dataType : 'json',
					success : function(data) {
// 						alert(data);
// 						var zNodes = eval("(" + data + ")");  //将文本类型转为json对象类型
						$.fn.zTree.init($("#menuTree"), setting, data);
					},
					error : function(msg) {
						alert('树加载异常!');
					}
				});
				
				// 点击保存
				//第三步：提交表单 提交数据（角色相关普通字段；权限Id；菜单Id）
				$('#save').click(function(){
					//先做表单校验
					var r = $("#roleForm").form("validate");
					if(r){
						//问题：ztree节点菜单id没有提交
						//原因：ztree勾选 底层span标签不能提交
						//解决：查询ztree的api,调用获取勾选中数据
						var treeObj = $.fn.zTree.getZTreeObj("menuTree");
						//注意：调用ztree组件,必须先获取到ztree对象
						var nodes = treeObj.getCheckedNodes(true);
						var array = new Array();
						for(var i=0;i<nodes.length;i++){
							var id = nodes[i].id;
							array.push(id);
						}
						var menuIds = array.join(",");
						//在表单中提供隐藏域（存放选中菜单ID）,给隐藏域赋值
						$("#menuIds").val(menuIds);
						$("#roleForm").submit();
					}
				});
			});
		</script>
	</head>

	<body class="easyui-layout">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="roleForm" method="post" action="${pageContext.request.contextPath}/roleAction_save.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
						<td>名称<input type="hidden" name="menuIds" id="menuIds" ></td>
						<td>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td>
							<input type="text" name="keyword" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>描述</td>
						<td>
							<textarea name="description" rows="4" cols="60"></textarea>
						</td>
					</tr>
					<tr>
						<td>权限选择</td>
						<td id="permissionTD">
<!-- 							<input type="checkbox" name="permissionIds" value="2" id="abc"/> <label for="abc">快递员列表查询</label> -->
<!-- 							<input type="checkbox" name="permissionIds" value="1" /> 添加快递员  -->
<!-- 							<input type="checkbox" name="permissionIds" value="3" /> 添加区域  -->
						</td>
					</tr>
					<tr>
						<td>菜单授权</td>
						<td>
							<ul id="menuTree" class="ztree"></ul>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>

</html>