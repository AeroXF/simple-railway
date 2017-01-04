spa.train = (function(){
	var addTableLinkEvent = function(){
		$(".ticket-generate").click(function(){
			var params = { trainTag: $(this).attr("traintag"), trainNo: $(this).parent().parent().find("td:eq(0)").text() };
			var that = this;
			
			$.get(APP_PATH + "/admin/train/add/ticket", params, function(data){
				if(data == "success"){
					layer.open({icon:1, title:'生成车票', content:'车票生成成功'});
					getTrainInfo();
				}
			});
		});
		
		addDetailViewEvent();
	};
	
	var viewTrainDetail = function(trainTag){
		$.get(APP_PATH + "/ticketOrder/get/ticketOrderByTrainTag", {trainTag: trainTag}, function(list){
			var columns = [
				//'订单号', 
				'车次', 
				//'trainTag', 
				'座位', 
				'类型', 
				'身份证号', 
				'乘客名称', 
				'乘客电话', 
				'购买人ID', 
				'始发站', 
				'终点站',
				'票价', 
				'购票时间', 
				'开车时间', 
				'状态'
			];

			var dataArray = [];
			for(var i=0; i<list.length; i++){
				dataArray.push([
				    //list[i]['orderNo'], 
				    list[i]['trainNo'],
				    //list[i]['trainTag'],
				    list[i]['seatNo'],
				    list[i]['seatType'],
				    list[i]['credentialNumber'],
				    list[i]['custName'],
				    list[i]['telephone'],
				    list[i]['orderPersonId'],
				    list[i]['startPos'],
				    list[i]['endPos'],
				    list[i]['price'],
				    util.formatDate(new Date(list[i]['timeBuyTicket']), 'yyyy-MM-dd hh:mm:ss'),
				    list[i]['timeTrainStart'],
				    list[i]['state']
				]);
			}
			layer.open({
				title: '车次详情',
				type: 1,
				area: ['1200px', '600px'],
				content: "<div><table id='specific_train_detail_table'></table></div>",
				success: function(layero, index){
					util.drawTable({id:'specific_train_detail_table', columns:columns, datArray:dataArray});
				}
			});
		});
	};
	
	var addDetailViewEvent = function(){
		$(".ticket-detail-review").click(function(){
			var trainTag = $(this).attr('trainTag');
			var trainNo = trainTag.substr(8, 12);
			
			$.get(APP_PATH + "/ticket/getTicketByTrainTag", {trainTag: trainTag}, function(list){
				if(list.length == 0){
					var layerIndex = layer.confirm('该车次还未生成, 是否生成该车次车票?', {
						btn: ['确定', '取消']
					}, function(){
						$.get(APP_PATH + "/ticket/generateTicket", {trainNo: trainNo, trainTag: trainTag}, function(data){
							layer.alert(data.msg);
							viewTrainDetail(trainTag);
						}); 
					}, function(){
						layer.close(layerIndex);
					});
				} else {
					viewTrainDetail(trainTag);
				}
			});
		});
	};
	
	var getTrainInfo = function(trainNo){
		var columns = ["车次"];
		
		var date = new Date();
		var len = Math.floor(($(".main-content-body").width() - 72) /105) + 1;
		for(var i = 0; i < len; i++){
			columns.push(util.addDay(date, i));
		}
		
		var params = {};
		params['length'] = len;
		if(trainNo){
			params['trainNo'] = trainNo;
		}

		$.get(APP_PATH + "/admin/train/get/trainInfo", params, function(dataArray){
			util.drawTable({id:"train_detail_table", columns:columns, datArray:dataArray, rowHeight:'36px', colWidth: '100px'});
			console.log("2)width: " + $("#train_detail_table").width() + ", height: " + $("#train_detail_table").height());
			addDetailViewEvent();
		});
		
	}
	
	var initViews = function(){
		getTrainInfo();
	};
	
	var initButtons = function(){
		$("#add_train_detail_table_length").click(function(){
			
		});
		
		$("#minus_train_detail_table_length").click(function(){
			
		});
		
		$("#train_search_btn").click(function(data){
			var trainNo = $.trim($("#train_search").val());

			getTrainInfo(trainNo);
		});
	};
	
	var initModule = function($container){
		initViews();
		initButtons();
	};
	
	return {
		initModule: initModule
	};
}());