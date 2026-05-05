// document.addEventListener('DOMContentLoaded',function(){

// })

/* registration option select js */
document.addEventListener('DOMContentLoaded', function() {
	document.querySelector('#fragment').classList.add('active')
	setTimeout(function() {
		var a = document.querySelectorAll('*');
		for (var i = 0; i < a.length; i++) {
			a[i].style.transition = 'all .5s';
		}
	}, 500);
})

window.addEventListener('resize', function() {
	setTimeout(function() {
		fixFooter();
	}, 1000);
})

// document.querySelector('#open').addEventListener('click', function() {
//
//	var a = document.querySelector('#notifications')
//
//	if (a.classList.contains('active')) {
//		a.classList.remove('active')
//	} else {
//		a.classList.add('active')
//	}
//});

// document.querySelector('body').addEventListener('click', function(e) {
//	if (!findParent(e.target, 'nav')) {
//		if (!findParent(e.target, 'open__')) {
//			var a = document.querySelector('#notifications')
//			a.classList.remove('active');
//		}
//	}
//});

function hasParent(element, className) {
	if (element.parentNode.tagName !== 'BODY') {
		if (element.parentNode.classList.contains(className)) {
			return true;
		} else {
			if (hasParent(element.parentNode, className)) {
				return true;
			}
		}
	}
	return false;
};

function findParent(element, className) {
	if (hasParent(element, className)) {
		if (element.parentNode.classList.contains(className)) {
			return element.parentNode;
		} else {
			if (findParent(element.parentNode, className)) {
				return findParent(element.parentNode, className);
			}
		}
	} else {
		return null;
	}
}

var fixFooter = function() {
	var a = document.querySelector('footer');
	var b = a.offsetTop;
	var d = a.clientHeight;
	var c = window.innerHeight;

	if (b + d < c) {
		a.style.position = 'fixed';
		a.style.bottom = '0px';
		a.style.width = '100vw';
	}

}

fixFooter();

function _tabs() {
	
	console.log('tabs');

	var a = document.querySelectorAll('#cond-buttons .button');

	var b = document.querySelectorAll('#cond-body .cond-text');

	for (var i = 0; i < a.length; i++) { 
		a[i].addEventListener('click', function(e) {

			var c = e.target.attributes['data-index']['nodeValue'];

			for (var i = 0; i < b.length; i++) {

				a[i].classList.remove('active')

				b[i].classList.remove('active');

				if (c == b[i].attributes['data-index']['nodeValue']) {

					b[i].classList.add('active')

				}

			}

			this.classList.add('active')

		})
	}

}

_tabs();

function _regSelect() {

	console.log('_regSelect');

	var a = document.querySelectorAll('div.options div.main');

	for (var i = 0; i < a.length; i++) {

		a[i].addEventListener('click', function(e) {

			e.target.parentNode.children[1].classList.toggle('active');

		})
	}

	var b = document.querySelectorAll('div.selects div.type');

	for (var i = 0; i < b.length; i++) {
		b[i]
				.addEventListener(
						'click',
						function(e) {

							console.log(e.target.parentNode)

							e.target.parentNode.classList.remove('active')
							e.target.parentNode.parentNode.children[0].innerHTML = e.target.innerHTML;

						})
	}

}

_regSelect();

function _checkBox() {

	var $a = $('div.bank div.check-boxs div.box');

	$a.on('click', function(e) {

		if (e.target.classList.contains('box')) {

			$a.removeClass('active');

			// console.log($(this).find('input'));

			$(this).find('input').trigger('click');

			$(this).addClass('active')

		}

	})

}

_checkBox();

var notif = document.querySelector('#notifications');

document.addEventListener('click', function(e) {
	// console.log(e.target.parentNode)
})

/* end of registration option select js */

var x, i, j, selElmnt, a, b, c;
x = document.getElementsByClassName("custom-select");
for (i = 0; i < x.length; i++) {
	selElmnt = x[i].getElementsByTagName("select")[0];
	a = document.createElement("DIV");
	a.setAttribute("class", "select-selected");
	a.innerHTML = selElmnt.options[selElmnt.selectedIndex].innerHTML;
	x[i].appendChild(a);
	b = document.createElement("DIV");
	b.setAttribute("class", "select-items select-hide");
	for (j = 1; j < selElmnt.length; j++) {
		c = document.createElement("DIV");
		c.innerHTML = selElmnt.options[j].innerHTML;
		c.addEventListener("click", function(e) {
			var y, i, k, s, h;
			s = this.parentNode.parentNode.getElementsByTagName("select")[0];
			h = this.parentNode.previousSibling;
			for (i = 0; i < s.length; i++) {
				if (s.options[i].innerHTML == this.innerHTML) {
					s.selectedIndex = i;
					h.innerHTML = this.innerHTML;
					y = this.parentNode
							.getElementsByClassName("same-as-selected");
					for (k = 0; k < y.length; k++) {
						y[k].removeAttribute("class");
					}
					this.setAttribute("class", "same-as-selected");
					break;
				}
			}
			h.click();
		});
		b.appendChild(c);
	}
	x[i].appendChild(b);
	a.addEventListener("click", function(e) {
		e.stopPropagation();
		closeAllSelect(this);
		this.nextSibling.classList.toggle("select-hide");
		this.classList.toggle("select-arrow-active");
	});
}
function closeAllSelect(elmnt) {
	var x, y, i, arrNo = [];
	x = document.getElementsByClassName("select-items");
	y = document.getElementsByClassName("select-selected");
	for (i = 0; i < y.length; i++) {
		if (elmnt == y[i]) {
			arrNo.push(i)
		} else {
			y[i].classList.remove("select-arrow-active");
		}
	}
	for (i = 0; i < x.length; i++) {
		if (arrNo.indexOf(i)) {
			x[i].classList.add("select-hide");
		}
	}
}
document.addEventListener("click", closeAllSelect);
