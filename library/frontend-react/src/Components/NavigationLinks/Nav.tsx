import { Link } from "react-router-dom"
import "./Nav.css"

function Nav() {
    return (
        <div>
            <nav>
                <ul>
                    <li className="login-link"><Link to="/login">Login Page</Link></li>
                </ul>
            </nav>
        </div>
    )
}

export default Nav
