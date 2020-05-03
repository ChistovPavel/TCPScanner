package Exceptions;

public class HostRangeParseException extends ParseInputException {

    public HostRangeParseException(String message, String unparsedData)
    {
        super(message, unparsedData);
    }
}
