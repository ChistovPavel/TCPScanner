package net.scanner.InputHandlerTest;

import exceptions.HostRangeParseException;
import exceptions.OctetParseException;
import net.scanner.input.handler.HostRange;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class HostRangeTest extends Assert
{
    @Test(expected = HostRangeParseException.class)
    public void hostRangeWrongTest1() throws HostRangeParseException, OctetParseException {
        HostRange hostRange = new HostRange("172.217.22.46.1");
    }

    @Test(expected = NullPointerException.class)
    public void hostRangeNullTest() throws HostRangeParseException, OctetParseException {
        HostRange hostRange = new HostRange(null);
    }

    @Test
    public void hostRangeTest() throws HostRangeParseException, OctetParseException {
        HostRange hostRange = new HostRange("172.217.22.46");
        assertNotNull(hostRange);
    }

    @Test
    public void getAllHostsTest() throws HostRangeParseException, OctetParseException {
        HostRange hostRange = new HostRange("172.217.22.150-155");
        List<String> list = hostRange.getAllHosts();
        List<String> expected = Arrays.asList("172.217.22.150",
                "172.217.22.151",
                "172.217.22.152",
                "172.217.22.153",
                "172.217.22.154",
                "172.217.22.155");
        assertEquals(expected, list);
    }
}
