const register_user = document.getElementById('register_user')

//get current values from html
let darkl_button = document.getElementById('darklight')
let firstNameInput=document.getElementById("firstNameInput")
let lastNameInput=document.getElementById("lastNameInput")
let emailInput=document.getElementById("emailInput")
let phoneNumberInput=document.getElementById("phoneNumberInput")
let dateOfBirthInput=document.getElementById("dateOfBirthInput")
let usernameInput=document.getElementById("usernameInput")
let passwordInput=document.getElementById("passwordInput")
let re_passwordInput=document.getElementById("re_passwordInput")

/** Change page appearance to Dark or Light mode. */
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

register_user.addEventListener('submit',function(event){
    event.preventDefault();

    //get values
    firstNameValue=firstNameInput.value
    lastNameValue=lastNameInput.value
    emailValue=emailInput.value
    phoneNumberValue=phoneNumberInput.value
    dateOfBirthValue=dateOfBirthInput.value
    usernameValue=usernameInput.value
    passwordValue=passwordInput.value
    re_passwordValue=re_passwordInput.value

    if(passwordValue===re_passwordValue){
        //store values in object
        let users={
            first_name: firstNameValue,
            last_name: lastNameValue,
            email: emailValue,
            phone_number: phoneNumberValue,
            dob: dateOfBirthValue,
            password: passwordValue,
        
        };

        console.log("User Registered Successfully")
        window.location.href='book-dashboard.html'
    }
    else{
        alert("Passwords do not match!")
        console.log("Password Mismatch")
        window.location.reload()
    }

})




