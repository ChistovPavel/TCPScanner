package util;

import net.scanner.ConnectionInfo;
import net.scanner.ScanResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class JsonUtilTest extends Assert {

    @Test
    public void objToJsonStrTestNull()
    {
        String result = JsonUtil.objToJsonStr(null);
        assertNotNull(result);
    }

    @Test
    public void objToJsonStrTest()
    {
        ScanResult scanResult = new ScanResult("192.168.0.1", Arrays.asList(new ConnectionInfo(80, true)));
        String result = JsonUtil.objToJsonStr(Arrays.asList(scanResult));
        assertEquals(result, new StringBuilder("[\n")
                .append("  {\n")
                .append("    \"host\": \"192.168.0.1\",\n")
                .append("    \"connectionInfo\": [\n")
                .append("      {\n")
                .append("        \"port\": 80,\n")
                .append("        \"status\": true\n")
                .append("      }\n")
                .append("    ]\n")
                .append("  }\n")
                .append("]").toString());
    }
}
