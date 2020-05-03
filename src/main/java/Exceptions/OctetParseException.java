package Exceptions;

public class OctetParseException extends ParseInputException {

    public OctetParseException(String message, String unparsedData)
    {
        super(message, unparsedData);
    }
}
