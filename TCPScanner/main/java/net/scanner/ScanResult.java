package NetScanner;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс хранит результаты сканирования в формате {@link String}:{@link List}. {@link List} состоит из {@link ConnectionInfo}.
 * */
public class ScanResult{

    @Getter @Setter @SerializedName("host") private String host;
    @Getter @Setter @SerializedName("connectionInfo") private List<ConnectionInfo> ports;

    /**
     * Конструктор класса
     * @param inHost IP адрес хоста, к которому производилось подключение;
     * @param connectionInfos список объектов {@link ConnectionInfo}, каждый из которых содержит информацию о подключении к конкретному порту.
     * */
    public ScanResult(String inHost, List<ConnectionInfo> connectionInfos)
    {
        this.host = inHost;
        this.ports = new ArrayList<ConnectionInfo>();
        this.ports.addAll(connectionInfos);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScanResult)
        {
            ScanResult scanResult = (ScanResult)obj;
            if (this.host == scanResult.getHost() && this.ports.equals(scanResult.getPorts()) == true)
            {
                return true;
            }
        }
        return false;
    }
}
