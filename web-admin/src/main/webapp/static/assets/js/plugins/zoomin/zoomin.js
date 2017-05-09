/*---------------------------------------------------------------------
	* Filename:         zoomin.js
	* Description:      zoomin
	* Version:          1.0.1 (2016-12-10)
	* Website:          hn.sun3d.com
	* Author:           JackyZeng (zrongs@vip.qq.com)
---------------------------------------------------------------------*/
;(function($){
	var defaults = {fadeduration:500}
	var $zoomiocontainer
	var currentzoominfo = { $zoomimage:null, offset:[,], settings:null, multiplier:[,] }
	var ismobile = navigator.userAgent.match(/(iPad)|(iPhone)|(iPod)|(android)|(webOS)/i) != null //boolean check for popular mobile browsers

	function getDimensions($target){
		return {w:$target.width(), h:$target.height()}
	}

	function getoffset(what, offsettype){ // custom get element offset from document (since jQuery version is whack in mobile browsers
		return (what.offsetParent)? what[offsettype]+getoffset(what.offsetParent, offsettype) : what[offsettype]
	}

	function zoomio($img, settings){ // zoomio plugin function
		var s = settings || defaults
		var trigger = ismobile? 'touchstart' : 'mouseenter'
		$img.off('touchstart mouseenter').on(trigger, function(e){
			var jqueryevt = e
			var e = jqueryevt.originalEvent.changedTouches? jqueryevt.originalEvent.changedTouches[0] : jqueryevt
			var offset = {left:getoffset($img.get(0), 'offsetLeft'), top:getoffset($img.get(0), 'offsetTop') }
			var mousecoord = [e.pageX - offset.left, e.pageY - offset.top]
			var $zoomimage
			var zoomdfd = $.Deferred()
			var imgdimensions = {imgw:null, imgh:null, zoomimgw:null, zoomimgh:null}
			$zoomiocontainer.html( '<img src="' + $img.attr('src') + '" />' )
			$zoomimage = $zoomiocontainer.find('img')
			if ($zoomimage.get(0).complete){
				zoomdfd.resolve()
			}
			else{
				$zoomimage.on('load', function(){
					zoomdfd.resolve()
				})
			}
			zoomdfd.done(function(){
				var imgdimensions = getDimensions($img)
				var containerwidth = s.w || imgdimensions.w
				var containerheight = s.h || imgdimensions.h
				$zoomiocontainer.css({width:containerwidth, height:containerheight, left:offset.left, top:offset.top}) // set zoom container dimensions and position
				var zoomiocontainerdimensions = getDimensions($zoomiocontainer)
				var zoomimgdimensions = getDimensions($zoomimage)
				$zoomiocontainer.stop().css({visibility:'visible', opacity:0}).animate({opacity:1}, s.fadeduration) // fade zoom container into view
				if (ismobile){
					var scrollleftpos = (mousecoord[0] / imgdimensions.w) * (zoomimgdimensions.w - zoomiocontainerdimensions.w)
					var scrolltoppos = (mousecoord[1] / imgdimensions.h) * (zoomimgdimensions.h - zoomiocontainerdimensions.h)
					$zoomiocontainer.scrollLeft( scrollleftpos )
					$zoomiocontainer.scrollTop( scrolltoppos )
				}
				currentzoominfo = {$zoomimage:$zoomimage, offset:offset, settings:s, multiplier:[zoomimgdimensions.w/zoomiocontainerdimensions.w, zoomimgdimensions.h/zoomiocontainerdimensions.h]}
			})
			jqueryevt.stopPropagation()
		})		
	}

	$.fn.zoomio = function(options){
		var s = $.extend({}, defaults, options)

		return this.each(function(){
			var $target = $(this)

			$target = ($target.is('img'))? $target : $target.find('img:eq(0)')
			if ($target.length == 0){
				return true
			}
			zoomio($target, s)
		})

	}

	$(function(){
		$zoomiocontainer = $('<div id="zoomiocontainer" />').appendTo($('body'));
		if (!ismobile){
			$zoomiocontainer.on('mousemove', function(e){
				var $zoomimage = currentzoominfo.$zoomimage
				var imgoffset = currentzoominfo.offset
				var mousecoord = [e.pageX-imgoffset.left, e.pageY-imgoffset.top]
				var multiplier = currentzoominfo.multiplier
				$zoomimage.css({left: -mousecoord[0] * multiplier[0] + mousecoord[0] , top: -mousecoord[1] * multiplier[1] + mousecoord[1]})
			})
			$zoomiocontainer.on('mouseleave', function(){
				$zoomiocontainer.stop().animate({opacity:0}, 0, function(){
					$(this).css({visibility:'hidden'})
				})
			})
		}
		else{ // is mobile
			$zoomiocontainer.addClass('mobileclass')
			$zoomiocontainer.on('touchstart', function(e){
				e.stopPropagation() // stopPropagation() works on jquery evt object (versus e.originalEvent.changedTouches[0]
			})
			$(document).on('touchstart', function(e){
				if (currentzoominfo.$zoomimage){ // if $zoomimage defined
					$zoomiocontainer.stop().animate({opacity:0}, 0, function(){
						$(this).css({visibility:'hidden'})
					})
				}
			})
		} // end else
	})

})(jQuery);