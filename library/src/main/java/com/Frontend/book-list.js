let books = [
    {
        BookId:1,
        BookName: "Harry Potter and the Philosopher’s Stone",
        Author: "JK Rowling",
        BookGenre: "Fantasy",
        BookAgeLimit: "8",
        image: "harry_potter1.jpg"
    },
    {
        BookId:2,
        BookName: "Harry Potter and the Chamber of Secrets",
        Author: "JK Rowling",
        BookGenre: "Fantasy",
        BookAgeLimit: "8",
        image: "harry_potter2.jpg"
    }
]

let book_list=document.getElementById('book_list_id')

function populateBooks(books){
    for(const book of books){
        let bookData=document.createElement('tr')
        bookData.className='book_data'

        bookData.innerHTML=`
            <td>${book.BookId}</td>
            <td>${book.BookName}</td>
            <td>${book.Author}</td>
            <td>${book.BookGenre}</td>
            <td>${book.BookAgeLimit}</td>
            <td><img src=${book.image} alt="book image" class="book_image" height="150" width="105"></td>
        `
        book_list.appendChild(bookData)
    }
}

populateBooks(books)

function openBookForm(){
    document.getElementById("id_form_popup").style.display="block";
}

function closeForm(){
    document.getElementById("id_form_popup").style.display = "none";
}