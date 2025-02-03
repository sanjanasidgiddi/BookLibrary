import { AppBar, Toolbar, IconButton, Typography, Box, Button } from "@mui/material"
import axios from "axios"
import { Link, useNavigate } from "react-router-dom"
import { useContext, useEffect } from "react"
import { UserInfo } from "../../App";

function NavMUI() {
    /** Only logged in users can access user information */
    const userAuth = useContext(UserInfo)

    const navigate = useNavigate()

    let navToPage = (location: string) => {
        navigate(location)
    }

    useEffect(() => {
        console.log("Current userAuth state: ", userAuth);
      }, [userAuth]);
      
    /** Action upon clicking logout button */
    let logOut = () => {
        axios.post(`http://localhost:8080/users/logout`, {}, { withCredentials: true })
        .then((res) => {
            sessionStorage.removeItem('logInUsername'); 
            userAuth?.setUsername('');
            userAuth?.setRole('unauthenticated');
            alert("Logged out successfully! Returns Blank -----> " + res.data);
            navigate('/login'); // Redirect to login page
        })
        .catch((err) => {
            console.error("Logout Error: ", err);
            alert("Logout failed! Please try again.");
        });
    }

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

    return (
        <>
            <AppBar component="nav" sx={{ background: "RebeccaPurple" }}>
                <Toolbar sx={{ display: "flex", justifyContent: "space-between", width: "100%" }}>
                    {/* Left-aligned logo and title */}
                    <Typography
                        variant="h6"
                        component="div"
                        sx={{ display: { xs: 'none', sm: 'block' }, flexGrow: 0, paddingRight:"15px" }}
                    >
                        Book Library
                    </Typography>
    
                    {/* Left-aligned navigation buttons (space between them) */}
                    <Box sx={{ display: "flex", gap: 2, flexGrow: 1, justifyContent: "flex-start" }}>
                        <Button variant="contained" color="secondary" sx={{ color: "white", background: "rgba(0, 0, 0, 0.3)" }} onClick={() => navToPage('/allbooks')}>All Books</Button>
                        <Button variant="contained" color="secondary" sx={{ color: "white", background: "rgba(0, 0, 0, 0.3)" }} onClick={() => navToPage('/mybooks')}>My Books</Button>
                        <Button variant="contained" color="secondary" sx={{ color: "white", background: "rgba(0, 0, 0, 0.3)" }} onClick={() => navToPage('/booklistmui')}>Book List</Button>
                        {/* <Button variant="contained" color="secondary" sx={{ color: "white", background: "rgba(0, 0, 0, 0.3)" }} onClick={() => navToPage('/libraryLogs')}>Logs</Button> */}
                    </Box>
    
                    {/* Right-aligned buttons (Login, Logout, Register) */}
                    <Box sx={{ display: "flex", gap: 2 }}>
                        <Button variant="contained" color="secondary" sx={{ color: "white", background: "rgba(0, 0, 0, 0.3)" }} onClick={() => navToPage('/register')}>Register</Button>
                        {userAuth?.role === "unauthenticated" ||  userAuth?.role === "STARTUP" ? 
                            (<Button variant="contained" color="secondary" sx={{ color: "white", background: "rgba(0, 0, 0, 0.3)" }} onClick={() => navToPage('/login')}>Login</Button>) 
                            : 
                            (<Button variant="contained" color="secondary" sx={{ color: "white", background: "rgba(0, 0, 0, 0.3)" }} onClick={logOut}>Logout</Button>)
                        }
                    </Box>
                </Toolbar>
            </AppBar>
            <Toolbar />
            <br />
        </>
    )
    
}    
export default NavMUI
