
//html elements links
var sign_in_block;
var sign_up_block;

//page animations
var animation_SwipeAndShow = "swipeAndShow 0.5s 1 ease-out forwards";
var animation_SwipeAndHide = "swipeAndHide 0.5s 1 ease-out forwards";

var animation_SubmitEnter = "submitEnter 0.5s forwards";
var animation_SubmitLeave = "submitLeave 0.5s forwards";


function SignInit() {

	//Initialize links
	sign_in_block = document.getElementById("sign_in_block");
	sign_up_block = document.getElementById("sign_up_block");

	var sign_container = document.getElementById("sign_container");

	//Show only request sign form
	var url_form_param = new URLSearchParams(window.location.search).get('form');

	if (url_form_param != null && url_form_param == 'signup') {
		showSignUpBlock();
	}else {
		hide(sign_up_block);
	}
	
	//Add events listeners
	document.getElementById("sign_in_toggle").addEventListener("click", function(env) {
		env.preventDefault();
		showSignUpBlock();
	});

	document.getElementById("sign_up_toggle").addEventListener("click", function(env) {
		env.preventDefault();
		showSignInBlock();
	});

	var sign_in_smb = document.getElementById("sign_in_submit");
	sign_in_smb.addEventListener("mouseenter", function() {addAnimation(animation_SubmitEnter, sign_in_smb)});
	sign_in_smb.addEventListener("mouseleave", function() {addAnimation(animation_SubmitLeave, sign_in_smb)});

	var sign_up_smb = document.getElementById("sign_up_submit");
	sign_up_smb.addEventListener("mouseenter", function() {addAnimation(animation_SubmitEnter, sign_up_smb)});
	sign_up_smb.addEventListener("mouseleave", function() {addAnimation(animation_SubmitLeave, sign_up_smb)});
}	
	
function showSignInBlock() {
	//Hide signup block
	addAnimation(animation_SwipeAndHide, sign_up_block);
	//Show signin block
	addAnimation(animation_SwipeAndShow, sign_in_block);

	//Move signin block to end of sign_block 
	setTimeout(function() {
		//Remove animations
		removeAnimation(sign_in_block);
		removeAnimation(sign_up_block);
		//Move first block to end
		moveToEnd(sign_container);
		//Hide sign up block
		hide(sign_up_block);
		show(sign_in_block);
	}, 500); 
}

function showSignUpBlock() {
	//Hide signin block
	addAnimation(animation_SwipeAndHide, sign_in_block);
	//Show signup block
	addAnimation(animation_SwipeAndShow, sign_up_block);

	//Move signin block to end of sign_block 
	setTimeout(function() {
		//Remove animations
		removeAnimation(sign_in_block);
		removeAnimation(sign_up_block);
		//Move first block to end
		moveToEnd(sign_container);
		//Hide sign in block
		hide(sign_in_block);
		show(sign_up_block);
	}, 500); 
}

function moveToEnd(container) {
	var blk = container.removeChild(container.children[0]);
	container.appendChild(blk);
}

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





