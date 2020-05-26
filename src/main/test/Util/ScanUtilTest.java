package Util;

import org.junit.Assert;
import org.junit.Test;

public class ScanUtilTest extends Assert {

    @Test
    public void scanHostTestTrue()
    {
        Boolean result = ScanUtil.scanHost("172.217.22.46", 80, 5000);
        assertTrue(result);
    }

    @Test
    public void scanHostTestFalse()
    {
        Boolean result = ScanUtil.scanHost("1.1.1.1", 1, 5000);
        assertFalse(result);
    }
}
