spa.shell = (function() {	
	var stateMap = {
		anchor_map : {}, active:null
	},
	$content, initModule, copyAnchorMap, changeAnchorPart, onHashchange, initButtons;
	
	var pageActive = false;
	
	var trainSubModelHtml     = "<div>车次管理</div><span tab='index'>当前车次</span><span tab='trainAdjust' >车次调整</span>";
	var travellerSubModelHtml = "<div>旅客管理</div><span tab='index'>在线人数</span><span tab='travellerAll'>所有旅客</span>";
	var messageSubModelHtml   = "<div>消息中心</div><span tab='index'>反馈意见</span><span tab='msgOnline'   >在线消息</span>";
	var statSubModelHtml      = "<div>数据统计</div><span tab='index'>车次分析</span><span tab='staffAnalyze'>人员分析</span>";
	
	copyAnchorMap = function() {
		return $.extend(true, {}, stateMap.anchor_map);
	};

	var loadModelActive = function(model, active){
		if(model == "train"){
			$("#left_second").empty().append(trainSubModelHtml);
		}else if(model == "traveller"){
			$("#left_second").empty().append(travellerSubModelHtml);
		}else if(model == "message"){
			$("#left_second").empty().append(messageSubModelHtml);
		}else if(model == "stat"){
			$("#left_second").empty().append(statSubModelHtml);
		}
		
		$("#left_second span").click(function(){
			var tab = $(this).attr("tab");
			changeAnchorPart({model: model, active: tab});
		});
		
		if(active == null){
			active = "index";
			$("#left_second span[tab='index']").addClass("active");
		}
		
		$("#" + model).addClass("active");
		$("#left_second span[tab='" + active + "']").addClass("active");
		
		$("#main_content").load(APP_PATH + "/admin/" + model + "/" + active, function(jsp_file){
			$("#main_content").empty().append(jsp_file);
			
			if(active == "index"){
				spa[model].initModule($("#main_content"));
			} else {
				spa[active].initModule($("#main_content"));
			}
		});
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
			if(model != anchor_map_previous.model){
				if(active != anchor_map_previous.active){
					loadModelActive(model, active);
				} else{
					loadModelActive(model);
				}
			} else {
				loadModelActive(model, active);
			}
		}
		
		return false;
	};
	
	initButtons = function(){
		$(".home-page-link").click(function(){
			window.location.href = "/simple12306/";
		});
		
		$("#left_top li").click(function(){
			$("#left_top li").removeClass("active");
			$(this).addClass("active");
			
			var modelId = $(this).attr("id");
			
			changeAnchorPart({model: modelId});
		})
		
		if(!pageActive){
			$("#train").trigger("click");
		}
	};
	
	var resizeWindow = function(){
		var width = document.body.clientWidth - 300;
		$("#main_content").css("width", width + "px");
		var height = window.innerHeight - 40;
		$("#left_top").css("height", height + "px");
		$("#left_second").css("height", height + "px");
		$("#main_content").css("height", height + "px");
	}
	
	initModule = function() {
		$(window).bind('hashchange', onHashchange).trigger('hashchange');
		
		resizeWindow();
		
		$(window).resize(function() {
			resizeWindow();
		});
		
		initButtons();
	};

	return {
		initModule: initModule
	};
}());