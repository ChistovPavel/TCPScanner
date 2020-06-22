package net.scanner.input.handler;

import exceptions.ParseInputException;
import exceptions.PortParseException;
import lombok.Getter;
import org.apache.commons.cli.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс предназначен для прасинга параметров командной строки
 **/
public class NetScannerInputParser
{
    private static Logger logger = LogManager.getLogger(NetScannerInputParser.class);

    public final static Option hostOption = Option.builder("h")
                .longOpt("host")
                .hasArg(true)
                .desc("hosts")
                .required(true)
                .numberOfArgs(Option.UNLIMITED_VALUES)
                .build();
    public final static Option portOption = Option.builder("p")
                .longOpt("port")
                .hasArg(true)
                .desc("ports")
                .required(true)
                .numberOfArgs(Option.UNLIMITED_VALUES)
                .build();
    public final static Option threadOption = Option.builder("t")
                .longOpt("thread")
                .hasArg(true)
                .desc("number of threads")
                .required(false)
                .numberOfArgs(1)
                .build();
    public final static Option timeoutOption = Option.builder("T")
                .longOpt("timeout")
                .hasArg(true)
                .desc("connection timeout")
                .required(false)
                .numberOfArgs(1)
                .build();
    public final static Option pathOption = Option.builder("P")
                .longOpt("path")
                .hasArg(true)
                .desc("Output file path")
                .required(false)
                .numberOfArgs(1)
                .build();

    @Deprecated public static final String HostFlag = "-h";
    @Deprecated public static final String PortFlag = "-p";
    @Deprecated public static final String ThreadFlag = "-t";
    @Deprecated public static final String TimeFlag = "-T";
    @Deprecated public static final String PathFlag = "-P";

    private CommandLine commandLine;
    @Getter private ArrayList<String> hosts;
    @Getter private ArrayList<Integer> ports;

    /**
     * Конструктор класса
     * @param args аргументы командной строки.
     * @exception ParseInputException throws в случае ошибки парсинга входных параметров (отсутсвие обязательных параметров -p или -h).
     * */
    public NetScannerInputParser(String[] args) throws ParseInputException, ParseException
    {
        Options options = new Options();
        options.addOption(hostOption);
        options.addOption(portOption);
        options.addOption(threadOption);
        options.addOption(timeoutOption);
        options.addOption(pathOption);

        this.commandLine = new DefaultParser().parse(options, args);

        this.hosts = new ArrayList<String>();
        this.ports = new ArrayList<Integer>();

        logger.info("Start parse hosts");
        this.parseHosts();
        logger.info("Stop parse hosts");
        logger.info("Start parse ports");
        this.parsePorts();
        logger.info("Stop parse ports");
    }

    /**
     * Парсинг IP адресов хостов
     * @exception ParseInputException throws в случае нарушения формата записи диапазона хостов
     * */
    private void parseHosts() throws ParseInputException
    {
        String[] hostsArray = this.commandLine.getOptionValues(hostOption.getOpt());

        for (String host : hostsArray)
        {
            logger.info(new StringBuilder("current parsing host: ").append(host));
            this.hosts.addAll(new HostRange(host).getAllHosts());
            logger.info("parse successfully");
        }
    }


    /**
     * Парсинг портов
     * @exception PortParseException throws в случае нарушения формата записи диапазона портов
     * */
    private void parsePorts() throws PortParseException
    {
        String[] portsArray = this.commandLine.getOptionValues(portOption.getOpt());

        for (String port : portsArray)
        {
            logger.info(new StringBuilder("current parsing port: ").append(port));
            this.ports.addAll(getAllPorts(port));
            logger.info("parse successfully");
        }
    }

    /**
     * Получение всех портов диапазона
     * @return список {@link ArrayList} всех значений портов диапазона
     * @exception PortParseException throws в случае нарушения формата записи диапазона портов
     * */
    private ArrayList<Integer> getAllPorts(String port) throws PortParseException {
        ArrayList<Integer> ports = new ArrayList<Integer>();
        if (port.contains("-"))
        {
            String[] minMax = port.split("-");

            if (minMax.length != 2)
            {
                PortParseException ex = new PortParseException("Port range has more parts", port);
                logger.error(ex);
                throw ex;
            }

            Integer min = null;
            try {min = Integer.valueOf(minMax[0]);}
            catch (NumberFormatException e){
                PortParseException ex = new PortParseException(e.getMessage(), port);
                logger.error(ex);
                throw ex;
            }
            Integer max = null;
            try {max = Integer.valueOf(minMax[1]);}
            catch (NumberFormatException e){
                PortParseException ex = new PortParseException(e.getMessage(), port);
                logger.error(ex);
                throw ex;
            }

            if ((min < 0 || min > 65535) || (max < 0 || max > 65535) || (min > max))
            {
                PortParseException ex = new PortParseException("Incorrect port number", port);
                logger.error(ex);
                throw ex;
            }

            for (int i = min; i <= max; i++)
            {
                ports.add(i);
            }
        }
        else
        {
            Integer portNum = null;
            try {portNum = Integer.valueOf(port);}
            catch (NumberFormatException e){
                PortParseException ex = new PortParseException(e.getMessage(), port);
                logger.error(ex);
                throw ex;
            }

            if (portNum < 0 || portNum > 65535)
            {
                PortParseException ex = new PortParseException("Incorrect port number", port);
                logger.error(ex);
                throw ex;
            }

            ports.add(portNum);
        }
        return ports;
    }
    /**
     * Получение параметра времени, которое будет отведено на одно подключение
     * @return {@link Integer} время в миллисекундах или null, если данный параметр не указан
     * */
    public Integer getTimeout()
    {
        return this.getNumberValue(timeoutOption);
    }

