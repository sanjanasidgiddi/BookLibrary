import "./Register.css"
import { UserInfo } from '../../App';
import { SyntheticEvent, useContext, useState } from "react";
import axios from "axios";

function Register() {
    /** We will store info about the currently logged in user, since they login at the same time as registration */
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
        axios.post("http://localhost:8080/users/register",
            { username, password }, { withCredentials: true }
        ).then((res) => {
            console.log(res.data);
            userAuth?.setUsername(res.data.username);
            userAuth?.setRole(res.data.role);
        }).catch((err) => {
            console.log(err);
        })
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
                    <h1>Register</h1>
                    <div className="input_box">
                        <input type="text" id="firstNameInput" placeholder="First Name" required />
                    </div>

                    <div className="input_box">
                        <input type="text" id="lastNameInput" placeholder="Last Name" required />
                    </div>

                    <div className="input_box">
                        <input type="email" id="emailInput" placeholder="Email" required />
                    </div>

                    <div className="input_box">
                        <input type="tel" id="phoneNumberInput" placeholder="Phone Number" required />
                    </div>

                    <div className="input_box">
                        <input type="date" id="dateOfBirthInput" title="Date of Birth" placeholder="Date of Birth" required />
                    </div>
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
                    <div className="input_box">
                        <input type="password" id="re_passwordInput" placeholder="Re-enter Password" required />
                    </div>
                    <button className="button" type="submit" onClick={submitUnamePass}> Register </button>
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

export default Register
