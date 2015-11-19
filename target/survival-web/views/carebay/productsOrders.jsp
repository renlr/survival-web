<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>已售商品</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel="stylesheet" href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#products_grid").datagrid({
			url : "${pageContext.request.contextPath}/products/readPagesOrders.do",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : true,
			striped : true,
			fitColumns : true, 
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			pageSize: 30,
			pageList: [30,40,50],
			sortname : 'id',
			toolbar : [{
				id : 'btnbuy',
				text : "订单完成",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/products/editStatus.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id+"&status=1",
				          success: function(data){
				        	 $("#products_grid").datagrid("reload");
				          },
						  error : function(data){
						  	alert("订单完成操作失败!");
						  }
					});
				}
			},'-',{
				id : 'btnfail',
				text : "订单失败",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#products_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/products/editStatus.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id+"&status=2",
				          success: function(data){
				        	 $("#products_grid").datagrid("reload");
				          },
						  error : function(data){
						  	alert("订单失败操作失败!");
						  }
					});
				}
			},'-',{
				id : 'btnsearch',
				text : "高级搜索",
				iconCls : 'icon-search',
				handler : function() {
					$("#search_dialog").dialog({
   						title:"高级搜索",
   						show:"slide",
   						resizable:true,
   						closed: false,
   						buttons:[{
   							text:"导出结果",
   							handler:function(){
								var orderNo = $("#orderNo").val();  								
   								var user = $("#user").combogrid("getValue");
								var tranStatus = $("#tranStatus").combobox("getValue");
								var chamber = $("#chamber").combobox("getValue");
								var beginDT = $("#beginDT").datetimebox("getValue");
								var endDT = $("#endDT").datetimebox("getValue");
								$("#search_dialog").dialog("close");
								location.href = "${pageContext.request.contextPath}/products/dcExcel.do?orderNo="+orderNo+"&user="+user+"&tranStatus="+tranStatus+"&chamber="+chamber+"&beginDT="+beginDT+"&endDT="+endDT;
   							}
   						},{
   							text : "搜索",
   							handler:function(){
								var orderNo = $("#orderNo").val();  								
   								var user = $("#user").combogrid("getValue");
								var chamber = $("#chamber").combobox("getValue");
								var tranStatus = $("#tranStatus").combobox("getValue");
								var beginDT = $("#beginDT").datetimebox("getValue");
								var endDT = $("#endDT").datetimebox("getValue");
								$("#products_grid").datagrid("reload", {"orderNo":orderNo,"userId":user,"tranStatus":tranStatus,"chamber":chamber,"beginDT":beginDT,"endDT":endDT});
								$("#orderNo").val("");
								$("#user").combogrid("setValue","");
								$("#chamber").combobox("setValue","");
								$("#tranStatus").combobox("setValue","-1");
								$("#beginDT").datetimebox("setValue","");
								$("#endDT").datetimebox("setValue","");
								$("#search_dialog").dialog("close");
   							}
   						},{
   							text:"关闭",
   							handler:function(){
   								$("#orderNo").val("");
								$("#user").combogrid("setValue","");
								$("#tranStatus").combobox("setValue","-1");
								$("#beginDT").datetimebox("setValue","");
								$("#endDT").datetimebox("setValue","");
   								$("#search_dialog").dialog("close");
   							}
   						}]
   					});
				}
			}],
			columns : [[{
				field : "orderNo",
				title : "订单编号",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "createDT",
				title : "成交时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(date,rec){
					return date.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			},{
				field : "user",
				title : "购买用户",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(rec.user != null)
						return rec.user.name;
					return "";
				}
			},{
				field : "chamber",
				title : "所在会所",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "totalPrice",
				title : "实收款(元)",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "tranStatus",
				title : "交易状态",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var htmls = "";
					if(value == 0){
						htmls = "等待处理";
					}else if(value == 1){
						htmls = "订单完成";
					}else if(value == 2){
						htmls = "订单失败";
					}
					return htmls;
				}
			}]],
			view: detailview,
			detailFormatter:function(index,row){
                 return "<div style='padding:2px'><table class='ddv'></table></div>";
            },
            onExpandRow: function(index,row){
                 var ddv = $(this).datagrid("getRowDetail",index).find("table.ddv");
                 ddv.datagrid({
                     url:"${pageContext.request.contextPath}/products/readOrdersDetails.do?gid="+row.id,
                     fitColumns:true,
                     singleSelect:true,
                     rownumbers:true,
                     loadMsg:"正在加载订单详细...",
                     height:"auto",
                     toolbar : [{
         				id : 'btnaddPd',
         				text : "添加商品",
         				iconCls : 'icon-add',
         				handler : function() {
         					$("#div_dialog").dialog({
	         					title:"添加商品",
	         					show:"slide",
	         					resizable:true,
	         					closed: false,
	         					buttons:[{
	         						text : "添加",
	         						handler:function(){
	         							var parentIds = row.id;
	         							var detailIds = $("#products").combogrid("getValue");
										var numbers = $("#numbers").numberbox("getValue");
										if(detailIds == null || detailIds == ""){
											alert("必须选择商品!");
											return;
										}
										if(numbers == null || numbers == 0){
											alert("商品数量不能为空，必须大于0!");
											return;
										}			
										$.ajax({
	                				          type: "POST",
	                				          dataType: "json",
	                				          url: "${pageContext.request.contextPath}/products/saveProductsOrders.do?t="+new Date().getTime(),
	                				          data: "parentIds="+parentIds+"&detailIds="+detailIds+"&numbers="+numbers,
	                				          success: function(data){
	                				        	$("#products").combogrid("setValue","");
	                				        	$("#numbers").numberbox("setValue","0");
	                				        	$("#div_dialog").dialog("close");
	                				        	ddv.datagrid("reload");
	                				          },
	                						  error : function(data){alert("添加订单商品失败!");}
	                					}); 
	         						}},{
	         						text:"关闭",
	         						handler:function(){
	         							$("#div_dialog").dialog("close");
	         						}
	         					}]
	         				});
         				}
         			}, '-', {
        				id : 'btnMid',
        				text : "数量",
        				iconCls : 'icon-edit',
        				handler : function() {
        					var row = ddv.datagrid("getSelections");
        					if($(row).length < 1 || $(row).length > 1){
        						alert("请选择记录，只能选取单行！","提示消息");
        		                return;
        					}
        					var id = row[0].id;
        					var readUrl = "${pageContext.request.contextPath}/products/readProductsOrdersDetails.do?t="+new Date().getTime();
        					$.ajax({
        				          type: "get",
        				          dataType: "json",
        				          url: readUrl,
        				          data: "id="+id,
        				          success: function(data){
        				        	  if(data != null){
        				        		  $("#nums").numberbox("setValue",data.number);
        				        		  $("#num_dialog").dialog({
          	         						title:"修改数量",
          	         						show:"slide",
          	         						resizable:true,
          	         						closed: false,
          	         						buttons:[{
          	         							text : "修改",
          	         							handler:function(){
          	         								var nums=$("#nums").numberbox("getValue"); 
          	         								if(nums == null || nums == 0){
          	         									alert("商品数量不能为空，必须大于0!");
          	         									return;
          	         								}
          	         								$.ajax({
	          	                				          type: "get",
	          	                				          dataType: "json",
	          	                				          url: "${pageContext.request.contextPath}/products/updateProductsOrdersDetails.do?t="+new Date().getTime(),
	          	                				          data: "id="+id+"&nums="+nums,
	          	                				          success: function(data){
	          	                				        	 ddv.datagrid("reload");
	          	                				        	$("#nums").numberbox("setValue","0");
	          	                				        	$("#num_dialog").dialog("close");
	          	                				          },
	          	                						  error : function(data){alert("修改商品数量失败!");}
	          	                					});      	         								
          	         								         								
          	         							}},{
          	         							text:"关闭",
          	         							handler:function(){
          	         								$("#nums").val("");
          	         								$("#num_dialog").dialog("close");
          	         							}
          	         						}]
          	         					});
        				        	  }
        				          },
        						  error : function(data){alert("修改商品数量失败!");}
        					});
        				}
        			}, '-', {
        				id : 'btnDel',
        				text : "删除",
        				iconCls : 'icon-no',
        				handler : function() {
        					var row = ddv.datagrid("getSelections");
        					if($(row).length < 1 || $(row).length > 1){
        						alert("请选择记录，只能选取单行！","提示消息");
        		                return;
        					}
        					var id = row[0].id;
        					var editurl = "${pageContext.request.contextPath}/products/deleteOrderDetails.do?t="+new Date().getTime();
        					$.ajax({
        				          type: "get",
        				          dataType: "json",
        				          url: editurl,
        				          data: "id="+id,
        				          success: function(data){
        				        	 ddv.datagrid("reload");
        				          },
        						  error : function(data){alert("删除订单商品失败!");}
        					});
        				}
        			}],
                    columns:[[
                         {field:"image",title:"图片",width:$(this).width() * 0.2,formatter:function(value,rec){return "<img src='"+rec.products.image+"' width='50' height='50'/>";}},
                         {field:"name",title:"名称",width:$(this).width() * 0.2,align:"left",formatter:function(value,rec){return rec.products.name;}},
                         {field:"sellPrice",title:"单价(元)",width:$(this).width() * 0.2,align:"left",formatter:function(value,rec){return rec.products.sellPrice;}},
                         {field:"number",title:"数量",width:$(this).width() * 0.2,align:"left"},
                         {field:"totals",title:"总计(元)",width:$(this).width() * 0.2,align:"left",formatter:function(value,rec){return rec.products.sellPrice * rec.number;}}
                     ]],
                     onResize:function(){
                         $('#products_grid').datagrid('fixDetailRowHeight',index);
                     },
                     onLoadSuccess:function(){
                         setTimeout(function(){
                             $('#products_grid').datagrid('fixDetailRowHeight',index);
                         },0);
                     }
                 });
                 $('#products_grid').datagrid('fixDetailRowHeight',index);
             }
		});
		$("#butClose").click(function(){
			$("#div_dialog").dialog("close");
		});
		$("#products").combogrid({
			panelWidth: 500,
			method : "POST",
			datatype : "json",
			url:"${pageContext.request.contextPath}/products/readPagesSkip.do",
			mode:"remote",
			fitColumns: true,
			rownumbers : true,
			pagination : true,
			idField:"id",
			textField:"name",
			pageSize: 5,
			pageList: [5,10,20,30,40,50],
			columns:[[
				{field:"name",title:"商品名称",width:120,sortable:true}
			]],
			keyHandler : {
                up: function() {},
                down: function() {},
                enter: function() {},
                query: function(q) {
                	if(q != null && q != ""){
                		$("#products").combogrid("grid").datagrid("reload", {"filter" : q});
                        $("#products").combogrid("setValue", q);
                	}
                }
            }
		});
		$("#user").combogrid({
			panelWidth: 500,
			method : "POST",
			datatype : "json",
			url:"${pageContext.request.contextPath}/care/readPagesSkip.do",
			mode:"remote",
			fitColumns: true,
			rownumbers : true,
			pagination : true,
			idField:"id",
			textField:"name",
			pageSize: 5,
			pageList: [5,10,20,30,40,50],
			columns:[[
				{field:"name",title:"商品名称",width:120,sortable:true}
			]],
			keyHandler : {
                up: function() {},
                down: function() {},
                enter: function() {},
                query: function(q) {
                	if(q != null && q != ""){
                		$("#user").combogrid("grid").datagrid("reload", {"filter" : q});
                        $("#user").combogrid("setValue", q);
                	}
                }
            }
		});
	});
</script>
</head>
<body>
	<table id="products_grid" title="已售商品"></table>
	<div id="div_dialog" style="height:170px;width:468px;">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" colspan="2">&nbsp;</td>
			</tr>
			<tr class="ji">
				<td class="rt">选择商品：<label style="color: red;">*</label></td>
				<td class="ji"><input id="products" name="products"></td>
			</tr>
			<tr class="ji">
				<td class="rt">商品数量：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="numbers" id="numbers" value="0" size="5" class="easyui-numberbox" data-options="min:0,precision:0"></td>
			</tr>
		</table>
	</div>
	<div id="num_dialog" style="height:170px;width:468px;">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" colspan="2">&nbsp;</td>
			</tr>
			<tr class="ji">
				<td class="rt">商品数量：<label style="color: red;">*</label></td>
				<td class="ji"><input type="text" name="nums" id="nums" value="0" size="5" class="easyui-numberbox" data-options="min:0,precision:0"></td>
			</tr>
			<tr class="ji">
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
				<td class="ji"><input type="text" name="orderNo" id="orderNo"></td>
				<td class="rt">所在会所：</td>
				<td class="ji">
					<input id="chamber" class="easyui-combobox" name="chamber" data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath}/chamber/readList.do'">
				</td>
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