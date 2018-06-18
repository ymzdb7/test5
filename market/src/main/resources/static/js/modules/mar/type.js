var content;
var curWwwPath=window.document.location.href;
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
var localhostPath=curWwwPath.substring(0,pos);
$(document).ready(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'market/type/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'typeId', width: 30, key: true},
			{ label: '商品类型', name: 'typeName', width: 50 }

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
			typeName: null
			
		},
		type : {},
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
			vm.type = {};
		},
		
		
		update: function () {
		
			var id = getSelectedRow();
			
			if(id == null){
				return ;
			}
			
            $.get(baseURL + "market/type/info/"+id, function(r){
                vm.showList = false;
                vm.title = "修改";
              
                vm.type = r.type;
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
				    url: baseURL + "market/type/delete",
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
            if(vm.validator()){
                return ;
            }
        
			var url = vm.type.typeId== null ? "market/type/save" : "market/type/update";
			var form = new FormData(document.getElementById("typeForm"));
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
			  if(isBlank(vm.type.typeName)){
		                alert("商品类型不能为空");
		                return true;
		       }
	        },
		
		
		
		
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'typeName': vm.q.typeName},
				
                page:page
            }).trigger("reloadGrid");
			
		}
	}
});

