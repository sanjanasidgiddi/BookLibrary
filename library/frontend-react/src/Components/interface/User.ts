// Fetch books from backend
export interface User{
    username: string,
    password: string,
    firstName: string,
    lastName: string,
    email: string,
    phoneNumber: number,
    dob: Date,
    role: "USER" | "ADMIN"
}