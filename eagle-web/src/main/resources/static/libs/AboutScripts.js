
var ANIMATION_SUBMIT_ENTER = "submitEnter 0.5s forwards";
var ANIMATION_SUBMIT_LEAVE = "submitLeave 0.5s forwards";

function AboutScripts() {

	var about_submit_btn = document.getElementById("about_submit");

	about_submit_btn.addEventListener("mouseenter", function() {addAnimation(ANIMATION_SUBMIT_ENTER, about_submit_btn)});
	about_submit_btn.addEventListener("mouseleave", function() {addAnimation(ANIMATION_SUBMIT_LEAVE, about_submit_btn)});
}