const register_user = document.getElementById('register_user')

//get current values from html
let firstNameInput=document.getElementById("firstNameInput")
let lastNameInput=document.getElementById("lastNameInput")
let emailInput=document.getElementById("emailInput")
let phoneNumberInput=document.getElementById("phoneNumberInput")
let dateOfBirthInput=document.getElementById("dateOfBirthInput")
let usernameInput=document.getElementById("usernameInput")
let passwordInput=document.getElementById("passwordInput")
let re_passwordInput=document.getElementById("re_passwordInput")

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




