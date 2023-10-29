package application.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "connection_device_type")
public class ConnectionDeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "connection_device_id")
    private Long connectionDeviceId;

    @Column(name = "connection_device_name")
    private String connectionDeviceName;

    @OneToMany(mappedBy = "connectionDeviceType")
    private List<Device> devices;

    public Long getConnectionDeviceId() {
        return connectionDeviceId;
    }

    public String getConnectionDeviceName() {
        return connectionDeviceName;
    }

    public void setConnectionDeviceName(String connectionDeviceName) {
        this.connectionDeviceName = connectionDeviceName;
    }

    public List<Device> getDevices() {
        return devices;
    }
}
