import React, { useState } from "react";
import axios from "axios";
import { Book } from "../interface/Book";

interface NewBookProps {
  onClose: () => void;
  onBookAdded: (book: Book) => void;
}

function NewBook({ onClose, onBookAdded }: NewBookProps) {
  const [formData, setFormData] = useState({
    bookName: "",
    author: "",
    bookGenre: "",
    bookAgeLimit: "",
    image: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    axios
      .post<Book>("http://localhost:8080/books", formData, { withCredentials: true })
      .then((response) => {
        console.log("New book added:", response.data);
        onBookAdded(response.data); 
        onClose();
      })
      .catch(error => {
          console.error("Error adding book:", error);
          alert("Failed to add the book.");
        });
  };

  return (
    <div className="popup-overlay">
      <div className="popup">
        <h2>Add a New Book</h2>
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
          <button type="submit">Add Book</button>
          <button type="button" onClick={onClose}>
            Cancel
          </button>
        </form>
      </div>
    </div>
  );
}

export default NewBook;
