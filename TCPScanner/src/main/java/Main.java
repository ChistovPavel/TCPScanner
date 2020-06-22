import exceptions.*;
import net.scanner.input.handler.NetScannerInputParser;
import net.scanner.NetScanner;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;

public class Main
{
    static
    {
        System.setProperty("LogFileName", new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date()));
        System.setProperty("LogDir", "log");
        System.setProperty("LogDirParse", "Parser");
        System.setProperty("LogDirScanner", "NetScanner");
    }

    public static void main(String[] args)
    {
        NetScannerInputParser parser = null;
        try {
            parser = new NetScannerInputParser(args);
        }
        catch (ParseInputException e) {
            System.err.println(new StringBuilder().append(e.getMessage()).append(": ").append(e.getValue()));
            return;
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return;
        }

        ArrayList<String> hosts = parser.getHosts();
        ArrayList<Integer> ports = parser.getPorts();
        Integer threads = parser.getThreads();
        Integer timeout = parser.getTimeout();
        String path = parser.getPath();

        NetScanner scanner = new NetScanner(hosts, ports, timeout, threads);

        try
        {
            if (path == null)
            {
                System.out.println(scanner.scanToJsonStr());
            }
            else
            {
                scanner.scanToJsonFile(path);
                System.out.println(new StringBuilder("Scanning result you can find on ")
                        .append(path)
                        .append(" file"));
            }
        }
        catch (ScannerAttributesException e)
        {
            System.err.println(e.getMessage());
            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
    }
}
