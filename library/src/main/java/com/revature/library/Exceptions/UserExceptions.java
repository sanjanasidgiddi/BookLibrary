package com.revature.library.Exceptions;

public final class UserExceptions {
    private UserExceptions(){}

    public static final class UsernameInvalid extends Exception{}

    public static final class PasswordInvalid extends Exception{}

    public static final class EmailInvalid extends Exception{}

    public static final class NotFound extends Exception{}

    public static final class UsernameAlreadyTaken extends Exception{}

    public static class IsHoldingBook extends Exception{}
}
