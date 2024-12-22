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
        <div
        style={{
            display:"flex",
            flexDirection:"row",
            justifyContent:"space-evenly"
        }}>
      {myBooks.length === 0 ? (
        <Typography variant="h6">No books available.</Typography>
      ) : (
        myBooks.map((log) => {
            return(
          <Card key={log.bookLogId} sx={{ maxWidth: 250, marginBottom: 2 }}>
            <CardActionArea>
              <CardMedia
                component="img"
                height="100"
                sx={{
                    width: '100%',
                    height: 'auto',  // Ensures the height adjusts automatically based on the width
                    aspectRatio: '5/8', // Maintain 9:16 aspect ratio
                    objectFit: 'cover', // Ensures the image is properly cropped or scaled
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
                onClick={() => returnBook(log.book.bookId)}
              >
                Return
              </Button>
            </CardActions>
          </Card>
        )})
      )}
    </div></div>
);
  
}


export default MyBooks;
