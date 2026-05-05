 $(document).ready(function(){

        	 
      
        	if(document.getElementById('exError').value==-1){
        	 
        		document.getElementById('errorExsist').style.display="block";
         }
        		 
        	else{
        		
        		document.getElementById('errorExsist').style.display="none";
        	}
        	
             $("#form").validate({
                 rules: {
                     password: {
                         required: true,
                         minlength: 3
                     },
                     url: {
                         required: true,
                         url: true
                     },
                     number: {
                         required: true,
                         number: true
                     },
                     min: {
                         required: true,
                         minlength: 6
                     },
                     max: {
                         required: true,
                         maxlength: 4
                     }
                     
                     
                 }
          
             });
             
             
             $( "#inputImage" ).change(function() {
            		var input= (document.getElementById('inputImage'));
            	var fReader = new FileReader();
            	fReader.readAsDataURL(input.files[0]);
            	fReader.onloadend = function(event){

            		 (document.getElementById('fileName')).value= input.files[0].name;
            		 document.getElementById('fileDescr').style.display='block'; 
            	}
            	
 
            	 
            	});
             
             $('input[type=checkbox]').change(function() {
            	 if ($(this).is(':checked')) {
            		 (document.getElementById('inlineCheckbox1')).value=true;
            	    } else {
            	    	(document.getElementById('inlineCheckbox1')).value=false;
            	    }
         	});
        });
         
         
  
         
         
     	$( "#cancelPage" ).click(function( event ) {
     		  window.location.href='/auction/company/view_company_users';
    		  
    		});

        $(document).ready(function(){

            Dropzone.options.myAwesomeDropzone = {

                autoProcessQueue: false,
                uploadMultiple: true,
                parallelUploads: 100,
                maxFiles: 100,

                // Dropzone settings
                init: function() {
                    var myDropzone = this;

                    this.element.querySelector("button[type=submit]").addEventListener("click", function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        myDropzone.processQueue();
                    });
                    this.on("sendingmultiple", function() {
                    });
                    this.on("successmultiple", function(files, response) {
                    });
                    this.on("errormultiple", function(files, response) {
                    });
                }

            }

       });