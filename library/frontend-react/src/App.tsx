import { createContext, useEffect, useState } from 'react'
// import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Register from './Components/RegisterComponent/Register'
import axios from 'axios'
import { User } from './Components/interface/User'
import NavMUI from './Components/NavigationLinks/NavMUI'
import BookListMUI from './Components/BookList/BookListMUI'
import AllBooksMUI from './Components/BookComponent/AllBooksMUI'
import MyBooksMUI from './Components/MyBooks/MyBooksMUI'
import LibraryLogsMUI from './Components/LibraryLogs/LibraryLogsMUI'
import Login from './Components/LoginComponent/Login'


/** Storing information about the logged in user */
export interface UserInfoType {
  username: string,
  setUsername: (username: string) => void,
  role: "unauthenticated" | "USER" | "ADMIN" | "STARTUP",
  setRole: (role: "unauthenticated" | "USER" | "ADMIN" | "STARTUP") => void
}

export const UserInfo = createContext<UserInfoType | null>(null);

function App() {
  /** User info context */
  const [role, setRole] = useState<"unauthenticated" | "USER" | "ADMIN" | "STARTUP">('STARTUP')
  /** Shared username variable */
  const [username, setUsername] = useState<string>('')

  /** This ensures that the navigation links include either login or logout option based on user type */
  useEffect(() => {
    const logInUsername = sessionStorage.getItem('logInUsername');
    if (!logInUsername) {
      setUsername('');
      /* 1st time app loads. */
      setRole('STARTUP'); 
      return;
    }
    axios.get<User>(`http://localhost:8080/users/${logInUsername}`, { withCredentials: true })
      .then((res) => { 
        setUsername(res.data.username);
        setRole(res.data.role);
        alert("Currently signed in User Details ------> " + res.data)
      })
      .catch((err) => {
        console.log(err);
        setUsername('');
        setRole('unauthenticated');
      })
  }, [])

  return (
    <div>
      {/* <h1>Welcome to the Book Library </h1> */}
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
            {/* <Route path='/libraryLogs' element={<LibraryLogs></LibraryLogs>}></Route> */}
            <Route path='/libraryLogsMUI' element={<LibraryLogsMUI></LibraryLogsMUI>}></Route>
          </Routes>
        </BrowserRouter>
      </UserInfo.Provider>
    </div>

  )
}

export default App