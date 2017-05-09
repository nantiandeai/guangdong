/**
 * Created by LENCON on 2016/11/12.
 */
$(document).ready(function(e) {
    $('.members-list').sly({
        itemNav: "smart",
        easing: "easeOutExpo",
        prevPage: ".pre-left",
        nextPage: ".pre-right",
        horizontal: 1,
        touchDragging: 1,
        dragContent: 1
    });
    $('.img-list').sly({
        itemNav: "smart",
        easing: "easeOutExpo",
        prevPage: ".pre-left1",
        nextPage: ".pre-right1",
        horizontal: 1,
        touchDragging: 1,
        dragContent: 1
    });
    $('.video-list').sly({
        itemNav: "smart",
        easing: "easeOutExpo",
        prevPage: ".pre-left2",
        nextPage: ".pre-right2",
        horizontal: 1,
        touchDragging: 1,
        dragContent: 1
    });
    $('.personnel-list').sly({
        itemNav: "smart",
        pagesBar: ".pages",
        dragContent: 1,
		cycleBy: 'items',
        startAt: 0,
        elasticBounds: 0,
        scrollBy: 1,
    });
})