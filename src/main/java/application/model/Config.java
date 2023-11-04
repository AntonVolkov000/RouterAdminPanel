package application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "config_id")
    private Long configId;

    @Column(name = "ip")
    private String ip;

    @Column(name = "mask")
    private String mask;

    @Column(name = "gate")
    private String gate;

    @ManyToOne
    @JoinColumn(name = "connection_internet_id")
    private ConnectionInternetType connectionInternetType;

    @OneToOne (mappedBy="config")
    private GeneralConfig generalConfig;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public ConnectionInternetType getConnectionInternetType() {
        return connectionInternetType;
    }

    public void setConnectionInternetType(ConnectionInternetType connectionInternetType) {
        this.connectionInternetType = connectionInternetType;
    }

    public GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    public void setGeneralConfig(GeneralConfig generalConfig) {
        this.generalConfig = generalConfig;
    }
}
