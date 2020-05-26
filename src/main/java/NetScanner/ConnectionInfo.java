package NetScanner;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс хранит результат подключения к конкретному порту
 * */
public class ConnectionInfo {

    @Getter @Setter @SerializedName("port") private Integer port;
    @Getter @Setter @SerializedName("status") private Boolean status;

    /**
     * Конструктор класса
     * @param inPort порт, к которому происходило подключение;
     * @param inStatus статус подключения: true если произошло успешное подключение или false если подключиться не удалось.
     * */
    public ConnectionInfo(Integer inPort, Boolean inStatus)
    {
        this.port = inPort;
        this.status = inStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConnectionInfo)
        {
            ConnectionInfo connectionInfo = (ConnectionInfo) obj;
            if (this.port == connectionInfo.getPort() && this.status == connectionInfo.getStatus())
            {
                return true;
            }
        }
        return false;
    }
}
