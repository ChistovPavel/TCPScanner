package NetScanner;

import Util.ScanUtil;
import org.javatuples.Triplet;
import java.util.concurrent.Callable;

@Deprecated
public class ScanTask implements Callable<Triplet<String, Integer, Boolean>> {

    private String host;
    private Integer port;
    private Integer connectionTimeout;

    public ScanTask(String inHost, Integer inPort, Integer timeout)
    {
        this.host = inHost;
        this.port = inPort;
        this.connectionTimeout = timeout;
    }

    public Triplet<String, Integer, Boolean> call() {
        boolean result = ScanUtil.scanHost(this.host, this.port, this.connectionTimeout);
        return new Triplet<String, Integer, Boolean>(host, port, result);
    }
}
