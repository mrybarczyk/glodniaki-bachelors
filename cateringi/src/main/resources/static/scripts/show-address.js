const address = document.getElementById("address-select");
const form = document.getElementsByTagName("form");
const diffAddr = document.getElementById("diff-addr");

address.addEventListener('change', () => {
    if(address.options[address.selectedIndex].value == "inny"){
        diffAddr.style.display = "block";
    }
    else{
        diffAddr.style.display = "none";
    }
})

if(address.childElementCount == 1){
    diffAddr.style.display = "block";
}