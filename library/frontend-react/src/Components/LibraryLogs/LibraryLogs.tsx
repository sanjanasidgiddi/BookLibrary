import { BookLog } from "../interface/BookLog"
import axios from "axios"
import { useEffect, useState } from "react"
import { Button } from "@mui/material";

function LibraryLogs() {
    const [libraryLogs, setlibraryLogs] = useState<BookLog[]>([])

    useEffect(() => {
        // This will execute when the component mounts and on certain other conditions
        // Send an AXIOS request when the page loads
        axios.get<BookLog[]>("http://localhost:8080/BookLogs")
            .then((res) => {
                setlibraryLogs(res.data)
            })
            .catch((error) => {
                console.error("Error fetching books:", error);
            });
    }, [])

    const formatDate = (date: Date | null) => {
        if (!date) return "N/A"; // Return N/A if date is missing
        const formattedDate = new Date(date);
        return formattedDate.toLocaleDateString("en-US"); 
    };

    return (
        <div>
            <h1> Library Logs </h1>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Book ID</th>
                        <th>Date Issued</th>
                        <th>Date to be Returned</th>
                        <th>Date Actually Returned</th>
                    </tr>
                </thead>
                <tbody>
                    {libraryLogs.map((log) => {
                        return (
                            <tr key={log.bookLogId}>
                                <td>{log.user.username}</td>
                                <td>{log.book.bookId}</td>
                                <td>{formatDate(log.dateIssued)}</td>
                                <td>{formatDate(log.dateToBeReturned)}</td>
                                <td>{formatDate(log.dateActuallyReturned)}</td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>

        </div>
    )
}

export default LibraryLogs