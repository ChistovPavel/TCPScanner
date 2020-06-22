package net.scanner;

import util.ScanUtil;
import org.javatuples.Triplet;
import java.util.concurrent.Callable;

/**
 * Класс, предназначенный для выполнения в отдельном потоке. Выполняет задачу сканирования.
 * */

@Deprecated
public class ScanTask implements Callable<Triplet<String, Integer, Boolean>> {

    private String host;
    private Integer port;
    private Integer connectionTimeout;

    /**
     * Конструктор класса
     * @param inHost IP адрес хоста, к которому будет производиться подключение;
     * @param inPort порт хоста, к которому будет производиться подключение;
     * @param timeout время, которое будет затрачено на подключение. Если время истекло и соединение не было установлено, то удаленный хост считается недоступным.
     * */
    public ScanTask(String inHost, Integer inPort, Integer timeout)
    {
        this.host = inHost;
        this.port = inPort;
        this.connectionTimeout = timeout;
    }

    /**
     * @return true, если удаленный хост доступен или false, если удаленный хост недоступен
     * */
    public Triplet<String, Integer, Boolean> call() {
        boolean result = ScanUtil.scanHost(this.host, this.port, this.connectionTimeout);
        return new Triplet<String, Integer, Boolean>(host, port, result);
    }
}
