var spa = (function(){
	var initModule = function(){
		spa.shell.initModule();
		$.ajaxSetup ({ 
		    cache: false //关闭AJAX相应的缓存 
		});
	};

	return { initModule: initModule };
}());