var content;
var curWwwPath=window.document.location.href;
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
var localhostPath=curWwwPath.substring(0,pos);
$(document).ready(function () {
	
	getType();
	getAddress();
    $("#jqGrid").jqGrid({
        url: baseURL + 'market/goods/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 30, key: true},
			{ label: '图片', name: 'goodsPicPath', width: 20 ,formatter:showPic}, 
			{ label: '商品名', name: 'goodsName', width: 30 }, 
			{ label: '商品类型', name: 'typeName', width: 30 }, 
			{ label: '商品价格', name: 'goodsPrice', width: 25 },
			{ label: '库存数量', name: 'counts', width: 25 },
			{ label: '创建时间', name: 'goodsCreatetime', width: 50 },
			{ label: '商品描述', name: 'goodsIntroduce', width: 50 },
			{ label: '商品状态', name: 'goodsStatus', width: 50,formatter:function(value,options,row){
				if(value==1){
					
					return '<span>商品下架</span>'
				}
				if(value==2){
					
					return '<span>商品上架</span>'
				}
				
				
			}},			
		/*	{ label: '商品开始销售时间', name: 'goodsSaleStarttime', width: 90 },
			{ label: '商品结束销售时间', name: 'goodsSaleEndtime', width: 90 }, 
			{ label: '链接商品详情页面', name: 'goodsUrl', width: 150 }*/
			{ label: '商品开始销售时间', name: 'goodsSaleStarttime', width: 60 },
			{ label: '商品结束销售时间', name: 'goodsSaleEndtime', width: 60 }
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
   
    function showPic(cellvalue, options, rowObject){
    	var str = '<img src="'+localhostPath+'/file/read/readImg?filePath='+rowObject.goodsPicPath +'"  width="42" height="42">';
    	return str;
    }


   /* function operate(cellvalue, options, rowObject){
    	var str = '<button type="button">评价管理</button> ';
    	return str;
    }*/
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			goodsName: null
			
		},
		showList: true,
		showListNo:false,
		show1:false,
		show2:false,
		
		title:null,

		goods :{
			
			/*goodsSaleStarttime1:null,
			goodsSaleEndtime1:null,*/
			goodsStatus:2
			
		},
		type:{},
		calculate:{},
		addresses: {}
		
		
	},
	methods: {
		query: function () {
			vm.reload();
		},
		
	add: function(){
		time();
		time1();
		vm.goods.goodsSaleStarttime1=$('#goodsSaleStarttime1').val();
		vm.goods.goodsSaleEndtime1=$('#goodsSaleEndtime1').val();
			vm.showList = false;
			vm.showListNo=true;
			vm.show1=true;
			vm.show2=false;
			vm.title = "新增";
			vm.goods = {goodsStatus:2};
			var obj = document.getElementById("addImg");
			addImg.innerHTML  = "";
			getAddress();
			getType();
			getCalculate();
			
		},
			update: function () {
				time();
				time1();
				vm.goods.goodsSaleStarttime1=$('#goodsSaleStarttime1').val();
				vm.goods.goodsSaleEndtime1=$('#goodsSaleEndtime1').val();
				
				getCalculate();
			var obj = document.getElementById("addImg");
			addImg.innerHTML  = "";
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
            $.get(baseURL + "market/goods/info/"+id, function(r){
            	 vm.showList = false;
                 vm.showListNo=true;
                 vm.show1=false;
                 vm.show2=true;
            	getAddress();
            	 getType();           	
                vm.title = "修改";
               
                vm.goods = r.goods;
               var divImg = document.getElementById("addImg");
                var img = document.createElement("img");
            　　　　		//设置 img 图片地址
            　　　　		img.src = localhostPath+'/file/read/readImg?filePath='+r.goods.goodsPicPath;
            　　　　		img.width='50';
            　　　　		img.id = 'goodsPic';
            　　　　		img.height = '50'
            　　　　		divImg.appendChild(img);
            　　　　		
                
            });
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
				    url: baseURL + "market/goods/delete",
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
			controlLen();
           if(vm.goods.id == null){
			
			if(vm.validator()){
                return ;
            }
			}
           if(vm.goods.id != null){
        	   if(vm.validator1()){
                   return ;
               }
           }
          
			var url = vm.goods.id == null ? "market/goods/save" : "market/goods/update";
			var form = new FormData(document.getElementById("goodsForm"));
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
		
		
	/*	getDishes: function(id){
			$.get(baseURL + "restaurant/dishes/info/"+id, function(r){
				vm.dishes = r.dishes;
			});
		},*/
		reload: function (event) {
			vm.showList = true;
			vm.showListNo=false;
			vm.show1=false;
			vm.show2=false;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'goodsName': vm.q.goodsName},
				
                page:page
            }).trigger("reloadGrid");
			
		},
		
		validator1: function () {
			  if(isBlank(vm.goods.goodsName)){
		                alert("商品名不能为空");
		                return true;
		       }
			  if(isBlank(vm.goods.typeName)){
	              alert("商品类型不能为空");
	              return true;
			  }
			  if(isBlank(vm.goods.goodsPrice1)){
	              alert("商品价格不能为空");
	              return true;
			  }
			  var oInp = document.getElementById('inp');
				 if(isNaN(Number(oInp.value))){  //当输入不是数字的时候，Number后返回的值是NaN;然后用isNaN判断。
				            alert('商品价格请填入数字！');
				            return true;
				}
				 
			  if(isBlank(vm.goods.calculateName)){
	              alert("计量单位不能为空");
	              return true;
			  }
			  var oInp = document.getElementById('inp1');
				 if(isNaN(Number(oInp.value))){  //当输入不是数字的时候，Number后返回的值是NaN;然后用isNaN判断。
				            alert('计量单位数量请填入数字！');
				            return true;
				}	
				 
				 
				 
				 
				 
				 if(isBlank(vm.goods.address)){
	              alert("属地不能为空");
	              return true;
			  }
			  
			  if(isBlank(vm.goods.counts)){
	              alert("库存数量不能为空");
	              return true;
			  }
			  var oInpoo = document.getElementById('cou');
				 if(isNaN(Number(oInpoo.value))){  //当输入不是数字的时候，Number后返回的值是NaN;然后用isNaN判断。
				            alert('库存数量请填入数字！');
				            return true;
				}	
				 
				  var st=document.getElementById("goodsSaleStarttime1").value;
				  var et=document.getElementById("goodsSaleEndtime1").value;
					if(isBlank(st)){
			              alert("商品开始销售时间不能为空");
			              return true;
					  }
					if(isBlank(et)){
			              alert("商品结束销售时间不能为空");
			              return true;
					  }
					 if(st>et){
						   alert("商品开始销售时间必须小于商品结束销售时间");
						    return true;
					  }
						 
			  
	        },
		
		
	        
	        
		
		validator: function () {
		  if(isBlank(vm.goods.goodsName)){
	                alert("商品名不能为空");
	                return true;
	       }
		  if(isBlank(vm.goods.typeId1)){
              alert("商品类型不能为空");
              return true;
		  }
		  
		  
		
		  if(isBlank(vm.goods.goodsPrice1)){
              alert("商品价格不能为空");
              return true;
		  }
		  
		  var oInp = document.getElementById('inp');
			 if(isNaN(Number(oInp.value))){  //当输入不是数字的时候，Number后返回的值是NaN;然后用isNaN判断。
			            alert('商品价格请填入数字！');
			            return true;
			}
		 
			 
			 if(isBlank(vm.goods.priceCounts)){
	              alert("计量单位数量不能为空");
	              return true;
			  }
			  
			  var oInp = document.getElementById('inp1');
				 if(isNaN(Number(oInp.value))){  //当输入不是数字的时候，Number后返回的值是NaN;然后用isNaN判断。
				            alert('计量单位数量请填入数字！');
				            return true;
				}	 
			 
			 
			if(isBlank(vm.goods.calculateName)){
              alert("计量单位不能为空");
              return true;
		  }
		  
		
			 
			  var fileVal = document.getElementById("f_file").value;
			  if(!fileVal){
			      alert("请上传图片！");
			      return true;
			  }
			   
			  var st=document.getElementById("goodsSaleStarttime1").value;
			  var et=document.getElementById("goodsSaleEndtime1").value;
				if(isBlank(st)){
		              alert("商品开始销售时间不能为空");
		              return true;
				  }
				if(isBlank(et)){
		              alert("商品结束销售时间不能为空");
		              return true;
				  }
				 if(st>et){
					   alert("商品开始销售时间必须小于商品结束销售时间");
					    return true;
				  }
					 
			  
			  
		  if(isBlank(vm.goods.address)){
              alert("属地不能为空");
              return true;
		  }
		  
		/*  
		  var str = document.getElementById("f_file").value;
		  if(str.length==0)
		  {
		  alert("请选择上传");
		  return false;
		  }*/
	
		   
		  
		  
	
		  if(isBlank(vm.goods.counts)){
              alert("库存数量不能为空");
              return true;
		  }
		  
		  var oInpooo = document.getElementById('cou');
			 if(isNaN(Number(oInpooo.value))){  //当输入不是数字的时候，Number后返回的值是NaN;然后用isNaN判断。
			            alert('库存数量请填入数字！');
			            return true;
			}	
		/*  if(isBlank(vm.goods.goodsSaleStarttime1)){
              alert("商品开始销售时间不能为空");
              return true;
		  }
		  if(isBlank(vm.goods.goodsSaleEndtime1)){
              alert("商品结束销售时间不能为空");
              return true;
		  }*/
		  
		  
        }
	}
});



 function getType() {
	$.get(baseURL + "market/goods/infoType", function(r){
		vm.type = r.type;
	});
}

 
 function getCalculate() {
		$.get(baseURL + "market/goods/infoCalculate", function(r){
			vm.calculate = r.calculate;
		});
	}


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
	
}

function controlLen(){
    //获取input输入框元素
    var inputText = document.getElementById('mytext').value;
    if(inputText.length > 10){
        var text = inputText.substring(0,10);
        document.getElementById('mytext').value = text;//从新设置input输入框的值
        alert("最多输入10个字符");
    }
}


function time(){
	$('#datetimepicker').datetimepicker({
			 language: 'zh-CN',
			 format: 'yyyy-mm-dd hh:ii',
		     autoclose: true,
		     minView: 0,
		     todayBtn:1,
			 todayHighlight:1,
			 weekStart:1,
			 forceParse:0,
			 minuteStep:1
		});
}

function time1(){
	$('#datetimepicker1').datetimepicker({
				 language: 'zh-CN',
				 format: 'yyyy-mm-dd hh:ii',
			     autoclose: true,
			     minView: 0,
			     todayBtn:1,
				 todayHighlight:1,
				 weekStart:1,
				 forceParse:0,
				 minuteStep:1
	});
}

