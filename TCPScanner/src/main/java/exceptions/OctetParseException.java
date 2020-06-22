package exceptions;

/**
 * Класс исключение, предназначенный для обработки ошибок, возникших при парсинге октетов IP адреса хоста
 * */
public class OctetParseException extends ParseInputException {

    /**
     * Конструктор класса
     * @param message сообщение об ошибке
     * @param unparsedData данные, которые не удалось пропарсить
     * */
    public OctetParseException(String message, String unparsedData)
    {
        super(message, unparsedData);
    }
}
