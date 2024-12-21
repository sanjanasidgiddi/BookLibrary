import { User } from "./User";
import { Book } from "./Book";

export interface BookLog {
    bookLogId: number;
    user: User;
    book: Book; 
    dateIssued: Date;
    date_to_be_returned: Date;
    date_actually_returned: Date;
}
