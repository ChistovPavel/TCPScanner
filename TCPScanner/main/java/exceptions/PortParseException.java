package Exceptions;

/**
 * Класс исключение, предназначенный для обработки ошибок, возникших при парсинге портов
 * */
public class PortParseException extends ParseInputException {

    /**
     * Конструктор класса
     * @param message сообщение об ошибке
     * @param unparsedData данные, которые не удалось пропарсить
     * */
    public PortParseException(String message, String unparsedData) {
        super(message, unparsedData);
    }
}
