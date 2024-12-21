import "./BookList.css"
import { Book } from "../interface/Book"
import axios from "axios"
import { useEffect, useState } from "react"
import NewBook from "./NewBook";

function BookList() {
    const [allBooks, setAllBooks] = useState<Book[]>([])
    const [showAddBookPopup, setShowAddBookPopup] = useState(false);

    useEffect(() => {
        // This will execute when the component mounts and on certain other conditions
        // Send an AXIOS request when the page loads
        axios.get<Book[]>("http://localhost:8080/books")
            .then((res) => {
                setAllBooks(res.data)
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

    /**Add new book */
    const addNewBookToList = (newBook: Book) => {
        setAllBooks((prevBooks) => [...prevBooks, newBook]);
      };

    /** Get all books from database */

    /** Tester Function: Sending a get request to the database and checking who the users are. */
    let getBooks = () => {
        axios.get("http://localhost:8080/books"
        ).then((res) => {
            console.log("Here are the current books in the database: ", res.data);
        }).catch((err) => {
            console.log(err);
        })
    }

    return (
        <div>
            <h1> Book List Page rendered!</h1>
            <div id="dark-topright">
                <label>
                    <i className='bx bx-moon' id="darklight" onClick={toggleDarkLight}>Light</i>
                </label>
            </div>
            <button onClick={() => setShowAddBookPopup(true)}>Add New Book</button>
            <h2>All Books</h2>
            <table>
                <thead>
                    <tr>
                        <th>Book ID</th>
                        <th>Book Name</th>
                        <th>Author</th>
                        <th>Genre</th>
                        <th>Age Limit</th>
                        <th>Image URL</th>
                    </tr>
                </thead>
                <tbody>
                    {allBooks.map((book) => {
                        return (
                            <tr key={book.bookId}>
                                <td>{book.bookName}</td>
                                <td>{book.author}</td>
                                <td>{book.bookGenre}</td>
                                <td>{book.bookAgeLimit}</td>
                                <td>{book.image}</td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>

            {showAddBookPopup && (
            <NewBook
            onClose={() => setShowAddBookPopup(false)}
            onBookAdded={addNewBookToList}
            />
        )}
        </div>
    )
}

export default BookList