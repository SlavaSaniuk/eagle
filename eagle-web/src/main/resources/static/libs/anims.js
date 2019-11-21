function addAnimation(a_animation, element) {
	element.style.animation = a_animation;
}

function removeAnimation(element) {
	element.style.animation = "";
}

function hide(element) {
	element.style.opacity = 0;
}

function show(element) {
	element.style.opacity = 1;
}