package NetScanner;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ScanResult{

    @Getter @Setter @SerializedName("host") private String host;
    @Getter @Setter @SerializedName("connectionInfo") private List<ConnectionInfo> ports;

    public ScanResult(String inHost, List<ConnectionInfo> connectionInfos)
    {
        this.host = inHost;
        this.ports = new ArrayList<ConnectionInfo>();
        this.ports.addAll(connectionInfos);
    }
}
