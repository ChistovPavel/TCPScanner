package exceptions;

/**
 * Класс исключение, предназначенный для обработки ошибок, возникших при парсинге IP адресов хостов
 * */
public class HostRangeParseException extends ParseInputException {

    /**
     * Конструктор класса
     * @param message сообщение об ошибке
     * @param unparsedData данные, которые не удалось пропарсить
     * */
    public HostRangeParseException(String message, String unparsedData)
    {
        super(message, unparsedData);
    }
}