    /**
     * Получение параметра количества потоков
     * @return {@link Integer} количество потоков или null, если данный параметр не указан
     * */
    public Integer getThreads()
    {
        return this.getNumberValue(threadOption);
    }

    /**
     * Получение целочисленного значения из аргументов командной строки по ключу
     * @param option {@link Option} задает ключ, по которому будут извлекаться данные.
     * @return соответствующее целочисленное значение
     * */
    private Integer getNumberValue(Option option)
    {
        if (this.commandLine.hasOption(option.getOpt()))
        {
            Integer returnValue;
            try
            {
                returnValue =  Integer.valueOf(this.commandLine.getOptionValue(option.getOpt()));
            }
            catch (NumberFormatException e)
            {
                returnValue = null;
            }
            return returnValue;
        }
        return null;
    }

    /**
     * Получение пути выходного файла из аргументов командной строки по ключу
     * @return {@link String} путь к файлу или null, если данный параметр не указан
     * */
    public String getPath()
    {
        if (this.commandLine.hasOption(pathOption.getOpt()))
        {
            return this.commandLine.getOptionValue(pathOption.getOpt());
        }
        return null;
    }

    /**
     * Парсинг аргументов командной строки.
     * @param data аргументы командной строки, представленные в виде одной строки.
     * @return список {@link ArrayList}, который содержит все переданные IP адреса хостов
     * */
    @Deprecated
    public static ArrayList<String> parseHosts(String data)
    {
        String tmp = RegEx(HostFlag + ".*?(" + PortFlag + "|"+
                                                       ThreadFlag + "|" +
                                                       TimeFlag + "|" +
                                                       PathFlag + "|" +
                                                       "$)", data);

        if (tmp == null) return null;

        ArrayList<String> hosts = new ArrayList<String>();

        try {
            getAllHosts(tmp.replaceAll("[^0-9. -]", "")
                           .replaceAll("^(-*? *?) | ( *?-*?$)", "")
                           .split("\\s"), hosts);
        } catch (ParseInputException e) {
            return null;
        }
        return hosts;
    }

    /**
     * Получение всех возможных комбинаций IP адресов диапазона
     * @param hosts массив строковых представлений IP адресов;
     * @param array_hosts список {@link List}, в который будут помещаться все возможные комбинации диапазона IP дресов.
     * @exception ParseInputException throws в случае нарушения формата записи диапазона хостов
     * */
    @Deprecated
    private static void getAllHosts(String[] hosts, List<String> array_hosts) throws ParseInputException
    {
        if (hosts.length <= 0) throw new ParseInputException("Input list of IP is null", null);

        ArrayList<String>[] components = new ArrayList[]{
                                         new ArrayList<String>(),
                                         new ArrayList<String>(),
                                         new ArrayList<String>(),
                                         new ArrayList<String>()};

        String[] tmp = null, octets = null;
        Integer index = 0;

        for (String host : hosts)
        {
            if (host.contains("-"))
            {
                octets = host.split("\\.");
                if (octets.length != 4) throw new ParseInputException("IP doesn't contain 4 octets", host);

                index = 0;

                for (int i = 0; i < 4; i++) components[i].removeAll(components[i]);

                for (String octet : octets)
                {
                    if (octet.contains("-"))
                    {
                        tmp = octet.split("-");
                        if (tmp.length != 2) throw new ParseInputException("Octet range parse exception", octet);
                        for (Integer i = Integer.valueOf(tmp[0]); i < Integer.valueOf(tmp[1])+1; i++)
                        {
                            components[index].add(i.toString());
                        }
                    }
                    else
                    {
                        components[index].add(octet);
                    }
                    index++;
                }
                for (String octet_1 : components[0])
                {
                    for (String octet_2 : components[1])
                    {
                        for (String octet_3 : components[2])
                        {
                            for (String octet_4 : components[3])
                            {
                                array_hosts.add(octet_1+"."+octet_2+"."+octet_3+"."+octet_4);
                            }
                        }
                    }
                }
            }
            else
            {
                array_hosts.add(host);
            }
        }
    }

