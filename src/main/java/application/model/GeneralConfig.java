package application.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "general_config")
public class GeneralConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "admin_account_id")
    private AdminAccount adminAccount;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "config_id")
    private Config config;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "wifi_id")
    private Wifi wifi;

    @OneToMany(mappedBy = "generalConfig")
    private List<Device> devices;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AdminAccount getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(AdminAccount adminAccount) {
        this.adminAccount = adminAccount;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Wifi getWifi() {
        return wifi;
    }

    public void setWifi(Wifi wifi) {
        this.wifi = wifi;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
