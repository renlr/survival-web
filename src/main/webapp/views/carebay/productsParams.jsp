<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>销售属性</title>
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
<script language="JavaScript" charset="utf-8" type="text/javascript" src="http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>
<script type="text/javascript">
	$.extend($.fn.datagrid.methods, {
	    editCell: function(jq,param){
	        return jq.each(function(){
	            var opts = $(this).datagrid("options");
	            var fields = $(this).datagrid("getColumnFields",true).concat($(this).datagrid("getColumnFields"));
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid("getColumnOption", fields[i]);
	                col.editor1 = col.editor;
	                if (fields[i] != param.field){
	                    col.editor = null;
	                }
	            }
	            $(this).datagrid("beginEdit", param.index);
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid("getColumnOption", fields[i]);
	                col.editor = col.editor1;
	            }
	        });
	    }
	});
	
	var values = undefined;
	var fields = undefined;
	var editIndex = undefined;
	function endEditing(){
	    if (editIndex == undefined){return true}
	    if ($("#products_grid").datagrid("validateRow", editIndex)){
			$("#products_grid").datagrid("endEdit", editIndex);
			var row = $("#products_grid").datagrid("getRows");
			var id = row[editIndex].id;
			var $value = row[editIndex][fields];
			if($value != null && $value != "" && values != $value){
				$.ajax({
					type: "post",
					dataType: "json",
					url: "${pageContext.request.contextPath}/products/saveParamsFields.do?t="+new Date().getTime(),
					data: "id="+id+"&field="+fields+"&value="+$value,
					success: function(data){
				    	if("success" == data.resultMessage){}
				    },
					error : function(data){}
				});				
			}
			values = undefined;
			fields = undefined;
	        editIndex = undefined;
	        return true;
	    } else {
	        return false;
	    }
	}
	function clickCell(index, field,value){
	    if (endEditing()){
	        $("#products_grid").datagrid("selectRow", index).datagrid("editCell", {index:index,field:field});
	        values = value;
	        fields = field;
	        editIndex = index;
	    }
	}
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#products_grid").datagrid({
			url : "${pageContext.request.contextPath}/products/readPagesParams.do?groupId=${duct.id}",
			datatype : "json",
			mtype : "POST",
			nowrap : false,
			striped : true,
			fitColumns : true, 
			fit : true,
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			pagination : true,
			sortname : "id",
			singleSelect : true,
			rownumbers : true,
			onLoadSuccess : function(data){
				if(data.rows.length > 0){
					mergeCellsFields("products_grid","name1,auts1");
				}
			},
			toolbar : [ '-', {
				id : "btnClearSelections",
				text : "添加销售属性",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加销售属性",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : "editSelections",
				text : "编辑模式",
				iconCls : "icon-edit",
				handler : function() {
					$("#products_grid").datagrid({
						onClickCell : function(index,field,value){
							clickCell(index,field,value);
						},
						onLoadSuccess : function(data){
						}
					});
					$("#products_grid").datagrid("reload");
				}
			},'-', {
				id : "editSelections",
				text : "视图模式",
				iconCls : "icon-redo",
				handler : function() {
					$("#products_grid").datagrid({
						onClickCell : function(index,field,value){
						},
						onLoadSuccess : function(data){
							mergeCellsFields("products_grid","name1,auts1");
						}
					});
					$("#products_grid").datagrid("reload");
				}
			},'-', {
				id : "btnDel",
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/products/deleteParams.do?t="+new Date().getTime();
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
							          		$("#products_grid").datagrid("reload");
							          	}
							          },
									  error : function(data){
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
				field : "name1",
				title : "属性名称",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true,
			},{
				field : "auts1",
				title : "属性值1",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true,
				editor : "text"
			},{
				field : "auts2",
				title : "属性值2",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true,
				editor : "text"
			},{
				field : "costPrice",
				title : "成本价",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:2
					}
				}
			},{
				field : "bazaarPrice",
				title : "市场价",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:2
					}
				}
			},{
				field : "sellPrice",
				title : "销售价",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:2
					}
				}
			},{
				field : "inventory",
				title : "库存数量",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:0
					}
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
			}]]
		});
		$($("#products_grid").datagrid("getPager")).pagination({
			beforePageText : "第",
			afterPageText : "页    共 {pages} 页",
			displayMsg : "当前显示 {from} - {to} 条记录   共 {total} 条记录"
		});
		$("#groupId").val("${duct.id}");
		$("#butClose").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#price").val("");
			$("#inventory").val("");
			$("#evaluate").val("");
			$("#integral").val("");
			$("#div_dialog").dialog("close");
		});
		$("#butSubmit").click(function(){
			$("#productsForm").form("submit",{
				onSubmit:function(){
					var name=$("#name1").val();
					if($.trim(name) == ""){ 
						alert("必须填写至少一个属性!"); 
						$("#name1").focus(); 
						return false; 
					} 
				},
				success:function(data){
					$("#div_dialog").dialog("close");
					$("#products_grid").datagrid("reload"); 
				}
			});
		});
		$("#butguige").click(function(){
			$("#guige").append(guigeHtml);
		});
	});
	var guigeHtml = "<tr>"
		+"<td><input type=\"text\" name=\"nameVal1\" id=\"nameVal1\" size=\"5\" class=\"input\"></td>"
		+"<td><input type=\"text\" name=\"nameVal2\" id=\"nameVal2\" size=\"5\" class=\"input\"></td>"
		+"<td><input type=\"text\" name=\"costPrice\" id=\"costPrice\" size=\"5\" class=\"easyui-numberbox input\" data-options=\"min:0,precision:2\"></td>"
		+"<td><input type=\"text\" name=\"bazaarPrice\" id=\"bazaarPrice\" size=\"5\" class=\"easyui-numberbox input\" data-options=\"min:0,precision:2\"></td>"
		+"<td><input type=\"text\" name=\"sellPrice\" id=\"sellPrice\" size=\"5\" class=\"easyui-numberbox input\" data-options=\"min:0,precision:2\"></td>"
		+"<td><input type=\"text\" name=\"inventory\" id=\"inventory\" size=\"5\" class=\"easyui-numberbox input\" data-options=\"min:0,precision:0\"></td>"
		+"<td>&nbsp;</td>"
		+"</tr>";
