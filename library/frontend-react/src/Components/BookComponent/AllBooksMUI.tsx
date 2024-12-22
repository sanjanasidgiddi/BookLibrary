import { useEffect, useState } from 'react';
import { Book } from "../interface/Book"
import axios from "axios"
import backgroundImg from './background_img.jpg';
import { Button, Card, CardActionArea, CardActions, CardContent, CardMedia, Divider, IconButton, InputBase, Paper, Typography } from '@mui/material';


function AllBooks() {

  const [allBooks, setAllBooks] = useState<Book[]>([])
  const [filteredBooks, setFilteredBooks] = useState<Book[]>([]);
  const [visibleBooks, setVisibleBooks] = useState<Book[]>([]);
  const [searchQuery, setSearchQuery] = useState<string>('');

  useEffect(() => {
    // This will execute when the component mounts and on certain other conditions
    // Send an AXIOS request when the page loads
    axios.get<Book[]>("http://localhost:8080/books", { withCredentials: true })
      .then((res) => {
        setAllBooks(res.data)
        setVisibleBooks(res.data.slice(0, 5));
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

  const handleSearch = (query: string) => {
    setSearchQuery(query);
    if (query === '') {
      setVisibleBooks(allBooks.slice(0, 5)); // Reset to first 5 books if query is empty
    } else {
      const lowerCaseQuery = query.toLowerCase();
      const filteredBooks = allBooks.filter(
        (book) =>
          book.bookName.toLowerCase().includes(lowerCaseQuery) ||
          book.bookGenre.toLowerCase().includes(lowerCaseQuery)
      );
      setVisibleBooks(filteredBooks.slice(0, 5)); // Show only the first 5 filtered books
    }
  };

  // Handle issuing a book
  const borrowBook = (bookId: number) => {
    axios.post(`http://localhost:8080/bookLogs/${bookId}`)
      .then(response => {
        axios.get<Book[]>("http://localhost:8080/books", { withCredentials: true })
        .then((response) => setAllBooks(response.data));
        console.log("Book issued:", response.data);
        // Optionally, update the UI to reflect the issued book
      })
      .catch(error => {
        console.error("Error issuing book:", error);
        alert("Failed to issue the book.");
      });
  }

      
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
      <Paper
        component="form"
        sx={{
          p: '2px 4px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center', // This centers the content horizontally
          width: 400,
          margin: 'auto', // This will ensure the Paper is centered in its container
        }}
      >
        <InputBase
          sx={{
            ml: 1,
            flex: 1,
            textAlign: 'center', // This centers the text inside the input
          }}
          placeholder="Search books..."
          inputProps={{ 'aria-label': 'search books' }}
          value={searchQuery}
          onChange={(e) => handleSearch(e.target.value)}
        />
        <IconButton type="button" sx={{ p: '10px' }} aria-label="search">
          {/* <SearchIcon /> */}
        </IconButton>
      </Paper>


      {allBooks.length === 0 ? (
        <Typography variant="h6">No books available.</Typography>
      ) : (
        <div
          style={{
            display: "flex",
            flexWrap: "wrap", 
            justifyContent: "space-evenly", 
            gap: "20px", 
            padding: "10px"
          }}
        >
          {visibleBooks.map((book) => {
            return (
              <Card key={book.bookId} sx={{ maxWidth: 250, marginBottom: 2 }}>
                <CardActionArea>
                  <CardMedia
                    component="img"
                    sx={{
                      width: '100%',
                      height: 'auto',
                      aspectRatio: '5/8', 
                      objectFit: 'cover', 
                    }}
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
                    onClick={() => borrowBook(book.bookId)}
                  >
                    Issue
                  </Button>
                </CardActions>
              </Card>
            );
          })}
        </div>
      )}
    </div>
  );
}
export default AllBooks
