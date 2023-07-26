function initialize (){
    var options = {
    types: ['(cities)']
}
var input1 = document.getElementById('location');
var autocomplete1 = new google.maps.places.Autocomplete(input1,options);
}
google.maps.event.addDomListener(window, 'load',initialize);
// var input2 = document.getElementById("to");
// var autocomplete1 = new google.maps.places.Autocomplete(input2,options);
