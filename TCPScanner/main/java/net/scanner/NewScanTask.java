package NetScanner;

import Util.ScanUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Класс, предназначенный для выполнения в отдельном потоке. Выполняет задачу сканирования.
 * */

public class NewScanTask implements Runnable{

    private String host;
    private Integer port;
    private Integer timeout;
    private List<ScanResult> result;

    /**
     * Конструктор класса
     * @param inHost IP адрес хоста, к которому будет производиться подключение;
     * @param inPort порт хоста, к которому будет производиться подключение;
     * @param inTimeout время, которое будет затрачено на подключение. Если время истекло и соединение не было установлено, то удаленный хост считается недоступным;
     * @param outResult {@link List}, в который будет записан результат сканирования.
     * */
    public NewScanTask(String inHost, Integer inPort, Integer inTimeout, List<ScanResult> outResult)
    {
        this.host = inHost;
        this.port = inPort;
        this.timeout = inTimeout;
        this.result = outResult;
    }

    public synchronized void run()
    {
        ConnectionInfo info = new ConnectionInfo(this.port, ScanUtil.scanHost(this.host, this.port, this.timeout));

            for (ScanResult scanResult : result)
            {
                if (scanResult.getHost().equals(host))
                {
                    scanResult.getPorts().add(info);
                    return;
                }
            }
            result.add(new ScanResult(host, Arrays.asList(info)));
    }
}
