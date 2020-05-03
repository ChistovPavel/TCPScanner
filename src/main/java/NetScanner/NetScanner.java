package NetScanner;

import Exceptions.ScannerAttributesException;
import Util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.javatuples.Triplet;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class NetScanner {

    private static Logger logger = LogManager.getLogger(NetScanner.class);

    public static final int defaultTimeout = 5000;
    public static final int defaultThreadsCount = 1;

    @Getter @Setter private ArrayList<String> hosts;
    @Getter @Setter private ArrayList<Integer> ports;
    @Getter @Setter private Integer connectionTimeout;
    @Getter @Setter private Integer threads;

    public NetScanner(ArrayList<String> inHosts, ArrayList<Integer> inPorts, Integer timeout, Integer threadsCount)
    {
        this.hosts = inHosts;
        this.ports = inPorts;
        if (timeout == null) this.connectionTimeout = defaultTimeout;
        else this.connectionTimeout = timeout;
        if (threadsCount == null) this.threads = defaultThreadsCount;
        else this.threads = threadsCount;
    }

    public ArrayList<ScanResult> scan2() throws ScannerAttributesException
    {
        if (this.threads <= 0) throw new ScannerAttributesException("Number of threads is less that 1", this.threads);
        else if (this.connectionTimeout <= 0) throw new ScannerAttributesException("Negative sign timeout", this.connectionTimeout);
        else if (this.hosts == null) throw new ScannerAttributesException("IP address table is null", null);
        else if (this.ports == null) throw new ScannerAttributesException("Port table is null", null);

        ArrayList<NewScanTask> tasks = new ArrayList<NewScanTask>();
        ArrayList<ScanResult> result = new ArrayList<ScanResult>();

        for (String host : this.hosts)
        {
            for (Integer port : this.ports)
            {
                tasks.add(new NewScanTask(host, port, this.connectionTimeout, result));
            }
        }

        Collections.shuffle(tasks);

        logger.info(new StringBuilder().append("Start scanning\tThreads amount: ")
                .append(this.threads)
                .append("\tTargets amount: ")
                .append(tasks.size()));

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.threads);

        for (NewScanTask task : tasks)
        {
            executor.execute(task);
        }

        executor.shutdown();

        while(!executor.isTerminated()) {
            System.out.println(new StringBuilder()
                    .append("Complete task amount: ")
                    .append(executor.getTaskCount())
                    .append("/")
                    .append(executor.getCompletedTaskCount()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(new StringBuilder()
                .append("Complete task amount: ")
                .append(executor.getTaskCount())
                .append("/")
                .append(executor.getCompletedTaskCount()));

        logger.info("Stop scanning");

        return result;
    }

    public String scanToJsonStr() throws ScannerAttributesException {
        return JsonUtil.objToJsonStr(this.scan2());
    }

    public void scanToJsonFile(String resultPath) throws ScannerAttributesException, IOException {
        String result = this.scanToJsonStr();

        FileWriter fileWriter = new FileWriter(resultPath);
        fileWriter.write(result);
        fileWriter.close();
    }

    @Deprecated
    public TreeMap<String, TreeMap<Integer, Boolean>> scan() throws InterruptedException,
                                                                    ScannerAttributesException,
                                                                    ExecutionException
    {
        if (this.threads <= 0) throw new ScannerAttributesException("Number of threads is less that 1", this.threads);
        else if (this.connectionTimeout <= 0) throw new ScannerAttributesException("Negative sign timeout", this.connectionTimeout);
        else if (this.hosts == null) throw new ScannerAttributesException("IP address table is null", null);
        else if (this.ports == null) throw new ScannerAttributesException("Port table is null", null);

        TreeMap<String, TreeMap<Integer, Boolean>> items = generateTable();
        ArrayList<ScanTask> tasks = new ArrayList<ScanTask>();

        for (String host : items.keySet())
        {
            for (Integer port : items.get(host).keySet())
            {
                tasks.add(new ScanTask(host, port, this.connectionTimeout));
            }
        }

        Collections.shuffle(tasks);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.threads);

        List<Future<Triplet<String, Integer, Boolean>>> futures = executor.invokeAll(tasks);

        for (Future<Triplet<String, Integer, Boolean>> future : futures)
        {
            items.get(future.get().getValue0()).put(future.get().getValue1(), future.get().getValue2());
        }

        executor.shutdown();

        return items;
    }

    @Deprecated
    private TreeMap<String, TreeMap<Integer, Boolean>> generateTable()
    {
        TreeMap<String, TreeMap<Integer, Boolean>> items = new TreeMap<String, TreeMap<Integer, Boolean>>();
        for(String host : this.hosts)
        {
            TreeMap<Integer, Boolean> tmp = new TreeMap<Integer, Boolean>();

            for (Integer port: this.ports)
            {
                tmp.put(port, false);
            }
            items.put(host, tmp);
        }
        return items;
    }
}
