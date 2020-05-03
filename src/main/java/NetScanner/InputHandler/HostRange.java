package NetScanner.InputHandler;

import Exceptions.HostRangeParseException;
import Exceptions.OctetParseException;
import NetScanner.NetScanner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class HostRange {

    private ArrayList<Octet> value;
    private Integer allCombinationsNumber;
    private static Logger logger = LogManager.getLogger(NetScanner.class);

    public HostRange(String host) throws HostRangeParseException, OctetParseException {
        String[] octets = host.split("\\.");

        if (octets.length != 4)
        {
            HostRangeParseException e = new HostRangeParseException("IP doesn't have 4 octets", host);
            logger.error(e);
            throw e;
        }

        this.allCombinationsNumber = 1;
        this.value = new ArrayList<Octet>();

        for (String octet : octets)
        {
            Octet tmp = new Octet(octet);
            this.value.add(tmp);
            this.allCombinationsNumber *= tmp.getSize();
        }
    }

    public ArrayList<String> getAllHosts()
    {
        ArrayList<String> hosts = new ArrayList<String>();

        StringBuilder currentHost = new StringBuilder();

        for (int i = 0; i < this.allCombinationsNumber; i++)
        {
            int tmpSize = this.allCombinationsNumber;
            for (Octet octet : this.value)
            {
                tmpSize/=octet.getSize();
                currentHost.append(octet.getOctetValue(i, tmpSize));
                if (!checkLastOctet(currentHost))currentHost.append(".");
            }
            hosts.add(currentHost.toString());
            currentHost.setLength(0);
        }

        return hosts;
    }

    private boolean checkLastOctet(StringBuilder host)
    {
        int dotCount = 0, lastIndex = 0;
        while ((lastIndex = host.indexOf(".", lastIndex)+1)>0) dotCount++;
        if (dotCount == 3) return true;
        return false;
    }
}
