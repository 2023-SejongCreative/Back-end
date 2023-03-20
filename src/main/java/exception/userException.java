package exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class userException extends RuntimeException{

    private errorCode errorCode;
}
