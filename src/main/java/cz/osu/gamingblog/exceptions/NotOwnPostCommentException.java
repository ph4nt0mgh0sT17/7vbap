package cz.osu.gamingblog.exceptions;

public class NotOwnPostCommentException extends IllegalStateException {
    public NotOwnPostCommentException() {
        super("The psot comment is not own.");
    }
}
