import React from 'react';
import "./AllBooks.css";
import backgroundImg from './background_img.jpg';

function AllBooks() {
  
    function toggleDarkLight() {
        var darkl_button = document.getElementById('darklight')
        /* Toggle Button Text */
        let current_text = darkl_button!.innerText;
        darkl_button!.innerText = current_text === 'Dark' ? 'Light' : 'Dark';
        /** Log it */
        console.log("Toggle button clicked!");
        /** Switch background and text colors using css class under wrapper */
        var theme_element = document.body;
        theme_element.classList.toggle("dark_mode");
        }
  
  
    return (
        <div>
            <nav>
            <div id="dark-topright">
                <label htmlFor="darklight">Appearance</label>
                <button id="darklight" onClick={toggleDarkLight}>Dark</button>
            </div>
            <div id="my_books_link">
                <a id="link_text" title="My Books" href="my-books.html">
                    <i className='bx bxs-bookmark-heart'></i>
                </a>
            </div>
            </nav>
            <header className="upper_block">
                <h1 id="qoute">Books are uniquely portable magic.</h1>
                <div className="book_search">
                    <input type="text" placeholder="Search your book" autoComplete="off"/>
                    <button><i className='bx bx-search'></i></button>
                </div>
            </header>
            <main className="book_container">
                <div className="wrapper">
                    <ul id="book_list_id" className="book_list">
                        
                    </ul>
                </div>
            </main>
        </div>
    )
}

export default AllBooks