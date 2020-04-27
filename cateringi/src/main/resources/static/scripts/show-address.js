const address = document.getElementById("address-select");
const form = document.getElementsByTagName("form");
const addrFields = document.getElementsByClassName("field");
const addrLabels = document.getElementsByClassName("addr-label");
const checkbox = document.getElementById("save");
const checkboxLabel = document.getElementById("save-label");

address.addEventListener('change', () => {
    if(address.options[address.selectedIndex].value == "inny"){
        for(i = 0; i < addrLabels.length; i++){
            addrFields[i].style.display = "block";
            addrLabels[i].style.display = "block";
        }
        checkbox.style.display = "block";
        checkboxLabel.style.display = "block";
    }
    else{
        for(i = 0; i < addrLabels.length; i++){
            addrFields[i].style.display = "none";
            addrLabels[i].style.display = "none";
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
    checkbox.style.display = "block";
    checkboxLabel.style.display = "block";
}