<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>月子套餐</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/docelltip.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#meal_grid").datagrid({
			url : "${pageContext.request.contextPath}/meal/readPages.do",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : true,
			striped : true,
			fitColumns : true, 
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			pageSize: pageSize > 30 ? pageSize : 30,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加月子套餐",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加月子套餐",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#meal_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许编辑！","提示消息");
		                return ;
		            }
		            var editurl = "${pageContext.request.contextPath}/meal/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#author").val(data.author);
				          	$("#productionMethods").val(data.productionMethods);
				          	$("#flavor").val(data.flavor);
				          	$("#heat").val(data.heat);
				          	$("#indexs").val(data.indexs);
				          	$("#describes").val(data.describes);
				          	$("#methodDescribes").val(data.methodDescribes);
				          	$("#price").numberbox("setValue",data.price);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#meal_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#meal_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/meal/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	if("success" == data.resultMessage){
					          	$("#meal_grid").datagrid("reload"); 
				          	}
				          },
						  error : function(data){
						  	$("#meal_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnEdit',
				text : "显示顺序",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#meal_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/meal/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id1").val(data.id);
				          	$("#indexs1").val(data.indexs);
				          	$("#index_dialog").dialog();
				          },
						  error : function(data){
						  	alert("修改显示顺序失败!");
						  }
					});
				}
			},'-', {
				id : 'btn-tsk',
				text : "定时任务",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#meal_grid").datagrid("getSelections");
		            if($(row).length < 1){
		                alert("请选择记录，至少选取一行！","提示消息");
		                return ;
		            }
		            var ids = "";
		            $.each(row,function(i,item){
		            	if(i)
		            		ids += ",";
		            	ids += item.id;
		            });
					$("#task_dialog").dialog({
     					title:"定时上下线任务",
     					show:"slide",
     					resizable:true,
     					closed: false,
     					buttons:[{
     						text : "确定",
     						handler:function(){
     							var onlineDT = $("#onlineDT").datetimebox("getValue");
								var offlineDT = $("#offlineDT").datetimebox("getValue");
								if(onlineDT == "" && offlineDT == ""){
									alert("定时上下线任务时间不能为空！");
									return;
								}
								$.ajax({
            				          type: "POST",
            				          dataType: "json",
            				          url: "${pageContext.request.contextPath}/meal/saveLineTask.do?t="+new Date().getTime(),
            				          data: "ids="+ids+"&onlineDT="+onlineDT+"&offlineDT="+offlineDT,
            				          success: function(data){
            				        	$("#onlineDT").datetimebox("setValue","");
            				        	$("#offlineDT").datetimebox("setValue","");
            				        	$("#task_dialog").dialog("close");
            				        	$("#meal_grid").datagrid("reload");
            				          },
            						  error : function(data){alert("添加定时任务失败!");}
            					}); 
     						}},{
     						text:"关闭",
     						handler:function(){
     							$("#task_dialog").dialog("close");
     						}
     					}]
     				});
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#meal_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许删除！","提示消息");
		                return ;
		            }
		            var delUrl = "${pageContext.request.contextPath}/meal/delete.do?t="+new Date().getTime();
					$("#delete_div").dialog({
						title : "删除提示",
						show:"slide",
			            height : 130,
						width : 200,
						modal : true,
						closeOnEscape : true,
						buttons: [{ 
						text: "确定", 
							handler: function() {
							$.ajax({
								type: "get",
								dataType: "json",
								url: delUrl,
								data: "id="+ids,//要发送的数据
								success: function(data){
									if("success" == data.resultMessage){
										$("#meal_grid").datagrid("reload"); 
								    }
								},
								error : function(data){
									$("#meal_grid").datagrid("reload"); 
								}
							});
							$("#delete_div").dialog("close");
						}}, {
							text: "取消", 
							handler: function() { 
								$("#delete_div").dialog("close");
							} 
						}] 
			       	});
				}
			}],
			columns : [[{
				field : "image",
				title : "套餐图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='200' height='100'/>";
				}
			},{
				field : "name",
				title : "套餐名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "author",
				title : "制作厨师",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "productionMethods",
				title : "套餐工艺",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "flavor",
				title : "套餐品味",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "heat",
				title : "套餐热量",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "price",
				title : "套餐价格",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "describes",
				title : "套餐介绍",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "methodDescribes",
				title : "烧法介绍",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "chamber",
				title : "所在会所",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "indexs",
				title : "显示顺序",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "online",
				title : "是否上线",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var hls = "";
					if(null != value){
						if(0 == value){
							hls = "未上线";
						}else if(1 == value){
							hls = "<font color='green'>上线中</font>";
						}else{
							hls = "<font color='red'>已下线</font>";
						}
					}
					if(rec.taskDT != null){
						var dts = rec.taskDT.split("|");	
						if(dts.length > 0 && dts[0] != null && dts[0] != ""){
							hls += "<br/>上线："+dts[0].toString().toDate().format("MM-dd hh:mm");
						}
						if(dts.length > 1 && dts[1] != null && dts[1] != ""){
							hls += "<br/>下线："+dts[1].toString().toDate().format("MM-dd hh:mm");
						}
					}
					return hls;
				}
			},{
				field : "toDetails",
				title : "套餐图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/meal/details.do?gid='+rec.id+'">添加图片</a>';
				}
			},{
				field : "createDT",
				title : "创建时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			}]],
			onLoadSuccess:function(data){
				$(this).datagrid('doCellTip',{'max-width':'300px','delay':500});
			}
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#author").val("");
			$("#productionMethods").val("");
			$("#flavor").val("");
			$("#heat").val("");
			$("#describes").val("");
			$("#methodDescribes").val("");
			$("#price").val("");
			$("#div_dialog").dialog("close");
		});
		$("#mealForm").submit(function(){
			var name = $("#name").val(); 
			var author = $("#author").val(); 
			var productionMethods = $("#productionMethods").val(); 
			var flavor = $("#flavor").val(); 
			var heat = $("#heat").val(); 
			var describes = $("#describes").val(); 
			var methodDescribes = $("#methodDescribes").val(); 
			var price = $("#price").numberbox("getValue"); 
			var indexs=$("#indexs").numberbox("getValue"); 
			
			if($.trim(name) == ""){
				alert("套餐名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if($.trim(author) == ""){
				alert("制作厨师不能为空！"); 
				$("#author").focus(); 
				return false; 
			}
			if($.trim(productionMethods) == ""){
				alert("制作工艺不能为空！"); 
				$("#productionMethods").focus(); 
				return false; 
			}
			if($.trim(flavor) == ""){
				alert("套餐口味不能为空！"); 
				$("#flavor").focus(); 
				return false; 
			}
			if($.trim(heat) == ""){
				alert("热量不能为空！"); 
				$("#heat").focus(); 
				return false; 
			}
			if($.trim(describes) == ""){
				alert("套餐介绍不能为空！"); 
				$("#describes").focus(); 
				return false; 
			}
			if($.trim(methodDescribes) == ""){
				alert("烧法介绍不能为空！"); 
				$("#methodDescribes").focus(); 
				return false; 
			}
			if($.trim(price) == ""){
				alert("套餐价格必须填写！"); 
				$("#price").focus(); 
				return false;
			}
			if($.trim(indexs) == ""){ 
				alert("显示顺序不能为空！"); 
				$("#indexs").focus(); 
				return false; 
			}
		});
		$("#indexsForm").submit(function(){
			var indexs=$("#indexs1").val();
			if($.trim(indexs) == ""){ 
				alert("显示顺序必须填写！"); 
				$("#indexs1").focus(); 
				return false; 
			}		
		});
		$("#butrs1").click(function(){
			$("#id1").val("");
			$("#indexs1").val("");
			$("#index_dialog").dialog("close");
		});
	});
