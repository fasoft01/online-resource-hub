package zw.co.fasoft.exceptions;

import lombok.Data;

/**
 * @author Farai Matsika
 * @date 31/May/2024
 */

@Data
public class Error {

    private final int status;
    private final String error;

    private Error(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public static Error of(int status, String error) {
        return new Error(status, error);
    }

    @Override
    public String toString() {
        return "Error{" +
                "status=" + status +
                ", error='" + error + '\'' +
                '}';
    }
}
