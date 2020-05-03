package Exceptions;

import lombok.Getter;

public class ScannerAttributesException extends Exception{
    @Getter private Object value;

    public ScannerAttributesException(String message, Object invalidParam)
    {
        super(message);
        this.value = invalidParam;
    }
}
