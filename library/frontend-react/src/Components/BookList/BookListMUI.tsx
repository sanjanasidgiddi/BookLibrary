import "./BookList.css"
import { Book } from "../interface/Book"
import axios from "axios"
import { useEffect, useState } from "react"
import NewBook from "./NewBook";
import { Button, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";

function BookListMUI() {
    const [allBooks, setAllBooks] = useState<Book[]>([])
    const [showAddBookPopup, setShowAddBookPopup] = useState(false);
    const [bookToEdit, setBookToEdit] = useState<Book | null>(null);

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

    /** Function to TableRowigger appearance theme change */
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

    const editBook = (book: Book) => {
        setBookToEdit(book);
        setShowAddBookPopup(true); 
      };

    /** Get all books from database */

    /** Tester Function: Sending a get request to the database getting all books. */
    let getBooks = () => {
        axios.get("http://localhost:8080/books"
        ).then((res) => {
            console.log("Here are the current books in the database: ", res.data);
        }).catch((err) => {
            console.log(err);
        })
    }

    return (
        <div className="booklist-container">
            <Button variant="contained" color="secondary" id="darklight" onClick={toggleDarkLight}>Dark</Button>
            <br />
            <Button variant="contained" color="secondary" onClick={() => setShowAddBookPopup(true)}>Add New Book</Button>
            <h2>Book Dashboard</h2>

            {/* Table Styling */}
            <Table className="book-table">
                <TableHead>
                    <TableRow>
                        <TableCell className="table-header">Book ID</TableCell>
                        <TableCell className="table-header">Book Name</TableCell>
                        <TableCell className="table-header">Author</TableCell>
                        <TableCell className="table-header">Genre</TableCell>
                        <TableCell className="table-header">Age Limit</TableCell>
                        <TableCell className="table-header">Image URL</TableCell>
                        <TableCell className="table-header">Actions</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {allBooks.map((book) => {
                        return (
                            <TableRow key={book.bookId}>
                                <TableCell>{book.bookId}</TableCell>
                                <TableCell>{book.bookName}</TableCell>
                                <TableCell>{book.author}</TableCell>
                                <TableCell>{book.bookGenre}</TableCell>
                                <TableCell>{book.bookAgeLimit}</TableCell>
                                <TableCell>{book.image}</TableCell>
                                <TableCell>
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        onClick={() => editBook(book)}
                                    >
                                        Edit
                                    </Button>
                                </TableCell>
                            </TableRow>
                        )
                    })}
                </TableBody>
            </Table>

            <Button variant="contained" color="secondary" onClick={getBooks}>Get All Books</Button>

            {showAddBookPopup && (
                <NewBook
                    onClose={() => {
                        setShowAddBookPopup(false);
                        setBookToEdit(null);
                    }}
                    onBookAdded={addNewBookToList}
                    bookToEdit={bookToEdit}
                />
            )}
        </div>
    )
}

export default BookListMUI