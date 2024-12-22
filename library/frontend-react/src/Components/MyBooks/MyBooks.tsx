import { useEffect, useState } from 'react';
import { Book } from "../interface/Book"
import { BookLog } from "../interface/BookLog"
import "./MyBooks.css";
import axios from "axios"
import backgroundImg from './background_img.jpg';


function MyBooks() {

  const [myBooks, setMyBooks] = useState<BookLog[]>([])

  useEffect(() => {
    // This will execute when the component mounts and on certain other conditions
    // Send an AXIOS request when the page loads
    axios.get<BookLog[]>("http://localhost:8080/bookLogs")
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
      <button id="darklight" onClick={toggleDarkLight}>Dark</button>
      <h1>My Books</h1>
      {myBooks.length === 0 ? (
        <p>No books issued.</p>
      ) : (
        <ul>
          {myBooks.map((log) => (
            <li key={log.bookLogId}>
              <div>
                <h3>{log.book.bookName} by {log.book.author}</h3>
                <p>Issued on: {new Date(log.dateIssued).toLocaleDateString()}</p>
                <p>Return by: {new Date(log.dateToBeReturned).toLocaleDateString()}</p>
                {log.dateActuallyReturned ? (
                  <p>Returned on: {new Date(log.dateActuallyReturned).toLocaleDateString()}</p>
                ) : (
                  <button onClick={() => returnBook(log.bookLogId)}>Return Book</button>
                )}
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default MyBooks;
