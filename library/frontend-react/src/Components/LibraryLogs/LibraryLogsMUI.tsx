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
        <div>
            <h1> Library Logs </h1>
            <TableContainer component={LibraryLogs}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                <TableRow>
                    <TableCell>ID</TableCell>
                    <TableCell align="right">Username</TableCell>
                    <TableCell align="right">Book ID</TableCell>
                    <TableCell align="right">Date Issued</TableCell>
                    <TableCell align="right">Date to be Returned</TableCell>
                    <TableCell align="right">Date Actually Returned</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {libraryLogs.map((log) => (
                    <TableRow
                    key={log.bookLogId}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                    <TableCell component="th" scope="row">
                        {log.bookLogId}
                    </TableCell>
                    <TableCell align="right">{log.user.username}</TableCell>
                    <TableCell align="right">{log.book.bookId}</TableCell>
                    <TableCell align="right">{formatDate(log.dateIssued)}</TableCell>
                    <TableCell align="right">{formatDate(log.dateToBeReturned)}</TableCell>
                    <TableCell align="right">{formatDate(log.dateActuallyReturned)}</TableCell>
                    </TableRow>
                ))}
                </TableBody>
            </Table>
            </TableContainer>

        </div>
    )
}

export default LibraryLogs