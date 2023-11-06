package application.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "connection_internet_type")
public class ConnectionInternetType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "connection_internet_id")
    private Long connectionInternetId;

    @Column(name = "connection_internet_name")
    private String connectionInternetName;

    @OneToMany(mappedBy = "connectionInternetType")
    private List<Config> configs;

    public ConnectionInternetType(long connectionInternetId, String connectionInternetName) {
        this.connectionInternetId = connectionInternetId;
        this.connectionInternetName = connectionInternetName;
    }

    public ConnectionInternetType() {

    }

    public Long getConnectionInternetId() {
        return connectionInternetId;
    }

    public void setConnectionInternetId(Long connectionInternetId) {
        this.connectionInternetId = connectionInternetId;
    }

    public String getConnectionInternetName() {
        return connectionInternetName;
    }

    public void setConnectionInternetName(String connectionInternetName) {
        this.connectionInternetName = connectionInternetName;
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }
}
