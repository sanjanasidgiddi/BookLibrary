import { createContext, useEffect, useState } from 'react'
import './App.css'
import Login from './Components/LoginComponent/Login'
import AllBooks from './Components/BookComponent/allBooks'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Nav from './Components/NavigationLinks/Nav'
import Register from './Components/RegisterComponent/Register'
import axios from 'axios'

/** Storing information about the logged in user */
export interface UserInfoType {
  username: string,
  setUsername: (username: string) => void,
  role: "unauthenticated" | "USER" | "ADMIN",
  setRole: (role: "unauthenticated" | "USER" | "ADMIN") => void
}

export const UserInfo = createContext<UserInfoType | null>(null);

function App() {
  /** User info context */
  const [role, setRole] = useState<"unauthenticated" | "USER" | "ADMIN">('unauthenticated')
  /** Shared username variable */
  const [username, setUsername] = useState<string>('')

  /** This ensures that the navigation links include either login or logout option based on user type */
  useEffect(() => {
    axios.get<User>('http://localhost:8080/users', { withCredentials: true })
      .then((res) => { 
        setUsername(res.data.username);
        setRole(res.data.role);
      })
      .catch((err) => {
        console.log(err);
        setUsername('');
        setRole('unauthenticated');
      })
  }, [])

  return (
    <>
      <h1>Welcome to the App Page using React</h1>
      <UserInfo.Provider value={
        {
          username,
          setUsername,
          role,
          setRole
        }
      }>
        <BrowserRouter>
          <Nav></Nav>
          <Routes>
            <Route path='/login' element={<Login></Login>}></Route>
            <Route path='/register' element={<Register></Register>}></Route>
            <Route path='/allbooks' element={<AllBooks></AllBooks>}></Route>
          </Routes>
        </BrowserRouter>
      </UserInfo.Provider>
    </>

  )
}

export default App
