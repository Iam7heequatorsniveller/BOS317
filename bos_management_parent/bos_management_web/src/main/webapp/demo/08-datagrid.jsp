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
<table class="easyui-datagrid">
	<thead>
		<tr>
			<th data-options="field:'id'">编号</th>
			<th data-options="field:'name'">姓名</th>
			<th data-options="field:'age'">年龄</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>001</td>
			<td>小明</td>
			<td>18</td>
		</tr>
		<tr>
			<td>002</td>
			<td>小红</td>
			<td>19</td>
		</tr>
	</tbody>
</table>
<hr>
发送ajax请求
<table class="easyui-datagrid" data-options="url:''">
	<thead>
		<tr>
			<th data-options="field:'id'">编号</th>
			<th data-options="field:'name'">姓名</th>
			<th data-options="field:'age'">年龄</th>
		</tr>
	</thead>
</table>
<hr>
通过js方式创建数据表格
<table id="dg"></table>
<script type="text/javascript">
$('#dg').datagrid({ 
	url:'../data/user.json', //发送请求   数据来源
	columns:[[       //展示数据
		{field:'id',title:'编号',width:100}, 
		{field:'name',title:'姓名',width:100}, 
		{field:'age',title:'年龄',width:100,align:'right'} 
	]],
	pagination:true,//展示分页栏
	/*
		请求：提交参数
		     加入分页组件后，会自动提交两个参数 1、page（当前页） 1、rows(每页显示记录数)
		响应：响应json数据
		{
			total:100,
			rows:[]  //当前页记录
		}
	*/
	toolbar:[{  		
			  iconCls: 'icon-edit',  
			  text:"编辑",
			  handler: function(){ //点击事件
				  alert('edit')
			  }  	
		  },{  		
			  iconCls: 'icon-save',  
			  text:"新增",
			  handler: function(){ //点击事件
				  alert('save')
			  }  	
		  },{  		
		  iconCls: 'icon-help', 
		  text:'帮助',
		  handler: function(){alert('help')}  	
	  }] ,
	  rownumbers:true,
	  singleSelect:true,
	  striped:true,
	  pageSize:3,
	  pageList:[3,10]

}); 
</script>
</body>
</html>