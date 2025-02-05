import { BookLog } from "../interface/BookLog"
import axios from "axios"
import { useEffect, useState } from "react"
import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";

function LibraryLogs() {
  const [libraryLogs, setlibraryLogs] = useState<BookLog[]>([])

    useEffect(() => {
        // This will execute when the component mounts and on certain other conditions
        // Send an AXIOS request when the page loads
        axios.get<BookLog[]>("http://localhost:8080/bookLogs", { withCredentials: true })
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

    // const handleReturnBook = (bookLogId: number) => {
    //   axios.post(`http://localhost:8080/bookLogs/return/${bookLogId}`)
    //     .then(() => {
    //       fetchLogs();
    //     })
    //     .catch((error) => {
    //       console.error("Error returning book:", error);
    //     });
    // };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
      <div style={{ width: '100%', display: 'flex', justifyContent: 'flex-end', padding: '10px' }}>
      </div>
      <br />
      <h1 style={{ marginBottom: '20px', textAlign: 'center' }}>Library Logs</h1>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Book Log ID</TableCell>
            <TableCell>Reader Name</TableCell>
            <TableCell>Book Name</TableCell>
            <TableCell>Date Issued</TableCell>
            <TableCell>Return Date</TableCell>
            <TableCell>Date Actually Returned</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {libraryLogs.length > 0 ? (
            libraryLogs.map((log) => (
              <TableRow key={log.bookLogId}>
                <TableCell>{log.bookLogId}</TableCell>
                <TableCell>{log.user.firstName} {log.user.lastName}</TableCell>
                <TableCell>{log.book.bookName}</TableCell>
                <TableCell>{formatDate(log.dateIssued)}</TableCell>
                <TableCell>{formatDate(log.dateToBeReturned)}</TableCell>
                <TableCell>{formatDate(log.dateActuallyReturned)}</TableCell>
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={6} style={{ textAlign: 'center' }}>
                No logs available.
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  )
}

export default LibraryLogs