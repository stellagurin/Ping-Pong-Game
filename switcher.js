function ImageGallery(image, ImageUrl){
	if (typeof ImageUrl == "object" || ImageUrl == "[object]") {
		document.getElementById(image).src = ImageUrl.src;
	} else {
		document.getElementById(image).src = ImageUrl;
	};
};
