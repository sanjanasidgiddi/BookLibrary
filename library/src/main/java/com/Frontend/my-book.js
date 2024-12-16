let books = [
    {
        BookId:1,
        BookName: "Harry Potter and the Philosopher’s Stone",
        BookGenre: "fiction",
        image: "harry_potter1.jpg",
        dueDate: "12/30/24"
    },
    {
        BookId:2,
        BookName: "Harry Potter and the Chamber of Secrets",
        BookGenre: "fiction",
        image: "harry_potter2.jpg",
        dueDate: "12/30/24"
    }
]

let bookList=document.getElementById('book_list_id')

function populateBooks(books){
    for(const book of books){
        let bookItem=document.createElement('li')
        bookItem.className='book_item'

        bookItem.innerHTML=`
            <img src=${book.image} alt="book image" class="book_image" height="256" width="81">
            <span class="book_genre">${book.BookGenre}</span>
            <h3 class="book_title"> ${book.BookName} </h3>
            <h3 class="due_date"> Due: ${book.dueDate} </h3>
        `   
        bookList.appendChild(bookItem)
    }
}

populateBooks(books)

