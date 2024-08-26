package zw.co.fasoft.exceptions;

public class AccountCannotBeDeletedException extends RuntimeException{

    public AccountCannotBeDeletedException(String message) {
        super(message);
    }
}
