package Exceptions;

import lombok.Getter;

/**
 * Класс исключение, предназначенный для обработки ошибок, возникших при парсинге входных данных
 * */
public class ParseInputException extends Exception {

    @Getter private String value;

    /**
     * Конструктор класса
     * @param message сообщение об ошибке
     * @param unparsedData данные, которые не удалось пропарсить
     * */
    public ParseInputException(String message, String unparsedData)
    {
        super(message);
        this.value = unparsedData;
    }
}
