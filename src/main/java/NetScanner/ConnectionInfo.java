package NetScanner;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

public class ConnectionInfo {

    @Getter @Setter @SerializedName("port") private Integer port;
    @Getter @Setter @SerializedName("status") private Boolean status;

    public ConnectionInfo(Integer inPort, Boolean inStatus)
    {
        this.port = inPort;
        this.status = inStatus;
    }
}
