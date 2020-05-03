package Exceptions;

public class PortParseException extends ParseInputException {
    public PortParseException(String message, String unparsedData) {
        super(message, unparsedData);
    }
}
