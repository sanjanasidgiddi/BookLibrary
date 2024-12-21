package com.revature.library.Exceptions;

public final class BookExceptions {
    private BookExceptions(){}

    public static final class TitleAndAuthorAlreadyExists extends Exception{}

    public static final class NotFound extends Exception{}

    public static final class IsHeld extends Exception{}

    public static final class AlreadyReturned extends Exception{}
}
