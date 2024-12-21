import { User } from "./User";
import { Book } from "./Book";

export interface BookLog {
    bookLogId: number;
    user: User;
    book: Book; 
    dateIssued: Date;
    dateToBeReturned: Date;
    dateActuallyReturned: Date;
}
