import { Link } from "react-router-dom"
import "./Nav.css"

function Nav() {
    return (
        <div>
            <nav>
                <ul>
                    <li className="link-styles"><Link to="/login">Login Page</Link></li>
                    <li className="link-styles"><Link to="/register">Register New User Page</Link></li>
                </ul>
            </nav>
        </div>
    )
}

export default Nav
