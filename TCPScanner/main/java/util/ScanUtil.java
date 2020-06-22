package Util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *  Класс, который содержит методы для сканирования
 * */
public class ScanUtil
{
    private static  Logger logger = LogManager.getLogger(ScanUtil.class);

    /**
     * метод {@link ScanUtil#scanHost}  предназначен для сканирования удаленного хоста методом подключения
     * @param host IP адрес хоста, к которому будет производиться подключение;
     * @param port порт хоста, к которому будет производиться подключение;
     * @param timeout время, которое будет затрачено на подключение. Если время истекло и соединение не было установлено, то удаленный хост считается недоступным.
     *
     * @return true, если удаленный хост доступен или false, если удаленный хост недоступен
     * */
    public static boolean scanHost(String host, int port, int timeout)
    {
        StringBuilder info = new StringBuilder().append("host: ")
                .append(host)
                .append("\tport: ")
                .append(port)
                .append("\ttimeout: ")
                .append(timeout);
        Socket socket = new Socket();
        try
        {
            socket.connect(new InetSocketAddress(host, port), timeout);
        }
        catch (IOException e)
        {
            logger.error(info, e);
            return false;
        }
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            logger.error(info, e);
            return false;
        }

        logger.info(info.append(" connect successfully"));

        return true;
    }
}
