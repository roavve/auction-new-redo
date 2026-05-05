	            $(document).ready(function () {
		            	$( "#password_again" ).change(function() {
		            		if(document.getElementById('newPassword').value!=document.getElementById('password_again').value){
		            			document.getElementById('confirmError').style.display="block";
		            		}
		            		else{
		            			document.getElementById('confirmError').style.display="none";
		            		}
		            		});
		            	
		            	
		
		                $('.i-checks').iCheck({
		                    checkboxClass: 'icheckbox_square-green',
		                    radioClass: 'iradio_square-green',
		                });
	            });
