package NetScanner;

import Exceptions.ScannerAttributesException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class NetScannerTest extends Assert {

    @Test(expected = ScannerAttributesException.class)
    public void scan2TestNull() throws ScannerAttributesException {
        NetScanner netScanner = new NetScanner(null, null, null, null);
        netScanner.scan2();
    }

    @Test(expected = ScannerAttributesException.class)
    public void scanTestNull() throws ScannerAttributesException, ExecutionException, InterruptedException {
        NetScanner netScanner = new NetScanner(null, null, null, null);
        netScanner.scan();
    }

    @Test
    public void scan2TestWrongHost() throws ScannerAttributesException
    {
        List<ScanResult> expected = Arrays.asList(new ScanResult("",
                Arrays.asList(new ConnectionInfo(81, false))));

        ArrayList<String> hosts = new ArrayList<String>();
        hosts.add("");
        ArrayList<Integer> ports = new ArrayList<Integer>();
        ports.add(81);

        NetScanner netScanner = new NetScanner(hosts, ports, 5000, 10);
        ArrayList<ScanResult> list = netScanner.scan2();
        assertEquals(expected, list);
    }

    @Test
    public void scanTestWrongHost() throws ScannerAttributesException, ExecutionException, InterruptedException
    {
        TreeMap<String, TreeMap<Integer, Boolean>> expected = new TreeMap<String, TreeMap<Integer, Boolean>>();
        TreeMap<Integer, Boolean> info = new TreeMap<Integer, Boolean>();
        info.put(81, false);
        expected.put("", info);

        ArrayList<String> hosts = new ArrayList<String>();
        hosts.add("");
        ArrayList<Integer> ports = new ArrayList<Integer>();
        ports.add(81);

        NetScanner netScanner = new NetScanner(hosts, ports, 5000, 10);
        TreeMap<String, TreeMap<Integer, Boolean>> result = netScanner.scan();
        assertEquals(expected, result);
    }

    @Test
    public void scan2Test() throws ScannerAttributesException
    {
        List<ScanResult> expected = Arrays.asList(new ScanResult("google.com",
                Arrays.asList(new ConnectionInfo(80, true))));

        ArrayList<String> hosts = new ArrayList<String>();
        hosts.add("172.217.22.46");
        ArrayList<Integer> ports = new ArrayList<Integer>();
        ports.add(80);

        NetScanner netScanner = new NetScanner(hosts, ports, 5000, 10);
        ArrayList<ScanResult> list = netScanner.scan2();
        assertEquals(expected, list);
    }

    @Test
    public void scanTest() throws ScannerAttributesException, ExecutionException, InterruptedException
    {
        TreeMap<String, TreeMap<Integer, Boolean>> expected = new TreeMap<String, TreeMap<Integer, Boolean>>();
        TreeMap<Integer, Boolean> info = new TreeMap<Integer, Boolean>();
        info.put(80, true);
        expected.put("172.217.22.46", info);

        ArrayList<String> hosts = new ArrayList<String>();
        hosts.add("172.217.22.46");
        ArrayList<Integer> ports = new ArrayList<Integer>();
        ports.add(80);

        NetScanner netScanner = new NetScanner(hosts, ports, 5000, 10);
        TreeMap<String, TreeMap<Integer, Boolean>> result = netScanner.scan();
        assertEquals(expected, result);
    }
}
