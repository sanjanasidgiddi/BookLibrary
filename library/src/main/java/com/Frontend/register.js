const register_user = document.getElementById('register_button')

register_user.addEventListener('submit',function(event){
    event.preventDefault();
    window.location.href='book-dashboard.html';
})