package NetScanner.InputHandlerTest;

import Exceptions.ParseInputException;
import NetScanner.InputHandler.NetScannerInputParser;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class NetScannerInputParserTest extends Assert
{
    private static final String[] in1 = {"-h", "192.168.0.1"};
    private static final String[] in2 = {"-p", "120-150"};
    private static final String[] in3 = {"-h", "192.168.0.1", "-p", "120-150"};
    private static final String[] in4 = {"-h", "192.168.0.1", "-p", "120-150", "-P", "file.json", "-t", "10", "-T", "1000"};

    @Test (expected = MissingOptionException.class)
    public void NetScannerInputParseNullTest() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(null);
    }

    @Test (expected = ParseException.class)
    public void NetScannerInputParseWrongInTest1() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(in1);
    }

    @Test (expected = ParseException.class)
    public void NetScannerInputParseWrongInTest2() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(in2);
    }

    @Test
    public void NetScannerInputParseTest1() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(in3);
        assertNotNull(netScannerInputParser);
    }

    @Test
    public void NetScannerInputParseTest2() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(in4);
        assertNotNull(netScannerInputParser);
    }

    @Test
    public void getTimeoutNullTest() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(in3);
        assertNull(netScannerInputParser.getTimeout());
    }

    @Test
    public void getTimeoutNotNullTest() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(in4);
        assertNotNull(netScannerInputParser.getTimeout());
    }

    @Test
    public void getThreadsNullTest() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(in3);
        assertNull(netScannerInputParser.getThreads());
    }

    @Test
    public void getThreadsNotNullTest() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(in4);
        assertNotNull(netScannerInputParser.getThreads());
    }

    @Test
    public void getPathNullTest() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(in3);
        assertNull(netScannerInputParser.getPath());
    }

    @Test
    public void getPathNotNullTest() throws ParseException, ParseInputException {
        NetScannerInputParser netScannerInputParser = new NetScannerInputParser(in4);
        assertNotNull(netScannerInputParser.getPath());
    }
}
