
$(document).ready(function() {
					$( "#cancelPage" ).click(function( event ) {
						window.location.replace("/auction/user/login_page");
						  
				    });
	
	
			if(document.getElementById('exError').value==-1){
				$('#errorExsist').addClass('active');
			}else{
				$('#errorExsist').removeClass('active');
			}
});