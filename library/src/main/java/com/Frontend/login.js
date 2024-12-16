const login_user = document.getElementById('login_user')

var darkl_button = document.getElementById('darklight')

function toggleDarkLight(){
    /* Toggle Button Text */
    let current_text = darkl_button.innerText;
    darkl_button.innerText = current_text === 'Dark' ? 'Light' : 'Dark';
    /** Log it */
    console.log("Toggle button clicked!");
    /** Switch background and text colors using css class under wrapper */
    var theme_element = document.body;
    theme_element.classList.toggle("dark_mode");
}
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