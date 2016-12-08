function showDynamicRemainTicket(){
	$("#index_right_content").empty().append('<table id="dynamic_search_table"></table>');
	var columns = ["出发地", "目的地", "7月13日", "7月14日", "7月15日", "7月16日"];
	var dataArray = [];
	
	for(var i = 0; i < 20; i++){
		var arr = ["上海", "北京", 295, 341, 227, 182];
		dataArray.push(arr);
	}
	
	var options = {id: "dynamic_search_table", columns: columns, datArray: dataArray};
	
	util.drawTable(options);
}

$(function(){
	for(var i = 1; i <= 30; i++){
		$("div.main-content-inner-div-left-list").append("<p><a href='#'>" + i + "). 恭喜simple12306客运人次突破10万大关!</a></p>");
	}
	
	$(".tab-detail-group span").click(function(){
		$(".tab-detail-group span").removeClass("tab-detail-group-active");
		$(this).addClass("tab-detail-group-active");
		
		switch($(this).attr("id")){
			case "dynamic_search_span":
				showDynamicRemainTicket();
				break;
			default:
				$("#index_right_content").empty().append($(this).text());
				break;
		}
	});

	$(".tab-detail-group span[id='dynamic_search_span'").trigger("click");
});