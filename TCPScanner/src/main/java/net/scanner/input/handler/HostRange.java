package net.scanner.input.handler;

import exceptions.HostRangeParseException;
import exceptions.OctetParseException;
import net.scanner.NetScanner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для хранения информации об IP адресе хоста. Данный класс используется для парсинга входных параметров командной строки
 * */
public class HostRange
{
    private ArrayList<Octet> value;
    private Integer allCombinationsNumber;
    private static Logger logger = LogManager.getLogger(NetScanner.class);

    /**
     * Конструктор класса
     * @param host строковое представление IP адреса хоста
     * @exception HostRangeParseException throws в случае нарушения формата записи IP адреса или диапозона IP адресов
     * @exception OctetParseException throws в случае нарушения формата записи октета IP адреса
     * */
    public HostRange(String host) throws HostRangeParseException, OctetParseException
    {
        String[] octets = host.split("\\.");

        if (octets.length != 4)
        {
            HostRangeParseException e = new HostRangeParseException("IP doesn't have 4 octets", host);
            logger.error(e);
            throw e;
        }

        this.allCombinationsNumber = 1;
        this.value = new ArrayList<Octet>();

        for (String octet : octets)
        {
            Octet tmp = new Octet(octet);
            this.value.add(tmp);
            this.allCombinationsNumber *= tmp.getSize();
        }
    }

    /**
     * Получение всех возможных комбинация диапазона IP адресов
     * @return список {@link List} строковых представлений диапазона IP адресов
     * */
    public List<String> getAllHosts()
    {
        ArrayList<String> hosts = new ArrayList<String>();

        StringBuilder currentHost = new StringBuilder();

        for (int i = 0; i < this.allCombinationsNumber; i++)
        {
            int tmpSize = this.allCombinationsNumber;
            for (Octet octet : this.value)
            {
                tmpSize/=octet.getSize();
                currentHost.append(octet.getOctetValue(i, tmpSize));
                if (!checkLastOctet(currentHost))currentHost.append(".");
            }
            hosts.add(currentHost.toString());
            currentHost.setLength(0);
        }

        return hosts;
    }

    private boolean checkLastOctet(StringBuilder host)
    {
        int dotCount = 0, lastIndex = 0;
        while ((lastIndex = host.indexOf(".", lastIndex)+1)>0) dotCount++;
        if (dotCount == 3) return true;
        return false;
    }
}
