import { createContext, useEffect, useState } from 'react'
import './App.css'
import Login from './Components/LoginComponent/Login'
import AllBooks from './Components/BookComponent/AllBooks'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Nav from './Components/NavigationLinks/Nav'
import Register from './Components/RegisterComponent/Register'
import axios from 'axios'
import BookList from './Components/BookList/BookList'
import MyBooks from './Components/MyBooks/MyBooks'
import { User } from './Components/interface/User'
import NavMUI from './Components/NavigationLinks/NavMUI'
import BookListMUI from './Components/BookList/BookListMUI'
import AllBooksMUI from './Components/BookComponent/AllBooksMUI'
import MyBooksMUI from './Components/MyBooks/MyBooksMUI'


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
          {/* <Nav></Nav> */}
          <NavMUI></NavMUI>
          <Routes>
            <Route path='/login' element={<Login></Login>}></Route>
            <Route path='/register' element={<Register></Register>}></Route>
            {/* <Route path='/allbooks' element={<AllBooks></AllBooks>}></Route> */}
            <Route path='/allbooks' element={<AllBooksMUI></AllBooksMUI>}></Route>
            {/* <Route path='/booklist' element={<BookList></BookList>}></Route> */}
            <Route path='/booklistmui' element={<BookListMUI></BookListMUI>}></Route>
            {/* <Route path='/mybooks' element={<MyBooks></MyBooks>}></Route> */}
            <Route path='/mybooks' element={<MyBooksMUI></MyBooksMUI>}></Route>
          </Routes>
        </BrowserRouter>
      </UserInfo.Provider>
    </>

  )
}

export default App