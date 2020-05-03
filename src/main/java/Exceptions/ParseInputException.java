package Exceptions;

import lombok.Getter;

public class ParseInputException extends Exception {

    @Getter private String value;

    public ParseInputException(String message, String unparsedData)
    {
        super(message);
        this.value = unparsedData;
    }
}
