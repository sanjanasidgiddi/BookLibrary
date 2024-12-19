import { Link, useNavigate } from "react-router-dom"
import "./Nav.css"
import axios from "axios";
import { useContext } from "react";
import { UserInfo } from "../../App";

function Nav() {
    /** Only logged in users can access user information */
    const userAuth = useContext(UserInfo)
    /** Used to rerender page based on login */
    const navigate = useNavigate();

    /** Action upon clicking logout button */
    let logOut = () => {
        axios.post('http://localhost:8080/users/logout', {}, {withCredentials: true})
        .then((res) => {
            userAuth?.setUsername('')
            userAuth?.setRole('unauthenticated')
            navigate('/login')
        })
    }
    return (
        <div>
            <nav>
                <ul>
                    {/* Conditionally rendering login link, or logout button based on whether a user is logged in */}
                    {
                        userAuth?.role == "unauthenticated" ?
                        <li className="link-styles"><Link to="/login">Login Page</Link></li> :
                        <li><button onClick={logOut}>Logout</button></li>
                    }
                    {/* Registration page */}
                    <li className="link-styles"><Link to="/register">Register New User Page</Link></li>
                    {/* Link to all books page */}
                    <li className="link-styles"><Link to="/allbooks">All Books</Link></li>
                    {/* Link to books list page */}
                    <li className="link-styles"><Link to="/booklist">Book List</Link></li>
                </ul>
            </nav>
        </div>
    )
}

export default Nav
