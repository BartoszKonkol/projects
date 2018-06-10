
function onFileChange(event)
{
	var file = event.target.files[0];
	var reader = new FileReader();
	reader.addEventListener("load", function(e)
	{
		document.getElementById("file").value = e.target.result;
	});
	reader.readAsDataURL(file);
}

function doDefineLocation()
{
	function onDefineLocation(location)
	{
		var latitude = location.coords.latitude;
		var longitude = location.coords.longitude;
		document.getElementById("latitude").value = latitude;
		document.getElementById("longitude").value = longitude;
		document.getElementById("location").value = latitude + "," + longitude;
	}
	navigator.geolocation.getCurrentPosition(onDefineLocation);
}
