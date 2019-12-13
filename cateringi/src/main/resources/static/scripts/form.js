const containers = document.getElementsByClassName('field-container');
const fields = document.getElementsByClassName('field');
var i;

for(i=0;i<fields.length;i++) {
    if(fields[i].value) {
        containers[i].classList.add('active');
    }
}

for(i=0;i<fields.length;i++) {
    fields[i].number= i;
    fields[i].addEventListener('focus', (e) => {
        containers[e.currentTarget.number].classList.add('active');
    })

    fields[i].addEventListener('blur', (e) => {
        if(!fields[e.currentTarget.number].value) {
        containers[e.currentTarget.number].classList.remove('active');
        }
    })

}