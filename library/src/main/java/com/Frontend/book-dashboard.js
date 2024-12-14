let books = [
    {
        BookId:1,
        BookName: "Harry Potter and the Philosopher’s Stone",
        BookGenre: "Fantasy",
        image: "harry_potter1.jpg"
    },
    {
        BookId:2,
        BookName: "Harry Potter and the Chamber of Secrets",
        BookGenre: "Fantasy",
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
            <h3 class="book_title"> ${book.BookName} </h3>
            <p class="book_genre">${book.BookGenre}</p>
            <button class="book_button" data-id=${book.BookId}>Book</button>
        `   
        book_list.appendChild(bookItem)
    }
}

populateBooks(books)

