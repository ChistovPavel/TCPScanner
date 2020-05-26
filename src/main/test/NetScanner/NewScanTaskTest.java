package NetScanner;

import org.javatuples.Triplet;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class NewScanTaskTest extends Assert {

    @Test(expected = NullPointerException.class)
    public void testNull()
    {
        NewScanTask task = new NewScanTask(null, null, null, null);
        task.run();
    }

    @Test
    public void testWrongHost()
    {
        ArrayList<ScanResult> list = new ArrayList<ScanResult>();
        NewScanTask task = new NewScanTask("", 0, 5000, list);
        task.run();

        ScanResult scanResult = new ScanResult("", Arrays.asList(new ConnectionInfo(0, false)));

        assertEquals(Arrays.asList(scanResult), list);
    }

    @Test
    public void testNotNull()
    {
        ArrayList<ScanResult> list = new ArrayList<ScanResult>();
        NewScanTask task = new NewScanTask("172.217.22.46", 80, 5000, list);
        task.run();

        ScanResult scanResult = new ScanResult("172.217.22.46", Arrays.asList(new ConnectionInfo(80, true)));

        assertEquals(Arrays.asList(scanResult), list);
    }


}
