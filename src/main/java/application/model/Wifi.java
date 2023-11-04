package application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wifi")
public class Wifi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wifi_id")
    private Long wifiId;

    @Column(name = "ssid")
    private String ssid;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "mode_id")
    private WifiMode wifiMode;

    @OneToOne (mappedBy="wifi")
    private GeneralConfig generalConfig;

    public Long getWifiId() {
        return wifiId;
    }

    public void setWifiId(Long wifiId) {
        this.wifiId = wifiId;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public WifiMode getWifiMode() {
        return wifiMode;
    }

    public void setWifiMode(WifiMode wifiMode) {
        this.wifiMode = wifiMode;
    }

    public GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    public void setGeneralConfig(GeneralConfig generalConfig) {
        this.generalConfig = generalConfig;
    }
}
