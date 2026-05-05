
$(document).ready(function(){


	     var result = null;
	     var invitation=null;
	     var scriptUrl = "/auction/user/log_in_user";
	    

	     $.ajax({
	        url: scriptUrl,
	        type: 'GET',
	        dataType: 'json',
	        async: false,
	 
	        cache: false,
	        success: function(data) {
        		

	        	result = null;	
	        	invitation=null;
	        	
	    result = data.objectResponse.list.user.username;
	    invitation=data.objectResponse.list.newInvitations;


	        } ,
	        error: function (request, status, error) {
	           console.log("error");
	        }
	     });
	   
	     console.log('Hi');
	     
	     $("#user-menu-text-lab").text(result);
	     $("#myBidUSER").val(result);
	     $("#newInvitations").text(invitation);
	     $("#newmessageInvitation").text(invitation);
	     
	     
	     
	});
	