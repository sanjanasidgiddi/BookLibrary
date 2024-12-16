const login_user = document.getElementById('login_user')

//let usernameInput=document.getElementById("usernameInput")
//let passwordInput=document.getElementById("passwordInput")

//get values
//let usernameValue=usernameInput.value
//let passwordValue=passwordInput.value

//store values in object
/*let users={
    username: usernameValue,
    password: passwordValue
}*/

login_user.addEventListener('submit',function(event){
    event.preventDefault();

    console.log("User Logged in Successfully")
    window.location.href='book-dashboard.html'
})