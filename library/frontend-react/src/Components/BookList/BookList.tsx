import "./BookList.css"
import { Book } from "../interface/Book"

function BookList() {

    // let books = [
    //     {
    //         book_id: 1,
    //         book_name: "Harry Potter and the Philosopher’s Stone",
    //         author: "JK Rowling",
    //         book_genre: "Fiction",
    //         book_age_limit: 8,
    //         image: "harry_potter1.jpg"
    //     },
    //     {
    //         book_id: 2,
    //         book_name: "Harry Potter and the Chamber of Secrets",
    //         author: "JK Rowling",
    //         book_genre: "Fiction",
    //         book_age_limit: 8,
    //         image: "harry_potter2.jpg"
    //     }
    // ]

    // let book_list = document.getElementById('book_list_id')

    // function populateBooks(props: Book[]) {
    //     for (const prop of props) {
    //         let bookData = document.createElement('tr')
    //         bookData.className = 'book_data'

    //         bookData.innerHTML = `
    //             <td>${prop.book_id}</td>
    //             <td>${prop.book_name}</td>
    //             <td>${prop.author}</td>
    //             <td>${prop.book_genre}</td>
    //             <td>${prop.book_age_limit}</td>
    //             <td><img src=${prop.image} alt="book image" class="book_image" height="150" width="105"></td>
    //         `
    //         book_list!.appendChild(bookData)
    //     }
    // }

    // populateBooks(books)

    // let book_popup = document.getElementById('add_book_link_text');

    // book_popup!.addEventListener('click', function (event) {
    //     event.preventDefault()
    //     openBookForm()
    // })

    // let openBookForm = () => {
    //     document.getElementById("id_form_popup")!.style.display = "block";
    // }

    // let closeForm = () => {
    //     document.getElementById("id_form_popup")!.style.display = "none";
    // }

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
            <nav>
            <h1> Book List Page rendered!</h1>
             <div id="dark-topright">
                <label>
                    <i className='bx bx-moon' id="darklight" onClick={toggleDarkLight}>Light</i>
                </label>
            </div>
            
            <header id="add_books">
                <span id="title">List of Books</span>
                <a id="add_book_link_text" title="Add Books" href="#">
                    <i className='bx bxs-book-add' ></i>
                </a>
            </header>
            <div className="form_popup" id="id_form_popup">
                <form id="form_add_books" />
                <h1>Add Book</h1>
                <input type="text" placeholder="Book Title" required />
                <input type="text" placeholder="Author" required />
                <div id="id_book_genre">
                    <label> Genre
                        <select>
                            <option value="Fiction">Fiction</option>
                            <option value="non-Fiction">non-Fiction</option>
                            <option value="Business">Business</option>
                            <option value="Self-Help">Self-Help</option>
                            <option value="Historical">Historical</option>
                        </select>
                    </label>
                </div>
                <input type="text" placeholder="Author" required />
                <input type="text" placeholder="Age Limit" required />
                <input type="text" placeholder="Book Cover Image Link" required />
                <button type="submit" className="add_book_button">Add</button>
                {/* <button type="button" className="cancel_button" onClick={closeForm}>Close</button> */}
                <button type="button" className="cancel_button">Close</button>
            </div> 
            </nav>
        </div>
    )
}

export default BookList