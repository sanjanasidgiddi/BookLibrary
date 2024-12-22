import React, { useEffect, useState } from "react";
import axios from "axios";
import { Book } from "../interface/Book";

interface NewBookProps {
  onClose: () => void;
  onBookAdded: (book: Book) => void;
  bookToEdit: Book | null
}

function NewBook({ onClose, onBookAdded, bookToEdit }: NewBookProps) {
  const [formData, setFormData] = useState({
    bookName: "",
    author: "",
    bookGenre: "",
    bookAgeLimit: "",
    image: "",
  });

  useEffect(() => {
    if (bookToEdit) {
      setFormData({
        bookName: bookToEdit.bookName,
        author: bookToEdit.author,
        bookGenre: bookToEdit.bookGenre,
        bookAgeLimit: bookToEdit.bookAgeLimit.toString(),
        image: bookToEdit.image,
      });
    }
  }, [bookToEdit]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();


    const submitAction = bookToEdit
    ? axios.patch<Book>(`http://localhost:8080/books/${bookToEdit.bookId}`, formData, { withCredentials: true })
    : axios.post<Book>("http://localhost:8080/books", formData, { withCredentials: true });

    submitAction
    .then((response) => {
      console.log(bookToEdit ? "Book updated:" : "New book added:", response.data);
      onBookAdded(response.data);
      onClose();
    })
    .catch((error) => {
      console.error("Error submitting book:", error);
      alert("Failed to submit the book.");
    });
};

  return (
    <div className="popup-overlay">
      <div className="popup">
        <h2>{bookToEdit ? "Edit Book" : "Add a New Book"}</h2>
        <form onSubmit={handleSubmit}>
          <label>
            Book Name:
            <input
              type="text"
              name="bookName"
              value={formData.bookName}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Author:
            <input
              type="text"
              name="author"
              value={formData.author}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Genre:
            <input
              type="text"
              name="bookGenre"
              value={formData.bookGenre}
              onChange={handleChange}
            />
          </label>
          <label>
            Age Limit:
            <input
              type="number"
              name="bookAgeLimit"
              value={formData.bookAgeLimit}
              onChange={handleChange}
            />
          </label>
          <label>
            Image URL:
            <input
              type="text"
              name="image"
              value={formData.image}
              onChange={handleChange}
            />
          </label>
          <button type="submit">{bookToEdit ? "Update Book" : "Add Book"}</button>
          <button type="button" onClick={onClose}>
            Cancel
          </button>
        </form>
      </div>
    </div>
  );
}

export default NewBook;
