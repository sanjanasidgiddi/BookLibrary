import { useEffect, useState } from 'react';
import { Book } from "../interface/Book"
import { BookLog } from "../interface/BookLog"
import "./MyBooks.css";
import axios from "axios"
import backgroundImg from './background_img.jpg';
import { Button, Card, CardActionArea, CardActions, CardContent, CardMedia, Typography } from '@mui/material';


function MyBooks() {

  const [myBooks, setMyBooks] = useState<BookLog[]>([])

  useEffect(() => {
    // This will execute when the component mounts and on certain other conditions
    // Send an AXIOS request when the page loads
    axios.get<BookLog[]>("http://localhost:8080/bookLogs")
      .then((res) => {
        const unreturnedBooks = res.data.filter(log => !log.dateActuallyReturned);
        setMyBooks(unreturnedBooks)
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

  const returnBook = (bookLogId: number) => {
  console.log("Attempting to return book with bookLogId:", bookLogId);
  axios.post(`http://localhost:8080/bookLogs/return/${bookLogId}`)
    .then(response => {
      console.log("Book returned successfully:", response.data);
      setMyBooks(prevBooks => prevBooks.filter(log => log.bookLogId !== bookLogId));
    })
    .catch(error => {
      console.error("Error returning book:", error.response?.data || error.message);
      if (error.response?.status === 400) {
        alert("Bad Request: " + error.response.data.message);
      } else if (error.response?.status === 404) {
        alert("Book log not found");
      } else {
        alert("Failed to return the book. Please try again later.");
      }
    });
};

  

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
      <div style={{ width: '100%', display: 'flex', justifyContent: 'flex-end', padding: '10px' }}>
        <Button
          variant="contained"
          color="secondary"
          id="darklight"
          onClick={toggleDarkLight}
          style={{ marginRight: '10px' }}
        >
          Dark
        </Button>
      </div>
      <br />
      <div
        style={{
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-evenly",
          gap: "20px",
          padding: "10px"
        }}>
        {myBooks.length === 0 ? (
          <Typography variant="h6">No books borrowed.</Typography>
        ) : (
          myBooks.map((log) => {
            return (
              <Card key={log.bookLogId} sx={{ maxWidth: 250, marginBottom: 2 }}>
                <CardActionArea>
                  <CardMedia
                    component="img"
                    height="100"
                    sx={{
                      width: '100%',
                      height: 'auto',
                      aspectRatio: '5/8',
                      objectFit: 'cover',
                    }}
                    image={log.book.image}
                    alt="Book cover"
                  />
                  <CardContent>
                    <Typography gutterBottom variant="h6" component="div">
                      {log.book.bookName}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {log.book.bookGenre}
                    </Typography>
                  </CardContent>
                </CardActionArea>
                <CardActions>
                  <Button
                    size="small"
                    color="primary"
                    onClick={() => returnBook(log.bookLogId)}
                  >
                    Return
                  </Button>
                </CardActions>
              </Card>
            )
          })
        )}
      </div></div>
  );

}


export default MyBooks;
