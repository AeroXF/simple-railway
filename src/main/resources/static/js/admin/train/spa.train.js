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
	
	var addDetailViewEvent = function(){
		$(".ticket-detail-review").click(function(){
			var trainTag = $(this).attr('trainTag');
			$.get(APP_PATH + "/ticket/getTicketByTrainTag", {trainTag: trainTag}, function(list){
				var columns = [];
				if(trainTag[8] == "D"){
					columns = ['序号', '车次', '站点', '开车时间', '到达时间', '一等座价格', '一等座票数', '二等座价格', '二等座票数', '站票价格', '站票票数'];
				}else if(trainTag[8] == "G"){
					columns = ['序号', '车次', '站点', '开车时间', '到达时间', '商务座价格', '商务座票数', '一等座价格', '一等座票数', '二等座价格', '二等座票数'];
				}
				var dataArray = [];
				for(var i=0; i<list.length; i++){
					if(trainTag[8] == "D"){
						dataArray.push([
						    list[i]['statOrder'] + 1, 
						    list[i]['trainNo'],
						    list[i]['stationName'],
						    util.formatDate(new Date(list[i]['startTime']), 'yyyy-MM-dd hh:mm'),
						    util.formatDate(new Date(list[i]['deptTime']), 'yyyy-MM-dd hh:mm'),
						    list[i]['priceFirstClass'],
						    list[i]['ticketFirstClass'],
						    list[i]['priceSecondClass'],
						    list[i]['ticketSecondClass'],
						    list[i]['priceStand'],
						    list[i]['ticketStand']
						]);
					}else if(trainTag[8] == "G"){
						dataArray.push([
						    list[i]['statOrder'] + 1, 
						    list[i]['trainNo'],
						    list[i]['stationName'],
						    util.formatDate(new Date(list[i]['startTime']), 'yyyy-MM-dd hh:mm'),
						    util.formatDate(new Date(list[i]['deptTime']), 'yyyy-MM-dd hh:mm'),
						    list[i]['priceBusiness'],
						    list[i]['ticketBusiness'],
						    list[i]['priceFirstClass'],
						    list[i]['ticketFirstClass'],
						    list[i]['priceSecondClass'],
						    list[i]['ticketSecondClass']
						]);
					}
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
		});
	};
	
	var getTrainInfo = function(){
		var columns = ["车次"];
		
		var date = new Date();
		var len = Math.floor(($(".main-content-body").width() - 72) /105) + 1;
		for(var i = 0; i < len; i++){
			columns.push(util.addDay(date, i));
		}

		$.get(APP_PATH + "/admin/train/get/trainInfo", {length: len}, function(dataArray){
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
	};
	
	var initModule = function($container){
		initViews();
		initButtons();
	};
	
	return {
		initModule: initModule
	};
}());