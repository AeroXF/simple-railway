$(function(){
	$(".home-header-content-right .home-link").click(function(){
		var id = $(this).attr("id");
		switch(id){
			case "ticket":
				window.location.href = "/ticket/";
				break;
			case "railway":
				window.location.href = "/home/";
				break;
			case "logout":
				$("#logout_btn").trigger("click");
				break;
		}
	});
});