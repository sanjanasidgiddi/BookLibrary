// import "./BookList.css"
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
        axios.get<Book[]>("http://localhost:8080/books", { withCredentials: true })
            .then((res) => {
                setAllBooks(res.data)
            })
            .catch((error) => {
                console.error("Error fetching books:", error);
            });
    }, [])

    /**Add new book */
    const addNewBookToList = (newBook: Book) => {
        setAllBooks((prevBooks) => [...prevBooks, newBook]);
    };

    const editBook = (book: Book) => {
        setBookToEdit(book);
        setShowAddBookPopup(true); 
      };

      const delBook = (bookId: number) => {
        axios.delete(`http://localhost:8080/books/${bookId}`, { withCredentials: true })
        .then(response => {
          axios.get<Book>(`http://localhost:8080/books/${bookId}`, { withCredentials: true })
          console.log("Book deleted:", response.data);
        })
        .catch(error => {
          console.error("Error deleting book:", error);
        });
      };

    /** Get all books from database */

    /** Tester Function: Sending a get request to the database getting all books. */
    let getBooks = () => {
        axios.get("http://localhost:8080/books", { withCredentials: true }
        ).then((res) => {
            console.log("Here are the current books in the database: ", res.data);
        }).catch((err) => {
            console.log(err);
        })
    }

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
            <div style={{ width: '100%', display: 'flex', justifyContent: 'flex-end', padding: '10px' }}>
            </div>
            <br />
            <Button variant="contained" color="secondary" style={{ marginBottom: '10px' }} onClick={() => setShowAddBookPopup(true)}>Add New Book</Button>
            {/* <h2>Book Dashboard</h2> */}
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
                        <TableCell className="table-header">Edit</TableCell>
                        <TableCell className="table-header">Delete</TableCell>
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
                                <TableCell>
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        onClick={() => delBook(book.bookId)}
                                    >
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        )
                    })}
                </TableBody>
            </Table>
            {/* <Button variant="contained" color="secondary" onClick={getBooks}>Get All Books</Button> */}

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