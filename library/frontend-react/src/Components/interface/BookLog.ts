import { User } from "./User";

// Fetch books from backend
export interface Book{
    bookLogId: number,
    user: User,
    book: Book,
    dateIssued: Date,
    date_to_be_returned: Date,
    date_actually_returned: Date
}