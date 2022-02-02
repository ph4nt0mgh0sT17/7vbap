package cz.osu.gamingblog.exceptions;

public class WrongUserCredentialsException extends IllegalStateException {
    public WrongUserCredentialsException() {
        super("Wrong user credentials.");
    }
}
