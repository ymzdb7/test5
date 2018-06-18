var content;
var curWwwPath=window.document.location.href;
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
var localhostPath=curWwwPath.substring(0,pos);
$(document).ready(function () {
/*	getAddress();*/
	
    $("#jqGrid").jqGrid({
        url: baseURL + 'market/order/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 20, key: true},
			{ label: '订单收货人', name: 'orConsignee', width: 30 }, 
			{ label: '电话', name: 'orderPhone', width: 50 }, 
			{ label: '下单时间', name: 'orderTime', width: 55 }, 
			{ label: '属地', name: 'address', width: 50 }, 
			{ label: '订单号', name: 'orderNumber', width: 50 }, 
			
			{ label: '订单状态', name: 'orderState', width: 45,formatter:function(value,options,row){
				if(value==1){
					
					return '<span>未付款</span>'
				}
				if(value==2){
					
					return '<span>已付款</span>'
				}
				if(value==3){
					
					return '<span>已提货</span>'
				}
				
				if(value==4){
					
					return '<span>订单取消</span>'
				}
				
			}},
			{ label: '详情', name: 'id', width: 30, formatter: function(value, options, row){
				var detail="<input type='button' value='查看'  onclick='btn_detail(\""+ value + "\")'>";
                return detail;
			}},
			{ label: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作', name: 'id', width: 60, formatter: function(value, options, row){
				var updateState1="<input type='button' value='已提货'  onclick='updateState1(\""+ value + "\")'>";
				var updateState2="<input type='button' value='订单取消'  onclick='updateState2(\""+ value + "\")'>";
                return updateState1+"&nbsp;&nbsp;"+updateState2;
			}}
		
			],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
 
});


var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			/*address: null*/
			orderState:null
			
		},
		order:{},
		orderO:{},
		goods:{},
		/*orderGoodsO:{},*/
		showList: true,
		showListNo: false,
		show:false,
		title:null,
		orderState:["未付款","已付款","已提货","订单取消"]
		/*addresses:{}*/
},
	methods: {
		query: function () {
			vm.reload();
		},
		
	del: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var boo=confirm('确定要删除选中的记录？');
			if(boo==true){
			/*confirm('确定要删除选中的记录？', function(){*/
				$.ajax({
					type: "POST",
				    url: baseURL + "market/order/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功');
                                vm.reload();
							
						}else{
							alert(r.msg);
						}
					}
				});
			/*});*/}
		},

		
		saveOrUpdate: function () {
       /*     if(vm.validator()){
                return ;
            }*/
          
			var url = vm.orderO.id == null ? "market/order/save" : "market/order/update";
			var form = new FormData(document.getElementById("orderForm"));
			$.ajax({
				type: "POST",
			    url: baseURL + url,
			    data:form,
			    contentType: false,
			    processData:false,
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功');
							vm.reload();
						
					}else{
						alert(r.msg);
					}
				}
			});
		},
		
		reload: function (event) {
			vm.showList = true;
			vm.show=false;
			vm.showListNo=false;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'orderState': vm.q.orderState},
				
                page:page
            }).trigger("reloadGrid");
		
		}
		
	/*	validator: function () {
		  if(isBlank(vm.goods.goodsName)){
	                alert("商品名不能为空");
	                return true;
	       }
        }*/
	}
});

//修改订单状态为已提货
function updateState1(id){
	
	$.get(baseURL+"market/order/orderState1/"+id,function(r){
		
	if(r.order11.orderState==3||r.order11.orderState==4){
		alert("只可对订单状态未付款、已付款进行操作!");
		
	}else{
		 if(confirm('确定对方已付款了吗?')) 
		    { 
			 
				$.get(baseURL+"market/order/updateState1/"+id,function(r){
					
					if(r.code === 0){
						/*alert('操作成功');*/
						 	vm.reload();
						
					}else{
						alert(r.msg);
					}
				});
		        return true; 
		        vm.reload();
		    } 
		    return false; 
		}
	});

}
function updateState2(id){
	$.get(baseURL+"market/order/orderState1/"+id,function(r){	
		if(r.order11.orderState==3||r.order11.orderState==4){
			alert("只可对订单状态未付款、已付款进行操作!");
			
		}else{
			
			 if(confirm('确定取消订单吗?')) 
			    { 
			$.get(baseURL+"market/order/updateState2/"+id,function(r){
				if(r.code === 0){
					alert(r.msg);
						vm.reload();
					
				}else{
					alert(r.msg);
				}
			});
			  return true; 
		        vm.reload();
			
			    } 
			    return false; 
		}
	});
}




function btn_detail(id){	
	
	$.get(baseURL +"market/order/detail?id="+id,function(r){
		
		if(r.goods!=null){
		vm.showList=false;
		vm.showListNo=true;
		/*	vm.show=false;*/
		/*vm.orderGoodsO=r.details;*/
	
		vm.goods=r.goods;
	
		vm.order=r.order;
		
		}
	});
/*	$.get(baseURL +"station/sys/list",function(r){
		vm.userList=r.userList;
	});*/
}

/*
function getAddress(){
	$.ajax({
		type: "GET",
	    url: "http://211.149.174.103:8080/wash/notice/address/list",
	    dataType:'jsonp',  
        jsonp:"callback",
        data:{"token":localStorage.getItem("token")},
	    success: function(r){
	    	if(r.code === 0){
	    		vm.addresses= r.addressList;
			}else{
				alert(r.msg);
			}
		}
	});
//	getLoginAddress();
}
*/
