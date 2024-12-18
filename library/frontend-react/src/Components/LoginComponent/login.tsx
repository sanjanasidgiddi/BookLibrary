import 'boxicons'

function login() {


  function toggleDarkLight() {
    var darkl_button = document.getElementById('darklight')
    /* Toggle Button Text */
    let current_text = darkl_button!.innerText;
    darkl_button!.innerText = current_text === 'Dark' ? 'Light' : 'Dark';
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

  const login_user = document.getElementById('login_user')
  login_user!.addEventListener('submit', function (event) {
    event.preventDefault();

    console.log("User Logged in Successfully")
    window.location.href = 'book-dashboard.html'
  })

  return (
    <div>
      <div id="dark-topright">
        <label htmlFor="darklight">Appearance</label>
        <button id="darklight" onClick={toggleDarkLight}>Light</button>
      </div>
      <div className="wrapper">
        <form action="" id="login_user">
          <h1>Login</h1>
          <div className="input_box">
            <input type="text" placeholder="Username" required />
            <i className='bx bxs-user' id="usernameInput"></i>
          </div>
          <div className="input_box">
            <input type="password" id="passwordInput" placeholder="Password" required />
            <i className='bx bxs-lock-alt' ></i>
          </div>

          <button className="button" type="submit"> Login </button>
          <div className="register">
            <p>Don't have an account?
              <a href="register.html">Register</a>
            </p>
          </div>
        </form>
      </div>
    </div>
  )
}

export default login
