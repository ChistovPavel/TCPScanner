package Util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ScanUtil
{
    private static  Logger logger = LogManager.getLogger(ScanUtil.class);

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