</script>
</head>
<body>
	<table id="products_grid" title="销售属性"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/products/saveParams.do" method="post" id="productsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt">属性参数：</td>
				<td class="ji">
				<div>
					<table cellspacing="0" border="0" style="visibility: visible;">
						<tr>
							<td align="center">
								<span><input type="text" name="name1" id="name1" size="5" class="input" value="颜色分类"><label style="color: red;">*</label></span>
							</td>
							<td align="center">
								<span><input type="text" name="name2" id="name2" size="5" class="input" value="重量分类"></span>
							</td>
							<td align="center"><span>成本价</span></td>
							<td align="center"><span>市场价</span></td>
							<td align="center"><span>销售价</span></td>
							<td align="center"><span>库存数量</span></td>
							<td>&nbsp;</td>
						</tr>
						<tbody id="guige">
						<tr>
							<td><input type="text" name="nameVal1" id="nameVal1" size="5" class="input"></td>
							<td><input type="text" name="nameVal2" id="nameVal2" size="5" class="input"></td>
							<td><input type="text" name="costPrice" id="costPrice" size="5" class="easyui-numberbox input" data-options="min:0,precision:2"></td>
							<td><input type="text" name="bazaarPrice" id="bazaarPrice" size="5" class="easyui-numberbox input" data-options="min:0,precision:2"></td>
							<td><input type="text" name="sellPrice" id="sellPrice" size="5" class="easyui-numberbox input" data-options="min:0,precision:2"></td>
							<td><input type="text" name="inventory" id="inventory" size="5" class="easyui-numberbox input" data-options="min:0,precision:0"></td>
							<td><input class="bginput" type="button" id="butguige" value="添加"></td>
						</tr>
						</tbody>
					</table>
				</div>
				</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="groupId" id="groupId"> 
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="button" id="butSubmit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>