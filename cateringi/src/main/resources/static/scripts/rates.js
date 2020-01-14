stars = document.getElementsByClassName("fa-star");
avgrate = document.getElementById('avgRating').textContent;

for(var i=0;i<avgrate;i++) {
    stars[i].classList.add('checked');
}