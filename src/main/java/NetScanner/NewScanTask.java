package NetScanner;

import Util.ScanUtil;

import java.util.Arrays;
import java.util.List;

public class NewScanTask implements Runnable{

    private String host;
    private Integer port;
    private Integer timeout;
    private List<ScanResult> result;

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
