package zw.co.fasoft.exceptions;

public class AccountNotFullySetupException extends RuntimeException{

    public AccountNotFullySetupException(String message) {
        super(message);
    }
}
