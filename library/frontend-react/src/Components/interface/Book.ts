// Fetch books from backend
export interface Book{
    bookId: number,
    bookName: string,
    author: string,
    bookGenre: string,
    bookAgeLimit: number,
    image: string
}