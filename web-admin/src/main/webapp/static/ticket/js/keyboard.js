document.onselectstart = new Function("return false");
var keyboard = {
	inputId: null, //文本框id
	flag: true,
	shift: false,
	capslock: false,
	cursorPos: 0,
	init: function(config){
		keyboard.inputId = config.inputId;
	},
	config: function(config){
		keyboard.init(config);
		keyboard.setEventFun(keyboard.inputId);
	},
	setHtml: function(){
		$d = document.createElement("div");
		$d.id = "keyboard";
		$d.innerHTML = "<ul class=\"keyboard\"><li class=\"symbol\"><span class=\"off\">`</span><span class=\"on\">~</span></li><li class=\"symbol\"><span class=\"off\">1</span><span class=\"on\">!</span></li><li class=\"symbol\"><span class=\"off\">2</span><span class=\"on\">@</span></li><li class=\"symbol\"><span class=\"off\">3</span><span class=\"on\">#</span></li><li class=\"symbol\"><span class=\"off\">4</span><span class=\"on\">$</span></li><li class=\"symbol\"><span class=\"off\">5</span><span class=\"on\">%</span></li><li class=\"symbol\"><span class=\"off\">6</span><span class=\"on\">^</span></li><li class=\"symbol\"><span class=\"off\">7</span><span class=\"on\">&amp;</span></li><li class=\"symbol\"><span class=\"off\">8</span><span class=\"on\">*</span></li><li class=\"symbol\"><span class=\"off\">9</span><span class=\"on\">(</span></li><li class=\"symbol\"><span class=\"off\">0</span><span class=\"on\">)</span></li><li class=\"symbol\"><span class=\"off\">-</span><span class=\"on\">_</span></li><li class=\"symbol\"><span class=\"off\">=</span><span class=\"on\">+</span></li><li class=\"delete lastitem\"><input type=\"button\" class=\"btn-del\" id=\"btn-del\" value=\"delete\"/></li><li class=\"tab\">tab</li><li class=\"letter\">q</li><li class=\"letter\">w</li><li class=\"letter\">e</li><li class=\"letter\">r</li><li class=\"letter\">t</li><li class=\"letter\">y</li><li class=\"letter\">u</li><li class=\"letter\">i</li><li class=\"letter\">o</li><li class=\"letter\">p</li><li class=\"symbol\"><span class=\"off\">[</span><span class=\"on\">{</span></li><li class=\"symbol\"><span class=\"off\">]</span><span class=\"on\">}</span></li><li class=\"symbol lastitem\"><span class=\"off\">\</span><span class=\"on\">|</span></li><li class=\"capslock\">caps lock</li><li class=\"letter\">a</li><li class=\"letter\">s</li><li class=\"letter\">d</li><li class=\"letter\">f</li><li class=\"letter\">g</li><li class=\"letter\">h</li><li class=\"letter\">j</li><li class=\"letter\">k</li><li class=\"letter\">l</li><li class=\"symbol\"><span class=\"off\">;</span><span class=\"on\">:</span></li><li class=\"symbol\"><span class=\"off\">'</span><span class=\"on\">&quot;</span></li><li class=\"return lastitem\">return</li><li class=\"left-shift\">shift</li><li class=\"letter\">z</li><li class=\"letter\">x</li><li class=\"letter\">c</li><li class=\"letter\">v</li><li class=\"letter\">b</li><li class=\"letter\">n</li><li class=\"letter\">m</li><li class=\"symbol\"><span class=\"off\">,</span><span class=\"on\">&lt;</span></li><li class=\"symbol\"><span class=\"off\">.</span><span class=\"on\">&gt;</span></li><li class=\"symbol\"><span class=\"off\">/</span><span class=\"on\">?</span></li><li class=\"right-shift lastitem\">shift</li><li class=\"space lastitem\">&nbsp;</li></ul>";
	},
	setEventFun: function(id){
		keyboard.setHtml();
		$($d).find("li").click(function(e){
			if(keyboard.flag == true){
				$(id).each(function(){
					if($(this).attr("focus") == "true") $write = $(this);
				});
			}
			e.stopPropagation();
			var $this = $(this),
				character = $this.html(); // If it's a lowercase letter, nothing happens to this variable

			// Shift keys
			if ($this.hasClass('left-shift') || $this.hasClass('right-shift')) {
				$('.letter').toggleClass('uppercase');
				$('.symbol span').toggle();

				keyboard.shift = (keyboard.shift === true) ? false : true;
				keyboard.capslock = false;
				return false;
			}

			// Caps lock
			if ($this.hasClass('capslock')) {
				$('.letter').toggleClass('uppercase');
				keyboard.capslock = true;
				return false;
			}

			// Delete
			if ($this.hasClass('delete')) {
				if($write.attr("placeholder") == $write.val()){
					//$write.focus();
					return;
				}
				keyboard.delValFun($write);
				return false;
			}

			// Special characters
			if ($this.hasClass('symbol')) character = $('span:visible', $this).html();
			if ($this.hasClass('space')) character = ' ';
			if ($this.hasClass('tab')) character = "\t";
			if ($this.hasClass('return')) character = "\n";

			// Uppercase letter
			if ($this.hasClass('uppercase')) character = character.toUpperCase();

			// Remove shift once a key is clicked.
			if (keyboard.shift === true) {
				$('.symbol span').toggle();
				if (keyboard.capslock === false) $('.letter').toggleClass('uppercase');

				keyboard.shift = false;
			}
			if($write.val() == $write.attr("placeholder")) $write.val("");
			// Add the character
			var cardNumVal = $write.val();
			var cardNumArr = cardNumVal.split("");
			cardNumArr.splice(keyboard.cursorPos, 0, keyboard.trim(character));
			cardNumVal = cardNumArr.join("");
			keyboard.cursorPos++;
			$write.val(cardNumVal).css({"color": "#000000"});
			keyboard.setCursorPosition($write.get(0), keyboard.cursorPos);

			$write.parent().find("input").each(function(){
				if($(this).attr("focus") == "true") {
					$(this).show().siblings("input").hide();
				}
			});
		});
		$($d).click(function(e){
			e.stopPropagation();
			$($d).show();
		});
		$(document).click(function(e){
			e.stopPropagation();
			$($d).hide();
		});
		$(id).on({
			"focus": function(e){
				e.stopPropagation();
				$(id).each(function(){
					$(this).attr("focus", false);
				});
				$(this).attr("focus", true);
				$write = $(this);
				keyboard.setPosition($write);
			},
			"click": function(e){
				e.stopPropagation();
				keyboard.cursorPos = keyboard.getPositionForInput($write.get(0));
			}
		});
	},
	setPosition: function($cur){
		var $parent = $cur.parent();
		var posTop = $parent.position().top;
		var posLeft = $parent.position().left;
		var $thisH = $write.outerHeight();
		document.body.appendChild($d);
		$($d).css({"display": "block", "position": "absolute", "top": posTop+$thisH, "left": posLeft});
	},
	trim: function(str) {
		if (str == " ") {
			return str;
		} else {
			return str.replace(/(^\s*)|(\s*$)/g, "");
		}
	},
	delValFun: function($obj) {
		var cardNumVal = $obj.val();
		if(keyboard.cursorPos > 0) {
			var cardNumArr = cardNumVal.split("");
			cardNumArr.splice(keyboard.cursorPos - 1, 1);
			cardNumVal = cardNumArr.join("");
			$obj.val(cardNumVal);
			keyboard.cursorPos--;
			keyboard.setCursorPosition($obj.get(0), keyboard.cursorPos);
		}else{
			keyboard.setCursorPosition($obj.get(0), 0);
		}
	},
	getPositionForInput: function(ctrl) {  //单行文本框 获取光标位置
		var CaretPos = 0;
		if (document.selection) { // IE Support
			$(ctrl).focus();
			var Sel = document.selection.createRange();
			Sel.moveStart('character', -ctrl.value.length);
			CaretPos = Sel.text.length;
		} else if (ctrl.selectionStart || ctrl.selectionStart == '0') {// Firefox support
			CaretPos = ctrl.selectionStart;
		}
		return (CaretPos);
	},
    setCursorPosition: function(ctrl, pos) {  //设置光标位置函数
		if (ctrl.setSelectionRange) {
			$(ctrl).focus();
			ctrl.setSelectionRange(pos, pos);
		}
		else if (ctrl.createTextRange) {
			var range = ctrl.createTextRange();
			range.collapse(true);
			range.moveEnd('character', pos);
			range.moveStart('character', pos);
			range.select();
		}
	}
};