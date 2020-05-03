package NetScanner.InputHandler;

import Exceptions.OctetParseException;
import NetScanner.NetScanner;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Octet {
    @Getter @Setter private ArrayList<Integer> value;
    private static Logger logger = LogManager.getLogger(NetScanner.class);

    public Octet(String octet) throws OctetParseException {
        this.value = new ArrayList<Integer>();
        if (octet.contains("-"))
        {
            String[] minMax = octet.split("-");

            if (minMax.length != 2)
            {
                OctetParseException ex = new OctetParseException("Octet range has more parts", octet);
                logger.error(ex);
                throw ex;
            }

            Integer min = null;
            try {min = Integer.valueOf(minMax[0]);}
            catch (NumberFormatException e){
                OctetParseException ex = new OctetParseException(e.getMessage(), octet);
                logger.error(ex);
                throw ex;
            }
            Integer max = null;
            try {max = Integer.valueOf(minMax[1]);}
            catch (NumberFormatException e){
                OctetParseException ex = new OctetParseException(e.getMessage(), octet);
                logger.error(ex);
                throw ex;
            }

            if ((min < 0 || min > 255) || (max < 0 || max > 255) || (min > max))
            {
                OctetParseException ex = new OctetParseException("Incorrect octet number", octet);
                logger.error(ex);
                throw ex;
            }

            for (int i = min; i <= max; i++)
            {
                this.value.add(i);
            }
        }
        else
        {
            Integer octetNum = null;
            try {octetNum = Integer.valueOf(octet);}
            catch (NumberFormatException e){
                OctetParseException ex = new OctetParseException("Number format exception", octet);
                logger.error(ex);
                throw ex;
            }
            if (octetNum < 0 || octetNum > 255)
            {
                OctetParseException ex = new OctetParseException("Incorrect octet number", octet);
                logger.error(ex);
                throw ex;
            }

            this.value.add(octetNum);
        }
    }

    public Integer getSize(){return this.value.size();}

    public Integer getOctetValue(Integer index, Integer size)
    {
        int newIndex = 0;

        if (size == 1) newIndex = index%(this.value.size());
        else newIndex = index/size;

        return (this.value.get(newIndex));
    }
}
