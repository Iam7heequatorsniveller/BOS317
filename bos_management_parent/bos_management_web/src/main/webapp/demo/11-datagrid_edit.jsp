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
通过js方式创建数据表格
<table id="dg"></table>
<script type="text/javascript">
var editIdx = -1;  //编辑记录索引
$('#dg').datagrid({ 
	url:'../data/user.json', //发送请求   数据来源
	columns:[[       //展示数据
		{field:'id',title:'编号',width:100}, 
		{field:'name',title:'姓名',width:100,editor:{
			type:"validatebox",   //编辑类型 numberbox,validatebox,datebox combotree
			options:{"required":true}	          //指定必填项
		}}, 
		{field:'age',title:'年龄',width:100,align:'right',editor:{
			type:"datebox",   //编辑类型 numberbox,validatebox,datebox combotree
			options:{}	          //指定必填项
		}} 
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
				  iconCls: 'icon-save',  
				  text:"新增",
				  handler: function(){ //点击事件
					  //在datagrid中添加空白记录
					//插入新的一行在第一排的位置
					  $('#dg').datagrid('insertRow',{
					    	index: 0,	// index start with 0
					    	row: {   //新增记录
// 					    		name: 'new name',
// 					    		age1: 30,
// 					    		note: 'some messages'
					    	}
					    }); 
				  //开始编辑新增空白记录
				  $("#dg").datagrid("beginEdit", 0);
				  editIdx = 0;
				  }  	
			  },{  		
			  iconCls: 'icon-edit',  
			  text:"编辑",
			  handler: function(){ //点击事件
				  
				  var rows = $("#dg").datagrid("getSelections");
				  
				  //获取选中编辑记录索引
				  var idx = $("#dg").datagrid("getRowIndex", rows[0]);
				  
// 				  alert(idx);
				  
				  //调用数据表格方法开始编辑某一条记录
				  $("#dg").datagrid("beginEdit", idx);
				  editIdx = idx;
			  }  	
		  },{  		
		  iconCls: 'icon-add', 
		  text:'保存',
		  handler: function(){
			  //结束编辑  --新增，修改最后点击保存按钮
			  $("#dg").datagrid("endEdit", editIdx);
			  //调用结束编辑方法，结束编辑事件会自动触发
			  
		  }  	
	  },{  		
		  iconCls: 'icon-remove', 
		  text:'删除',
		  handler: function(){
			  var rows = $("#dg").datagrid("getSelections");
			  var array = new Array();
			 
			  for(var i=0;i<rows.length;i++){
				  array.push(rows[i].id);
				  //获取每一条记录索引
				  //获取选中编辑记录索引
				  var idx = $("#dg").datagrid("getRowIndex", rows[i]);
				  $("#dg").datagrid("deleteRow", idx);
			  }
			 var ids = array.join(",");  //拼接数组中每一个元素 返回字符串
			$.post("userAction_delete.action",{"ids":ids},function(data){
				  
			  })
			  
		  }  	
	  }] ,
	  rownumbers:true,
// 	  singleSelect:true,
	  striped:true,
	  pageSize:3,
	  pageList:[3,10],
	  onAfterEdit:function(rowIndex, rowData, changes){  //仅仅调用结束编辑方法才会触发
		  //rowData行记录
		  console.info(rowData);
		  $.post("userAction_save.action",rowData,function(data){
			  //刷新数据表格
		  })
	  }
}); 
</script>
</body>
</html>