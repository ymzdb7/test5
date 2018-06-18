var content;
var curWwwPath=window.document.location.href;
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
var localhostPath=curWwwPath.substring(0,pos);
$(document).ready(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'market/calculate/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 30, key: true},
			{ label: '计量单位名称', name: 'calculateName', width: 50 }

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
			calculateName: null
			
		},
		calculate : {},
		showList: true,
		title:null,
	},
	methods: {
		query: function () {
			vm.reload();
		},

		add: function(){
			
			vm.showList = false;
			vm.title = "新增";
			vm.calculate = {};
		},
		
		
		update: function () {
			var id = getSelectedRow();
			 
			if(id == null){
				return ;
			}
			
            $.get(baseURL + "market/calculate/info/"+id, function(r){
                vm.showList = false;
                vm.title = "修改";
             
                vm.calculate = r.calculate;
         });
		},
		
		del: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
		/*	var boo=confirm('确定要删除选中的记录？');
			if(boo==true){*/
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "market/calculate/delete",
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
			});/*}*/
		},
		
		saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }
      
			var url = vm.calculate.id== null ? "market/calculate/save" : "market/calculate/update";
			var form = new FormData(document.getElementById("calculateForm"));
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
		validator: function () {
			  if(isBlank(vm.calculate.calculateName)){
		                alert("计量单位名称不能为空");
		                return true;
		       }
	        },
		
		
		
		
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'calculateName': vm.q.calculateName},
				
                page:page
            }).trigger("reloadGrid");
		
		}
	}
});

