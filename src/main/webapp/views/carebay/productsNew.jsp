<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>母婴商品</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/docelltip.js"></script>
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
			if($value != null && values != $value && $value != ""){
				$.ajax({
					type: "post",
					dataType: "json",
					url: "${pageContext.request.contextPath}/products/saveEditFields.do?t="+new Date().getTime(),
					data: "id="+id+"&field="+fields+"&value="+encodeURI($value),
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
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 50);
		$("#products_grid").datagrid({
			url : "${pageContext.request.contextPath}/products/readPages.do?gid=${gid}",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : true,
			striped : true,
			fitColumns : true, 
			rownumbers : true,
			pagination : true,
			singleSelect : false,
			pageSize: pageSize > 30 ? pageSize : 30,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			onClickCell : function(index,field,value){
				//clickCell(index,field,value);
			},
			onLoadSuccess:function(data){
				$(this).datagrid('doCellTip',{'max-width':'300px','delay':500});
			},
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加商品",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加商品",
						show:"slide",
						resizable:true,
						closed: false
					});
				}
			},'-', {
				id : 'btn-mid',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许编辑!");
		                return ;
		            }
		            var editurl = "${pageContext.request.contextPath}/products/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#bazaarPrice").numberbox("setValue",data.bazaarPrice);
				          	$("#sellPrice").numberbox("setValue",data.sellPrice);
				          	$("#imageDetails").val(data.imageDetails);
				          	$("#indexs").numberbox("setValue",data.indexs);
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	alert("修改商品分类失败!");
						  }
					});
				}
			},'-', {
				id : 'btn-mid',
				text : "设置折扣",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
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
		            $("#disIds").val(ids);
		            $("#discount_dialog").dialog({
						title:"设置折扣",
						show:"slide",
						resizable:true,
						closed: false
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "上|下 线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
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
		            var editurl = "${pageContext.request.contextPath}/products/online1.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+ids,
				          success: function(data){
				          	$("#products_grid").datagrid("reload"); 
				          },
						  error : function(data){
						  	$("#products_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btn-tsk',
				text : "定时任务",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
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
            				          url: "${pageContext.request.contextPath}/products/saveLineTask.do?t="+new Date().getTime(),
            				          data: "ids="+ids+"&onlineDT="+onlineDT+"&offlineDT="+offlineDT,
            				          success: function(data){
            				        	$("#onlineDT").datetimebox("setValue","");
            				        	$("#offlineDT").datetimebox("setValue","");
            				        	$("#task_dialog").dialog("close");
            				        	$("#products_grid").datagrid("reload");
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
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1){
			            alert("请选择记录，至少选取一行！","提示消息");
		                return ;
		            }
		            var ids = "";
		            $.each(row,function(i,item){
		            	if(i){
		            		ids += ",";
		            	}
		            	var online = item.online;
		            	if(online == 1){
		            		alert("上线中不允许删除!");
			                return;
		            	}
		            	ids += item.id;
		            });
		            var delUrl = "${pageContext.request.contextPath}/products/delete.do?t="+new Date().getTime();
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
			},'-', {
				id : 'btnEdit',
				text : "显示顺序",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/products/edit.do?t="+new Date().getTime();
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
				id : 'btnRedo',
				text : "返回上一级",
				iconCls : 'icon-redo',
				handler : function() {
					location.href = "${pageContext.request.contextPath}/productsCategory/show.do";
				}
			}],
			columns : [[{
				field : "ck",
				checkbox : true
			},{
				field : "image",
				title : "商品图标",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return "<img src='"+value+"' width='50' height='50'/>";
				}
			},{
				field : "name",
				title : "商品名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : "textarea"
			},{
				field : "category",
				title : "分类名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return rec.category.name;
				}
			},{
				field : "bazaarPrice",
				title : "商品价格",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:2
					}
				}
			},{
				field : "sellPrice",
				title : "折扣价格",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : {
					type:"numberbox",
					options:{
						precision:2
					}
				}
			},{
				field : "imageDetails",
				title : "商品介绍",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : "textarea"
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
				field : "images",
				title : "展示图片",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/products/addImages.do?id='+rec.id+'&pid=${gid}">展示图片</a>';
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
		$("#butClose").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#price").val("");
			$("#inventory").val("");
			$("#evaluate").val("");
			$("#integral").val("");
			$("#div_dialog").dialog("close");
		});
		$("#productsForm").submit(function(){
			var name=$("#name").val(); 
			//var category=$("#category").combobox("getValue");
			var bazaarPrice=$("#bazaarPrice").numberbox("getValue");
			var sellPrice=$("#sellPrice").numberbox("getValue");
			var imageDetails=$("#imageDetails").val(); 
			var indexs=$("#indexs").numberbox("getValue"); 
<%--			if($.trim(category) == ""){ --%>
<%--				alert("所属分类必须选择！"); --%>
<%--				$("#category").focus(); --%>
<%--				return false; --%>
<%--			}--%>
			if($.trim(name) == ""){ 
				alert("商品名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if($.trim(bazaarPrice) == ""){ 
				alert("商品价格不能为空！"); 
				$("#bazaarPrice").focus(); 
				return false; 
			}
			if($.trim(sellPrice) == ""){ 
				alert("折扣价格不能为空！"); 
				$("#sellPrice").focus(); 
				return false; 
			}
			if($.trim(imageDetails) == ""){ 
				alert("商品介绍不能为空！"); 
				$("#imageDetails").focus(); 
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
		$("#discountForm").submit(function(){
			var discount=$("#discount").val();
			if($.trim(discount) == ""){ 
				alert("折扣率必须填写！"); 
				$("#discount").focus(); 
				return false; 
			}		
		});
		$("#disbut").click(function(){
			$("#disIds").val("");
			$("#discount").val("");
			$("#discount_dialog").dialog("close");
		});
	});
</script>
</head>
<body>
	<table id="products_grid" title="母婴商品"></table>
	<div id="delete_div">确定删除所选内容?</div>
	<div id="tb" style="padding:3px">
	    <span>Item ID:</span><input id="itemid" style="line-height:26px;border:1px solid #ccc">
		<span>Product ID:</span><input id="productid" style="line-height:26px;border:1px solid #ccc">
		<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">Search</a>
	</div>
	<div id="div_dialog" style="height:360px;width:580px;">
		<form action="${pageContext.request.contextPath}/products/save.do" method="post" id="productsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
<%--			<tr class="ji">--%>
<%--				<td class="rt" align="right">所属分类：<label style="color: red;">*</label></td>--%>
<%--				<td class="ji" colspan="3"><input type="text" class="easyui-combobox input" name="category" id="category" data-options="min:0,precision:0"></td>--%>
<%--			</tr>--%>
			<tr class="ji">
				<td class="rt">商品名称：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="name" id="name" style="width:450px;" maxlength="200" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt">商品价格：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="bazaarPrice" id="bazaarPrice" size="20" maxlength="50" class="easyui-numberbox input" data-options="min:0,precision:2"></td>
			</tr>
			<tr class="tr">
				<td class="rt">折扣价格：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="sellPrice" id="sellPrice" size="20" maxlength="50" class="easyui-numberbox input" data-options="min:0,precision:2"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="top">商品介绍：<label style="color: red;">*</label></td>
				<td class="ji"><textarea id="imageDetails" name="imageDetails" style="height:100px;width:450px;"></textarea></td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs" id="indexs" size="5" class="easyui-numberbox" data-options="min:0,precision:0">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="4">
					<input type="hidden" name="id" id="id">
					<input type="hidden" name="gid" id="gid" value="${gid}"> 
					<input class="bginput" type="submit" id="butSubmit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="index_dialog" title="修改显示顺序">
		<form action="${pageContext.request.contextPath}/products/saveIndexs1.do" method="post" id="indexsForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">显示顺序：<label style="color: red;">*</label></td>
				<td><input type="text" name="indexs1" id="indexs1" size="4" maxlength="10" class="easyui-numberbox input">降序排列</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id1" id="id1">
					<input type="hidden" name="gid" id="gid" value="${gid}"> 
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs1" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="discount_dialog" title="设置折扣" style="height:180px;width:328px;">
		<form action="${pageContext.request.contextPath}/products/saveDiscount.do" method="post" id="discountForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" colspan="2">&nbsp;</td>
			</tr>
			<tr class="tr">
				<td class="rt">折扣：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="discount" id="discount" size="4" maxlength="10" class="easyui-numberbox input" data-options="min:0,precision:2">折(必须为整数，例如:95折,80折)</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="disIds" id="disIds">
					<input type="hidden" name="gid" id="gid" value="${gid}"> 
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="disbut" value="关闭">
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
	<div id="search_dialog">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" colspan="4">&nbsp;</td>
			</tr>
			<tr class="ji">
				<td class="rt">订单编号：</td>
				<td class="ji" colspan="3"><input type="text" name="orderNo" id="orderNo"></td>
			</tr>
			<tr class="ji">
				<td class="rt">馨月用户：</td>
				<td class="ji"><input type="text" id="user" name="user"></td>
				<td class="rt">订单状态：</td>
				<td class="ji">
					<select id=tranStatus class="easyui-combobox" name="tranStatus">
    					<option value="-1">选择状态</option>
    					<option value="0">等待处理</option>
        				<option value="1">订单完成</option>
	    				<option value="2">订单失败</option>
		    		</select>
				</td>
			</tr>
			<tr class="ji">
				<td class="rt">开始时间：</td>
				<td class="ji"><input type="text" class="easyui-datetimebox input" id="beginDT" name="beginDT" data-options="required:true,showSeconds:false" style="width:150px"></td>
				<td class="rt">结束时间：</td>
				<td class="ji"><input type="text" class="easyui-datetimebox input" id="endDT" name="endDT" data-options="required:true,showSeconds:false" style="width:150px"></td>
			</tr>
			<tr class="ji">
				<td class="rt" colspan="4">&nbsp;</td>
			</tr>
		</table>
	</div>
</body>
</html>