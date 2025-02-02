import 'boxicons'
import './Login.css'
import axios from "axios"
import { SyntheticEvent, useContext, useState } from 'react';
import { UserInfo } from '../../App';

function Login() {
  /** Information about the currently logged in user */
  const userAuth = useContext(UserInfo)
  /** State variables username and password */
  const [username, setUsername] = useState<string>('')
  const [password, setPassword] = useState<string>('')

  /** Function to trigger appearance theme change */
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

  /** Set state variables upon submit */
  let submitUnamePass = () => {
    console.log("Username and password submitted!")
    console.log("Entered: ", username)
    console.log("Entered: ", password)
    /** Sending a post request to the database and setting authentication credentials/context. */
    axios.post("http://localhost:8080/users/login", { username, password }, {
      headers: { 'Content-Type': 'application/json' },
      withCredentials: true,
    })
    .then((res) => {
      console.log("Login Successful: ", res.data);
      alert("Login Successful, Welcome :) " + res.data);
    })
    .catch((err) => {
      console.error("Axios Error: " + err);
      alert("Login Failed! Error -----> " + (err.response ? err.response.data : err.message));
    });
  }

  /** Tester Function: Sending a get request to the database and checking who the users are. */
  let getusers = () => {
    axios.get("http://localhost:8080/users"
    ).then((res) => {
      console.log("Here are the current users in the database: ", res.data);
    }).catch((err) => {
      console.log(err);
    })
  }

  return (
    <div>
      <button id="darklight" onClick={toggleDarkLight}>DARK</button>
      <button onClick={getusers}>Get all users</button>
      <div className="wrapper">
        <form action="" id="login_user">
          <h1>Login</h1>
          <div className="input_box">
            <input type="text"
              placeholder="Username" required
              value={username}
              onChange={(e: SyntheticEvent) => { setUsername((e.target as HTMLInputElement).value) }} />
            <i className='bx bxs-user' id="usernameInput"></i>
          </div>
          <div className="input_box">
            <input type="password"
              id="passwordInput"
              placeholder="Password" required
              value={password}
              onChange={(e: SyntheticEvent) => { setPassword((e.target as HTMLInputElement).value) }} />
            <i className='bx bxs-lock-alt' ></i>
          </div>

          <button className="button" type="button" onClick={submitUnamePass}> Login </button>
          {/* <button className="button" type="submit" onClick={getusers}> Get Current users in Database </button> */}
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