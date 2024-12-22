import { BookLog } from "../interface/BookLog"
import axios from "axios"
import { useEffect, useState } from "react"
import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";

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
        <div style={{ width: '100%', padding: 0, margin: 0 }}>
            <h1> Library Logs </h1>
            <TableContainer component="div" style={{ width: '100%', margin: '0 auto' }}>
            <Table sx={{ minWidth: 650 }} aria-label="library logs table">
                <TableHead>
                <TableRow>
                    <TableCell>ID</TableCell>
                    <TableCell>Username</TableCell>
                    <TableCell>Book ID</TableCell>
                    <TableCell>Date Issued</TableCell>
                    <TableCell>Date to be Returned</TableCell>
                    <TableCell>Date Actually Returned</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {libraryLogs.map((log) => (
                    <TableRow key={log.bookLogId}>
                    <TableCell>{log.bookLogId}</TableCell>
                    <TableCell>{log.user.username}</TableCell>
                    <TableCell >{log.book.bookId}</TableCell>
                    <TableCell>{formatDate(log.dateIssued)}</TableCell>
                    <TableCell>{formatDate(log.dateToBeReturned)}</TableCell>
                    <TableCell>{formatDate(log.dateActuallyReturned)}</TableCell>
                    </TableRow>
                ))}
                </TableBody>
            </Table>
            </TableContainer>
        </div>
    )
}

export default LibraryLogs