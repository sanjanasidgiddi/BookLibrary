
function MyBooks() {
    
    // let bookList=document.getElementById('book_list_id')
    
    // function populateBooks(books:[]){
    //     for(const book of books){
    //         let bookItem=document.createElement('li')
    //         bookItem.className='book_item'
    
    //         bookItem.innerHTML=`
    //             <img src=${book.image} alt="book image" class="book_image" height="256" width="81">
    //             <span class="book_genre">${book.BookGenre}</span>
    //             <h3 class="book_title"> ${book.BookName} </h3>
    //             <h3 class="due_date"> Due: ${book.dueDate} </h3>
    //         `   
    //         bookList!.appendChild(bookItem)
    //     }
    // }
    
    // populateBooks(books)

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
    <h1>My Books Page</h1>
     <div id="dark-topright">
        <label>
            <i className='bx bx-moon' id="darklight" onClick={toggleDarkLight}>Light</i>
        </label>
    </div>
    {/* <div id="my_books_link">
        <a id="logout_text" title="All Books" href="book-dashboard.html">
          <i className='bx bxs-bookmarks'></i>
        </a>
    </div> */}
  <main className="content">
    <h1 className="page-title">My Books</h1>
    <ul id="book_list_id" className="book_list">
    </ul>
    </main>
    </div>
  )
}

export default MyBooks
