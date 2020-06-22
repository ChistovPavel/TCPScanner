package Exceptions;

import lombok.Getter;

/**
 * Класс исключение, предназначенный для обработки ошибок, возникших при работе с атрибутами класса {@link NetScanner.NetScanner}
 * */
public class ScannerAttributesException extends Exception{
    @Getter private Object value;

    /**
     * Конструктор класса
     * @param message сообщение об ошибке
     * @param invalidParam значение неверного атрибута
     * */
    public ScannerAttributesException(String message, Object invalidParam)
    {
        super(message);
        this.value = invalidParam;
    }
}
