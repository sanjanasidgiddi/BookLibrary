import 'boxicons'
import './Login.css'
import { SyntheticEvent, useState } from 'react';

function Login() {

  const [username, setUsername] = useState<string>('')
  const [password, setPassword] = useState<string>('')

  let toggleDarkLight = () => {
    console.log("Dark mode clicked")
    const darkl_button = document.getElementById('darklight')
    /* Toggle Button Text */
    let current_text: string = darkl_button!.innerText;
    darkl_button!.innerText = current_text === 'Dark' ? 'Light' : 'Dark';
    /* Switch background and text colors using css class under wrapper */
    var theme_element = document.body;
    theme_element.classList.toggle("dark_mode");
  }

  return (
    <div>
      <div id="dark-topright">
        <label>
          <i className='bx bx-moon' id="darklight" onClick={toggleDarkLight}>Light</i>
        </label>
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

export default Login