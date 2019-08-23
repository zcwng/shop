
 
 <table class="layui-hide" id="test"></table>

<script>
  
  layui.table.render({
    elem: '#test'
    ,url:'/item/list'
    ,request:{
	    pageName: 'page' //页码的参数名称，默认：page
	    ,limitName: 'rows' //每页数据量的参数名，默认：limit
  	}
  	,response: {
	    statusName: 'code' //规定数据状态的字段名称，默认：code
	    ,statusCode: 200 //规定成功的状态码，默认：0
	    ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
	    ,countName: 'total' //规定数据总数的字段名称，默认：count
	    ,dataName: 'rows' //规定数据列表的字段名称，默认：data
  	} 
    ,cols: [[
      {type:'checkbox'}
      ,{field:'id', width:80, title: '商品ID', sort: true}
      ,{field:'title', width:80, title: '商品标题'}
      ,{field:'cid', width:80, title: '叶子类目', sort: true}
      ,{field:'sellPoint', width:80, title: '卖点'}
      ,{field:'price', title: '价格', minWidth: 100}
      ,{field:'num', width:80, title: '库存', sort: true}
      ,{field:'barcode', width:80, title: '条形码', sort: true}
      ,{field:'status', width:80, title: '状态'}
      ,{field:'created', width:135, title: '创建日期', sort: true}
      ,{field:'updated', width:135, title: '更新日期', sort: true}
    ]]
    ,page: true
  });

</script>
