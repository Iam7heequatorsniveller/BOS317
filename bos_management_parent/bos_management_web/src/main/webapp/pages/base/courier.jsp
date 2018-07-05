<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>管理取派员</title>
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
		<script type="text/javascript">
			function doAdd(){
				$('#addWindow').window("open");
			}
			
			function doEdit(){
				alert("修改...");
			}
			
			//批量删除
			function doDelete(){
				//判断选中记录个数
				var rows = $("#grid").datagrid("getSelections");
				if(rows.length==0){
					$.messager.alert('系统信息',"至少选中一条记录操作！",'warning');
				}else{
					//询问是否删除
				$.messager.confirm("系统信息","是否确定要删除？",function(r){
					if(r){
						//创建js Array数组
						var array = new Array();
						//1、将选中快递员的id全部获取到 2、发送请求将快递员id提交服务端
						for(var i=0;i<rows.length;i++){
							var courierId = rows[i].id;
							array.push(courierId);
						}
// 						alert(array);
						//方式一
						window.location.href="${pageContext.request.contextPath}/courierAction_delete.action?ids="+array;
						//方式二，在action方法中不需要结果视图
// 						$.post("${pageContext.request.contextPath}/courierAction_delete.action",{"ids":array},function(data){
// 							//将数据表格重新加载 调用datagrid 的reload方法
// 						})

// 						$.ajax({
// 							url:'',
// 							type:"post"
// 							data:{},
// 							success:function(data){
								
// 							},
// 							error:function(data){
								
// 							}
// 						})
					}
				})
				}
				
			}
			
			function doRestore(){
				alert("将取派员还原...");
			}
			//工具栏
			var toolbar = [  {
				id : 'button-search',	
				text : '搜索',
				iconCls : 'icon-search',
				handler : function(){
					$("#searchWindow").window("open")
				}
			},{
				id : 'button-add',	
				text : '增加',
				iconCls : 'icon-add',
				handler : doAdd
			}, {
				id : 'button-edit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : doEdit
			},
			<shiro:hasPermission name="courier_delete">
			{
				id : 'button-delete',
				text : '作废',
				iconCls : 'icon-cancel',
				handler : doDelete
			},
			</shiro:hasPermission>
			{
				id : 'button-restore',
				text : '还原',
				iconCls : 'icon-save',
				handler : doRestore
			}];
			// 定义列
			var columns = [ [ {
				field : 'id',
				checkbox : true,
			},{
				field : 'courierNum',
				title : '工号',
				width : 80,
				align : 'center'
			},{
				field : 'name',
				title : '姓名',
				width : 80,
				align : 'center'
			}, {
				field : 'telephone',
				title : '手机号',
				width : 120,
				align : 'center'
			}, {
				field : 'checkPwd',
				title : '查台密码',
				width : 120,
				align : 'center'
			}, {
				field : 'pda',
				title : 'PDA号',
				width : 120,
				align : 'center'
			}, {
				field : 'standard.name',
				title : '取派标准',
				width : 120,
				align : 'center',
				formatter : function(data,row, index){
					//行记录数据  当返回数据是对象时候，使用第二个参数
					if(row.standard != null){
						return row.standard.name;
					}
					return "";
				}
			}, {
				field : 'type',
				title : '取派员类型',
				width : 120,
				align : 'center'
			}, {
				field : 'company',
				title : '所属单位',
				width : 200,
				align : 'center'
			}, {
				field : 'deltag',
				title : '是否作废',
				width : 80,
				align : 'center',
				formatter : function(data,row, index){//格式化函数
					//value: 字段值。 当返回json数据值是简单类型使用第一个参数
					if(data=="0"){
						return "正常使用"
					}else{
						return "已作废";
					}
				}
			}, {
				field : 'vehicleType',
				title : '车型',
				width : 100,
				align : 'center'
			}, {
				field : 'vehicleNum',
				title : '车牌号',
				width : 120,
				align : 'center'
			} ] ];
			
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				
				// 取派员信息表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
					pageList: [30,50,100],
					pagination : true,
					toolbar : toolbar,
					url : "${pageContext.request.contextPath}/courierAction_pageQuery.action",
					idField : 'id',
					columns : columns,
					onDblClickRow : doDblClickRow
				});
				
				// 添加取派员窗口
				$('#addWindow').window({
			        title: '添加取派员',
			        width: 800,
			        modal: true,
			        shadow: true,
			        closed: true,
			        height: 400,
			        resizable:false
			    });
				
			});
		
			function doDblClickRow(){
				alert("双击表格数据...");
			}
		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="grid"></table>
		</div>
		
		<div class="easyui-window" title="对收派员进行添加或者修改" id="addWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
			<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
					<script type="text/javascript">
						$("#save").click(function(){
							//先做表单校验
							var r = $("#courierForm").form("validate");
							if(r){
								$("#courierForm").submit();
							}
						})
					</script>
				</div>
			</div>

			<div region="center" style="overflow:auto;padding:5px;" border="false">
				<form id="courierForm" action="${pageContext.request.contextPath}/courierAction_save.action" method="post">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="4">收派员信息</td>
						</tr>
						<tr>
							<td>快递员工号</td>
							<td>
								<input type="text" name="courierNum" class="easyui-validatebox" required="true" />
							</td>
							<td>姓名</td>
							<td>
								<input type="text" name="name" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>手机</td>
							<td>
								<input type="text" name="telephone" class="easyui-validatebox" required="true" />
							</td>
							<td>所属单位</td>
							<td>
								<input type="text" name="company" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>查台密码</td>
							<td>
								<input type="text" name="checkPwd" class="easyui-validatebox" required="true" />
							</td>
							<td>PDA号码</td>
							<td>
								<input type="text" name="pda" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>快递员类型</td>
							<td>
								<input type="text" name="type" class="easyui-validatebox" required="true" />
							</td>
							<td>取派标准</td>
							<td>
								<input type="text" name="standard.id" 
										class="easyui-combobox" 
										data-options="required:true,valueField:'id',textField:'name',
											url:'${pageContext.request.contextPath}/standardAction_findAll.action'"/>
							</td>
						</tr>
						<tr>
							<td>车型</td>
							<td>
								<input type="text" name="vehicleType" class="easyui-validatebox" required="true" />
							</td>
							<td>车牌号</td>
							<td>
								<input type="text" name="vehicleNum" class="easyui-validatebox" required="true" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<!-- 查询快递员-->
		<div class="easyui-window" title="查询快递员窗口" closed="true" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="width: 400px; top:40px;left:200px">
			<div style="overflow:auto;padding:5px;" border="false">
				<form id="searchForm" action="">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">查询条件</td>
						</tr>
						<tr>
							<td>工号</td>
							<td>
								<input type="text" name="courierNum" />
							</td>
						</tr>
						<tr>
							<td>收派标准</td>
							<td>
								<input type="text" name="standard.name" />
							</td>
						</tr>
						<tr>
							<td>所属单位</td>
							<td>
								<input type="text" name="company" />
							</td>
						</tr>
						<tr>
							<td>类型</td>
							<td>
								<input type="text" name="type" />
							</td>
						</tr>
						<tr>
							<td colspan="2"><a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
							<script type="text/javascript">
								$("#searchBtn").click(function(){
									//第一步：获取表单中所有输入项值（查询条件值）
									var condition = $("#searchForm").serializeJson();
									console.info(condition);
									//第二步：发送请求 提交参数（1、分页需要page,rows  2、查询条件）--让datagrid再次发出请求可以提交额外参数
									$('#grid').datagrid('load', condition); 
									//将查询窗口关闭
									$("#searchWindow").window("close")
								})
								
								//将表单中所有输入项（必须有name） 转为json对象形式  {"input的name","input中value"}
								$.fn.serializeJson=function(){  
						            var serializeObj={};  
						            var array=this.serializeArray();  
						            var str=this.serialize();  
						            $(array).each(function(){  
						                if(serializeObj[this.name]){  
						                    if($.isArray(serializeObj[this.name])){  
						                        serializeObj[this.name].push(this.value);  
						                    }else{  
						                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
						                    }  
						                }else{  
						                    serializeObj[this.name]=this.value;   
						                }  
						            });  
						            return serializeObj;  
						        }; 
							</script>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>

</html>