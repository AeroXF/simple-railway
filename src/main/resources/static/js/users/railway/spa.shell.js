spa.shell = (function() {	
	var stateMap = {
		anchor_map : {}, active:null
	},
	$content, initModule, copyAnchorMap, changeAnchorPart, onHashchange, initButtons;
	
	var pageActive = false;
	var ticketOrderParams = {};		
	
	copyAnchorMap = function() {
		return $.extend(true, {}, stateMap.anchor_map);
	};
	
	var addSimple12306Event = function(active){
		$(".home-content-left-body p").click(function(){
			var id = $(this).attr("id");
			if(id){
				changeAnchorPart({ model: "railway", active:id });
			}
		});
		
		if(active){
			$(".home-content-left-body p").removeClass("model-active").css("padding-left", "10px");
			$(".home-content-left-body p.sub-link").css("padding-left", "30px");
			$("#" + active).addClass("model-active");
			if($("#" + active).hasClass("sub-link")){
				$(".model-active").css("padding-left", "26px");
			}else{
				$(".model-active").css("padding-left", "6px");
			}
		}
	};
	
	var addTicketOrderEvent = function(){
		$("#ticket_order_btn").click(function(){
			ticketOrderParams = {
				trainTag  : $("#ticket_order_train_tag").val(),
				startPos  : $("#ticket_order_start_pos").val(),
				endPos    : $("#ticket_order_end_pos").val(),
				trainNo   : $("#ticket_order_train_no").val(),
				queryDate : $("#ticket_order_query_date").val()
			}
			changeAnchorPart({ model: "ticket", active:"ticketOrder"});
		});
	}
	
	var loadModelActive = function(model, active){
		console.log("model: " + model + ", active: " + active);
		if(typeof model == "undefined" || model == "railway"){
			if(typeof active == "undefined" || active == "home"){
				spa.main.initModule($("#home_body"));
				addSimple12306Event();
			} else {	
				var layerIndex = layer.msg('数据加载中..', {icon: 16, shade: [0.4, '#B3B3B3'], time: 300000});
				$("#home_body").load(APP_PATH + "/" + active + "/index", function(){
					layer.close(layerIndex);
					spa[active].initModule($("#home_body_right"));
					addSimple12306Event(active);
				});
			}
		} else if(model == "ticket"){
			if(active == "ticketOrder"){
				var layerIndex = layer.msg('数据加载中..', {icon: 16, shade: [0.4, '#B3B3B3'], time: 300000});
				$.get(APP_PATH + "/" + active + "/index", ticketOrderParams, function(jsp_file){
					layer.close(layerIndex);
					$("#home_body").empty().append(jsp_file);
					spa.ticketorder.initModule();
				});
			} else if(active == "orderResult"){
				var orderNo = $("#order-no-input").val();
				var url = "";
				if(typeof orderNo == "undefined" || orderNo == null){
					url = APP_PATH + "/ticketOrder/get/orderPayment";
				} else {
					url = APP_PATH + "/ticketOrder/get/orderPayment?orderNo=" + orderNo;
				}
				var layerIndex = layer.msg('数据加载中..', {icon: 16, shade: [0.4, '#B3B3B3'], time: 300000});
				$("#home_body").load(url, function(){
					layer.close(layerIndex);
					spa.orderresult.initModule($("#home_body"));
				});
			}else{
				window.location.href = "/ticket/";
			}
		} else {
			var layerIndex = layer.msg('数据加载中..', {icon: 16, shade: [0.4, '#B3B3B3'], time: 300000});
			$("#home_body").load(APP_PATH + "/" + active + "/index", function(){
				layer.close(layerIndex);
				spa[active].initModule($("#home_body"));
			});
		}
	}
	
	changeAnchorPart = function(arg_map) {
		var anchor_map_revise = copyAnchorMap(), 
		    bool_return       = true, 
		    key_name, key_name_dep;

		KEYVAL: for (key_name in arg_map) {
			if (arg_map.hasOwnProperty(key_name)) {
				if (key_name.indexOf('_') === 0) {
					continue KEYVAL;
				}

				anchor_map_revise[key_name] = arg_map[key_name];

				key_name_dep = '_' + key_name;
				if (arg_map[key_name_dep]) {
					anchor_map_revise[key_name_dep] = arg_map[key_name_dep];
				} else {
					delete anchor_map_revise[key_name_dep];
					delete anchor_map_revise['_s' + key_name_dep];
				}
			}
		}

		try {
			$.uriAnchor.setAnchor(anchor_map_revise);
		} catch (error) {
			$.uriAnchor.setAnchor(stateMap.anchor_map, null, true);
			bool_return = false;
		}

		return bool_return;
	};

	onHashchange = function(event) {
		var anchor_map_proposed, anchor_map_previous = copyAnchorMap();

		try {
			anchor_map_proposed = $.uriAnchor.makeAnchorMap();
		} catch (error) {
			$.uriAnchor.setAnchor(anchor_map_previous, null, true);
			return false;
		}
		stateMap.anchor_map = anchor_map_proposed;
		
        var model  = stateMap.anchor_map.model;
		var active = stateMap.anchor_map.active;
		
		if(typeof model != "undefined" || typeof active != "undefined"){
			pageActive = true;
		}
		
		if(model != anchor_map_previous.model || active != anchor_map_previous.active){
			loadModelActive(model, active);
		}
		
		return false;
	};
	
	initButtons = function(){
		$(".home-header-content-left font").click(function(){
			window.location.href = "/";
		});
		
		if(!pageActive){
			loadModelActive();
		}
		
		$(".home-header-content-right .home-link").click(function(){
			var id = $(this).attr("id");
			switch(id){
				case "ticket":
					changeAnchorPart({ model:id, active:"ticket" });
					break;
				case "railway":
					changeAnchorPart({ model:id, active: "home"});
					break;
				case "logout":
					$("#logout_btn").trigger("click");
					break;
			}
		});
	};
	
	initModule = function() {
		$(window).bind('hashchange', onHashchange).trigger('hashchange');
		
		initButtons();
	};

	return {
		initModule: initModule,
		changeAnchorPart: changeAnchorPart
	};
}());