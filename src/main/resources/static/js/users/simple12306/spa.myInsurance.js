/**
 * 
 */
spa.myInsurance = (function(){
	
	var initModule = function($container){
		$("#home_current_position").html("您现在的位置：客运首页 > 我的12306> <font class='position-font'>我的保险</font>");
	};
	
	return {
		initModule: initModule
	};
}());