</script>
</head>
<body>
	<table id="meal_grid" title="月子套餐"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/meal/save.do" method="post" id="mealForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">套餐名称：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="name" id="name" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">厨师：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="author" id="author" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">工艺：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="productionMethods" id="productionMethods" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">口味：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="flavor" id="flavor" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">热量：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="heat" id="heat" style="width:200px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">套餐介绍：<label style="color: red;">*</label></td>
				<td class="ji"><textarea id="describes" name="describes" style="height:100px;width:450px;"></textarea></td>
			</tr>
			<tr class="ji">
				<td class="rt">烧法介绍：<label style="color: red;">*</label></td>
				<td class="ji"><textarea id="methodDescribes" name="methodDescribes" style="height:100px;width:450px;"></textarea></td>
			</tr>
			<tr class="ji">
				<td class="rt">套餐价格：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="price" id="price" size="20" maxlength="50" class="easyui-numberbox input" data-options="min:0,precision:2"></td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="submit" id="buts" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="index_dialog" title="修改显示顺序">
		<form action="${pageContext.request.contextPath}/meal/saveIndexs1.do" method="post" id="indexsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs1" id="indexs1" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id1" id="id1">
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs1" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="task_dialog" title="定时上下线任务" style="height:205px;width:445px;">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" colspan="2">&nbsp;</td>
			</tr>
			<tr class="tr">
				<td class="rt">上线时间：</td>
				<td class="ji"><input type="text" class="easyui-datetimebox input" id="onlineDT" name="onlineDT" data-options="required:true,showSeconds:false" style="width:150px"></td>
			</tr>
			<tr class="tr">
				<td class="rt">下线时间：</td>
				<td class="ji"><input type="text" class="easyui-datetimebox input" id="offlineDT" name="offlineDT" data-options="required:true,showSeconds:false" style="width:150px"></td>
			</tr>
			<tr class="tr">
				<td class="rt" colspan="2">&nbsp;</td>
			</tr>
		</table>
	</div>
</body>
</html>