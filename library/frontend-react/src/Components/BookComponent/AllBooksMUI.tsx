import { useEffect, useState } from 'react';
import { Book } from "../interface/Book"
import axios from "axios"
import backgroundImg from './background_img.jpg';
import { Button, Card, CardActionArea, CardActions, CardContent, CardMedia, Typography } from '@mui/material';


function AllBooks() {

    const [allBooks, setAllBooks] = useState<Book[]>([])

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

    // Handle issuing a book
    const issueBook = (bookId: number) => {
        axios.post(`http://localhost:8080/bookLogs/${bookId}`)
            .then(response => {
                console.log("Book issued:", response.data);
                // Optionally, update the UI to reflect the issued book
            })
            .catch(error => {
                console.error("Error issuing book:", error);
                alert("Failed to issue the book.");
            });
    }


    return (
        <div>
          {allBooks.length === 0 ? (
            <Typography variant="h6">No books available.</Typography>
          ) : (
            allBooks.map((book) => {
                return(
              <Card key={book.bookId} sx={{ maxWidth: 345, marginBottom: 2 }}>
                <CardActionArea>
                  <CardMedia
                    component="img"
                    height="400"
                    image={book.image}
                    alt="Book cover"
                  />
                  <CardContent>
                    <Typography gutterBottom variant="h6" component="div">
                      {book.bookName}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {book.bookGenre}
                    </Typography>
                  </CardContent>
                </CardActionArea>
                <CardActions>
                  <Button
                    size="small"
                    color="primary"
                    onClick={() => issueBook(book.bookId)}
                  >
                    Issue
                  </Button>
                </CardActions>
              </Card>
            )})
          )}
        </div>
    );
      
}
export default AllBooks
