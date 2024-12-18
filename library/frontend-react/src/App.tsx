import { useState } from 'react'
import './App.css'
import Login from './Components/LoginComponent/Login'
import AllBooks from './Components/BookComponent/allBooks'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Nav from './Components/NavigationLinks/Nav'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <h1>Welcome to the App Page using React</h1>
      <BrowserRouter>
        <Nav></Nav>
        <Routes>
          <Route path='/login' element={<Login></Login>}></Route>
        </Routes>
      </BrowserRouter>
      {/*<AllBooks></AllBooks> */}
    </>

  )
}

export default App