    /**
     * Парсинг аргументов командной строки.
     * @param data аргументы командной строки, представленные в виде одной строки.
     * @return список {@link ArrayList}, который содержит все переданные порты
     * */
    @Deprecated
    public static ArrayList<Integer> parsePorts(String data)
    {
        ArrayList<Integer> ports = new ArrayList<Integer>();

        String tmp = RegEx(PortFlag + ".*?(" + HostFlag + "|"+
                                                       ThreadFlag + "|" +
                                                       TimeFlag + "|" +
                                                       PathFlag + "|" +
                                                       "$)", data);

        if (tmp == null) return null;

        try {
            getAllPorts(tmp.replaceAll("[^0-9. -]", "")
                           .replaceAll("^(-*? *?) | ( *?-*?$)", "")
                           .split("\\s"), ports);
        } catch (ParseInputException e) {
            return null;
        }

        return ports;
    }

    /**
     * Получение всех возможных комбинаций портов диапазона
     * @param ports массив строковых представлений диапазона портов;
     * @param array_ports список {@link List}, в который будут помещаться все возможные комбинации портов диапазона.
     * @exception ParseInputException throws в случае нарушения формата записи диапазона портов
     * */
    @Deprecated
    private static void getAllPorts(String[] ports, ArrayList<Integer> array_ports) throws ParseInputException {

        if (ports.length <= 0) throw new ParseInputException("Input list of ports is null", null);

        for (String port : ports)
        {
            if (port.contains("-"))
            {
                String[] tmp = port.split("-");
                if (tmp.length != 2) throw new ParseInputException("Port range doesn't contain 2 numbers", port);

                for (int i = Integer.valueOf(tmp[0]); i < Integer.valueOf(tmp[1])+1; i++)
                {
                    array_ports.add(i);
                }
            }
            else
            {
                array_ports.add(Integer.valueOf(port));
            }
        }
    }

    /**
     * Получение параметра количества потоков
     * @param data аргументы командной строки, представленные в виде одной строки.
     * @return {@link Integer} количество потоков
     * */
    @Deprecated
    public static Integer getThreads(String data)
    {
        return getSingleNumber(data, ThreadFlag + ".*?(" + PortFlag + "|"+
                                                                   TimeFlag + "|" +
                                                                   HostFlag + "|" +
                                                                   PathFlag + "|" +
                                                                    "$)");
    }

    /**
     * Получение параметра времени, которое будет отведено на одно подключение
     * @param data аргументы командной строки, представленные в виде одной строки.
     * @return {@link Integer} время в миллисекундах
     * */
    @Deprecated
    public static Integer getTime(String data)
    {
        return getSingleNumber(data, TimeFlag + ".*?(" + PortFlag + "|"+
                                                                 ThreadFlag + "|" +
                                                                 HostFlag + "|" +
                                                                 PathFlag + "|" +
                                                                 "$)");
    }

    /**
     * Получение целочисленного значения из аргументов командной строки по ключу
     * @param data аргументы командной строки, представленные в виде одной строки;
     * @param regex регулярное выражение, которое определяет параметры получения данных из аргументов командной строки.
     * @return соответствующее целочисленное значение
     * */
    @Deprecated
    private static Integer getSingleNumber(String data, String regex)
    {
        String tmp = RegEx(regex, data).replaceAll("[^0-9]", "");
        if (tmp == null) return null;
        try
        {
            return Integer.valueOf(tmp);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Получение пути выходного файла из аргументов командной строки по ключу
     *  @param data аргументы командной строки, представленные в виде одной строки.
     * @return {@link String} путь к файлу
     * */
    @Deprecated
    public static String getPath(String data)
    {
        String tmp = RegEx(PathFlag + ".*?(" + HostFlag + "|"+
                                                       ThreadFlag + "|" +
                                                       TimeFlag + "|" +
                                                       PortFlag + "|" +
                                                       "$)", data);
        if (tmp == null) return null;
        return tmp.replaceAll("(^.*?\"|\".*?$)", "");
    }

    /**
     * Метод выполнения заданного регулярного выражения
     * @param regex резулярное выражение;
     * @param data данные, к которым регулярное выражение будет применено.
     * @return {@link String} результат применения регулярного выражения
     * */
    @Deprecated
    private static String RegEx(String regex, String data)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        matcher.find();

        try
        {
            return data.substring(matcher.start(), matcher.end());
        }
        catch (IllegalStateException e)
        {
            return null;
        }
    }
}
