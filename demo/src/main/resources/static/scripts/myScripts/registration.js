 $(document).ready(function(){
	 
		

      
        	
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
             
             
             $("#registrationBtn").click(function( event ) {
 	          	
            	 console.log('click ');
            	 $("#form").submit();
     		  
     		});
             
 
             
             
             $( "#inputImage" ).change(function() {
            		var input= (document.getElementById('inputImage'));
            	var fReader = new FileReader();
            	fReader.readAsDataURL(input.files[0]);
            	fReader.onloadend = function(event){

            		 (document.getElementById('fileName')).value= input.files[0].name;
            		
            	}
            	
 
            	 
            	});
             
             $( "#inputVat" ).change(function() {
         	var input= (document.getElementById('inputVat'));
         	var fReader = new FileReader();
         	fReader.readAsDataURL(input.files[0]);
         	console.log(input.files[0].name);
         	fReader.onloadend = function(event){

         		 (document.getElementById('fileNameVat')).value= input.files[0].name;
         		
         	}
             });
         	
         	$( "#fileOther" ).change(function() {
             	var input= (document.getElementById("fileOther"));
             	var fReader = new FileReader();
             	fReader.readAsDataURL(input.files[0]);
             	console.log(input.files[0].name);
             	fReader.onloadend = function(event){

             		 (document.getElementById('fileNameOther')).value= input.files[0].name;
          
             		
             	}
         	

         	 
         	});
         	
         	
         	
            $( "#otherFileFirst" ).change(function() {
        		var input= (document.getElementById('otherFileFirst'));
        	var fReader = new FileReader();
        	fReader.readAsDataURL(input.files[0]);
        	fReader.onloadend = function(event){

        		 (document.getElementById('otherFileFirstName')).value= input.files[0].name;
        		
        	}
        	

        	 
        	});
            
            $( "#otherFileSecond" ).change(function() {
        		var input= (document.getElementById('otherFileSecond'));
        	var fReader = new FileReader();
        	fReader.readAsDataURL(input.files[0]);
        	fReader.onloadend = function(event){

        		 (document.getElementById('otherFileSecondName')).value= input.files[0].name;
        		
        	}
        	

        	 
        	});
         	
            
            
            $( "#otherFileThird" ).change(function() {
        		var input= (document.getElementById('otherFileThird'));
        	var fReader = new FileReader();
        	fReader.readAsDataURL(input.files[0]);
        	fReader.onloadend = function(event){

        		 (document.getElementById('otherFileThirdName')).value= input.files[0].name;
        		
        	}
        	

        	 
        	});
         	
         	
             
         	$("#inputVat").change(function() {
      
         		 
         		});
         	
             $('input[type=checkbox]').change(function() {
            	 
            	 if ($(this).is(':checked')) {
            		 (document.getElementById('inlineCheckbox1')).value=true;
            		 $("#fileNameVat").prop('required',true);
            	
            		
            	
            	    } else {
            	    	(document.getElementById('inlineCheckbox1')).value=false;
            	   		 $("#fileNameVat").prop('required',false);
            	    }
            	 
            	 
         	});
        });
         
         
  

         
     	$( "#cancelPage" ).click(function( event ) {
     		  window.history.back();
    		  
    		});
     	
     	
     	$( "#muRegistrationSubmit" ).click(function( event ) {
     		if ($('#registrationConditions').is(':checked')) {
     			 document.getElementById("registrationConditionsLabel").style.color='black';
    	    } else {
    	    $( "#muRegistrationSubmit" ).prop( "disabled", true );
    	       document.getElementById("registrationConditionsLabel").style.color='red';
    	    }
  
  		  
  		});
     	
     	 $('#registrationConditions').change(function() { 
     		 if ($(this).is(':checked')) {
     			 document.getElementById("registrationConditionsLabel").style.color='black';
     			$( "#muRegistrationSubmit" ).prop( "disabled", false );
     		    } else {
     		    	$( "#muRegistrationSubmit" ).prop( "disabled", true );
     		    }
     		 
     	 }); 
     	 
     	$('.input').on('click',function(){
     		
     		console.log(123);

 			var $this = $(this);
 			var input = $(this).parent().find('input');

 			$(input).trigger('click');

 			$(input).on('change', function(){
 				if ($(this).val() != '') {
 					$($this).find('span').text($(this).val());
 					inputChanged($(this));
 				}
 			});

 		});



     	function _addInput() {
     		
     		var a = document.querySelector('div.reg-body-1 div.add-button');

     		var b = document.querySelector('.add')

     		a.addEventListener('click',function(){

     			b.style.display = 'block';

     		});

     	}

     	_addInput();

     	function _regCheck2(){

     		var  a = document.querySelector('.reg-condition');

     		a.addEventListener('click',function(){

     			this.children[1].classList.toggle('active');

     			// console.log(this.childNode);

     		});

     	}
     	_regCheck2();
     	
     	
     	document.addEventListener('click',function(e){

     			// console.log(e.target.classList)

     		});

     		

     		function inputChanged(input){
     			input.parent().find('img').hide();
     			input.parent().find('img.delete').show();
     		}

     		function clearInput(input) {
     			input.val('');
     			input.parent().find('img').show();
     			var span = input.parent().find('span');
     			$(span).text(span.data('text'));
     			input.parent().find('img.delete').hide();
     		}

     		var inpDeleteBtns = document.querySelectorAll('img.delete');

     		for (var i = 0; i < inpDeleteBtns.length; i++) {
     			inpDeleteBtns[i].addEventListener('click',function(e){
     				clearInput($($(this).parent().siblings('input')));
     				e.stopPropagation();
     			})
     		}

    