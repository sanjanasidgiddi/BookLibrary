import { useEffect, useState } from 'react';
import { Book } from "../interface/Book"
import "./AllBooks.css";
import axios from "axios"
import backgroundImg from './background_img.jpg';


function AllBooks() {

    const [allBooks, setAllBooks] = useState<Book[]>([])

    useEffect(() => {
        // This will execute when the component mounts and on certain other conditions
        // Send an AXIOS request when the page loads
        axios.get<Book[]>("http://localhost:8080/books")
            .then((res) => {
                setAllBooks(res.data)
            })
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


    return (
        <div>
            <div id="dark-topright">
                <label>
                    <i className='bx bx-moon' id="darklight" onClick={toggleDarkLight}>Light</i>
                </label>
            </div>
            <header className="upper_block">
                <h1 id="qoute">Books are uniquely portable magic.</h1>
                <div className="book_search">
                    <input type="text" placeholder="Search your book" autoComplete="off" />
                    <button><i className='bx bx-search'></i></button>
                </div>
            </header>
            <main className="book_container">
                <div className="wrapper">
                    <ul id="book_list_id" className="book_list">
                        {allBooks.map((book)=>{
                            return (
                                <li>
                                    <img src={book.image} alt="book image" className="book_image" height="256" width="81"/>
                                    <span className="book_genre">{book.bookGenre}</span>
                                    <h3 className="book_title"> {book.bookName} </h3>
                                    <button className="book_button" data-id={book.bookId}>Issue</button>
                                </li>
                            )
                        })}
                    </ul>
                </div>
            </main>
        </div>
    )
}

export default AllBooks