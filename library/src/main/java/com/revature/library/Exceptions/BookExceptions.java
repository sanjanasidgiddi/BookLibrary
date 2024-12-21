package com.revature.library.Exceptions;

public class BookExceptions {
    public static class TitleAndAuthorAlreadyExists extends Exception{}

    public static class NotFound extends Exception{}

    public static class IsHeld extends Exception{}

    public static class AlreadyReturned extends Exception{}
}
