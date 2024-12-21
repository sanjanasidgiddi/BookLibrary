import { AppBar, Toolbar, IconButton, Typography, Box, Button } from "@mui/material"
import axios from "axios"
import { Link, useNavigate } from "react-router-dom"
import { useContext } from "react"
import { UserInfo } from "../../App";

function NavMUI() {
    /** Only logged in users can access user information */
    const userAuth = useContext(UserInfo)

    const navigate = useNavigate()

    let navToPage = (location: string) => {
        navigate(location)
    }

    /** Action upon clicking logout button */
    let logOut = () => {
        axios.post('http://localhost:8080/users/logout', {}, { withCredentials: true })
            .then((res) => {
                userAuth?.setUsername('')
                userAuth?.setRole('unauthenticated')
                navigate('/login')
            })
    }

    return (
        <>
            <AppBar component="nav">
                <Toolbar>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        edge="start"
                        sx={{ mr: 2, display: { sm: 'none' } }}
                    >
                    </IconButton>
                    <Typography
                        variant="h6"
                        component="div"
                        sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' } }}
                    >
                        MUI
                    </Typography>
                    <Box sx={{ display: { xs: 'none', sm: 'block' } }}>
                        {/* Conditionally rendering login link, or logout button based on whether a user is logged in */}
                        {
                            userAuth?.role == "unauthenticated" ?
                                <Button variant="contained" color="secondary" onClick={() => navToPage('/login')}>Login</Button> :
                                <Button variant="contained" color="secondary" onClick={logOut}>Logout</Button>
                        }
                        <Button variant="contained" color="secondary" onClick={() => navToPage('/register')}>Register</Button>
                        <Button variant="contained" color="secondary" onClick={() => navToPage('/allbooks')}>All Books</Button>
                        <Button variant="contained" color="secondary" onClick={() => navToPage('/booklist')}>Book List</Button>
                        <Button variant="contained" color="secondary" onClick={() => navToPage('/mybooks')}>My Books</Button>
                    </Box>
                </Toolbar>
            </AppBar>
            <Toolbar />
        </>
    )
}

export default NavMUI
