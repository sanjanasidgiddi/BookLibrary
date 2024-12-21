import { useEffect, useState } from 'react';
import { Book } from "../interface/Book"
import "./MyBooks.css";
import axios from "axios"
import backgroundImg from './background_img.jpg';


function MyBooks() {
    
  const [myBooks, setMyBooks] = useState<Book[]>([])  
    
  useEffect(() => {
      // This will execute when the component mounts and on certain other conditions
      // Send an AXIOS request when the page loads
      axios.get<Book[]>("http://localhost:8080/books",{withCredentials:true})
          .then((res) => {
            setMyBooks(res.data)
          })
          .catch((error) => {
                console.error("Error fetching books:", error);
          });
  }, [])

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

  const returnBook = (bookId: number) => {
    axios.post(`http://localhost:8080/bookLogs/return/${bookId}`)
        .then(response => {
            console.log("Book returned:", response.data);

        })
        .catch(error => {
            console.error("Error returning book:", error);
            alert("Failed to return the book.");
        });
}

  return (
    <div>
    <h1>My Books Page</h1>
     <div id="dark-topright">
        <label>
            <i className='bx bx-moon' id="darklight" onClick={toggleDarkLight}>Light</i>
        </label>
    </div>
    {/* <div id="my_books_link">
        <a id="logout_text" title="All Books" href="book-dashboard.html">
          <i className='bx bxs-bookmarks'></i>
        </a>
    </div> */}
  <main className="content">
    <h1 className="page-title">My Books</h1>
    <ul id="book_list_id" className="book_list">
    {myBooks.map((book) => (
        <li key={book.bookId}>
            <img src={book.image} alt="book image" className="book_image" height="256" width="81" />
            <span className="book_genre">{book.bookGenre}</span>
            <h3 className="book_title"> {book.bookName} </h3>
            <button
                className="book_button"
                data-id={book.bookId}
                onClick={() => returnBook(book.bookId)}
            >
                Return
            </button>
        </li>
    ))}
    </ul>
    </main>
    </div>
  )
}

export default MyBooks
