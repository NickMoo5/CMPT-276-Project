var map;
var geocoder;

async function initMap() {
    
    // async library loading
    const { Map } = await google.maps.importLibrary("maps");
    const {Geocoder} = await google.maps.importLibrary("geocoding");

    // new geocoder to grab latlan of city
    var geocoder = new google.maps.Geocoder();

    //make new map 
    map = new Map(document.getElementById("map"), {
        center: { lat: 0, lng: 0 },
        zoom: 15,
    });

    //geocodes place based on string result and if successful, updates map location to said point
    geocoder.geocode({ 'address': "Toronto" }, function (results, status) {
        if (status === 'OK') {
            map.setCenter(results[0].geometry.location);
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}

initMap();