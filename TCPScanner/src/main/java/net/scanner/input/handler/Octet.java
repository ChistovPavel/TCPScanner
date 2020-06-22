package net.scanner.input.handler;

import exceptions.OctetParseException;
import net.scanner.NetScanner;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Класс предназначен для хранения информации об октетах IP адреса. Данный класс используется для парсинга входных параметров командной строки
 * */
public class Octet
{
    @Getter @Setter private ArrayList<Integer> value;
    private static Logger logger = LogManager.getLogger(NetScanner.class);

    /**
     * Конструктор класса
     * @param octet строковое представление октета (должно быть в интервале от 0 жо 255).
     * @exception OctetParseException throws в случае нарушения формата записи осктета или диапозона октетов
     * */
    public Octet(String octet) throws OctetParseException {
        this.value = new ArrayList<Integer>();
        if (octet.contains("-"))
        {
            String[] minMax = octet.split("-");

            if (minMax.length != 2)
            {
                OctetParseException ex = new OctetParseException("Octet range has more parts", octet);
                logger.error(ex);
                throw ex;
            }

            Integer min = null;
            try {min = Integer.valueOf(minMax[0]);}
            catch (NumberFormatException e){
                OctetParseException ex = new OctetParseException(e.getMessage(), octet);
                logger.error(ex);
                throw ex;
            }
            Integer max = null;
            try {max = Integer.valueOf(minMax[1]);}
            catch (NumberFormatException e){
                OctetParseException ex = new OctetParseException(e.getMessage(), octet);
                logger.error(ex);
                throw ex;
            }

            if ((min < 0 || min > 255) || (max < 0 || max > 255) || (min > max))
            {
                OctetParseException ex = new OctetParseException("Incorrect octet number", octet);
                logger.error(ex);
                throw ex;
            }

            for (int i = min; i <= max; i++)
            {
                this.value.add(i);
            }
        }
        else
        {
            Integer octetNum = null;
            try {octetNum = Integer.valueOf(octet);}
            catch (NumberFormatException e){
                OctetParseException ex = new OctetParseException("Number format exception", octet);
                logger.error(ex);
                throw ex;
            }
            if (octetNum < 0 || octetNum > 255)
            {
                OctetParseException ex = new OctetParseException("Incorrect octet number", octet);
                logger.error(ex);
                throw ex;
            }

            this.value.add(octetNum);
        }
    }

    /**
     * Возвращает длину диапозона отктетов
     * @return {@link Integer} длина диапозона октетов
     * */
    public Integer getSize(){return this.value.size();}

    /**
     * Возвращает значение октета
     * @param index параметр, используемый для получения значения. Алгоритм получения описан в {@link HostRange#getAllHosts()};
     * @param inSize параметр, используемый для получения значения. Алгоритм получения описан в {@link HostRange#getAllHosts()}.
     * @return {@link Integer} значения октета по заданным параметрам
     * */
    public Integer getOctetValue(Integer index, Integer inSize)
    {
        int newIndex = 0;

        if (inSize == 1) newIndex = index%(this.value.size());
        else newIndex = index/inSize;

        return (this.value.get(newIndex));
    }
}
