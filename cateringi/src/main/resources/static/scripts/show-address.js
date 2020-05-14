const address = document.getElementById("address-select");
const form = document.getElementsByTagName("form");
const addrFields = document.getElementsByClassName("field");
const addrLabels = document.getElementsByClassName("addr-label");
const checkbox = document.getElementById("save");
const checkboxLabel = document.getElementById("save-label");
const order = document.getElementById("submit-order");

address.addEventListener('change', () => {
    if(address.options[address.selectedIndex].value == "inny"){
        for(i = 0; i < addrLabels.length; i++){
            addrFields[i].style.display = "block";
            addrLabels[i].style.display = "block";
        }
        addrFields[0].required = true;
        addrFields[2].required = true;
        addrFields[3].required = true;
        checkbox.style.display = "inline";
        checkboxLabel.style.display = "inline";
    }
    else{
        for(i = 0; i < addrLabels.length; i++){
            addrFields[i].style.display = "none";
            addrLabels[i].style.display = "none";
            addrFields[i].required = false;

        }
        checkbox.style.display = "none";
        checkboxLabel.style.display = "none";
    }
})

if(address.childElementCount == 1){
    for(i = 0; i < addrLabels.length; i++){
        addrFields[i].style.display = "block";
        addrLabels[i].style.display = "block";
    }
    addrFields[0].required = true;
    addrFields[2].required = true;
    addrFields[3].required = true;
    checkbox.style.display = "inline";
    checkboxLabel.style.display = "inline";
}

//addrFields[0].addEventListener('input', validateStreet);
//
//function validateStreet(field) {
//
//}

