package NetScanner.InputHandlerTest;

import Exceptions.OctetParseException;
import NetScanner.InputHandler.Octet;
import org.junit.Assert;
import org.junit.Test;

public class OctetTest extends Assert {

    @Test(expected = OctetParseException.class)
    public void octetWrongInTest1() throws OctetParseException {
        Octet octet = new Octet("ABC");
    }

    @Test(expected = OctetParseException.class)
    public void octetWrongInTest2() throws OctetParseException {
        Octet octet = new Octet("12-12-12");
    }

    @Test(expected = OctetParseException.class)
    public void octetWrongInTest3() throws OctetParseException {
        Octet octet = new Octet("12-ab");
    }

    @Test(expected = OctetParseException.class)
    public void octetWrongInTest4() throws OctetParseException {
        Octet octet = new Octet("257");
    }

    @Test(expected = OctetParseException.class)
    public void octetWrongInTest5() throws OctetParseException {
        Octet octet = new Octet("0-1000");
    }

    @Test()
    public void octetTest1() throws OctetParseException {
        Octet octet = new Octet("125");
        assertNotNull(octet);
    }

    @Test()
    public void octetTest2() throws OctetParseException {
        Octet octet = new Octet("150-170");
        assertNotNull(octet);
    }

    @Test(expected = NullPointerException.class)
    public void octetNullTest() throws OctetParseException {
        Octet octet = new Octet(null);
    }
}
