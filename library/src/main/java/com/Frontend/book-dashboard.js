let books = [
    {
        BookId:1,
        BookName: "Harry Potter and the Philosopher’s Stone",
        BookGenre: "fiction",
        image: "harry_potter1.jpg"
    },
    {
        BookId:2,
        BookName: "Harry Potter and the Chamber of Secrets",
        BookGenre: "fiction",
        image: "harry_potter2.jpg"
    }
]

let book_list=document.getElementById('book_list_id')

function populateBooks(books){
    for(const book of books){

        let bookItem=document.createElement('li')
        bookItem.className='book_item'

        bookItem.innerHTML=`
            <img src=${book.image} alt="book image" class="book_image" height="256" width="81">
            <span class="book_genre">${book.BookGenre}</span>
            <h3 class="book_title"> ${book.BookName} </h3>
            <button class="book_button" data-id=${book.BookId}>Book</button>
        `   
        book_list.appendChild(bookItem)
    }
}

populateBooks(books)

