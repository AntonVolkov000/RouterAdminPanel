package application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "mac_address")
    private String macAddress;

    @ManyToOne
    @JoinColumn(name = "connection_device_id")
    private ConnectionDeviceType connectionDeviceType;

    public Long getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public ConnectionDeviceType getConnectionDeviceType() {
        return connectionDeviceType;
    }

    public void setConnectionDeviceType(ConnectionDeviceType connectionDeviceType) {
        this.connectionDeviceType = connectionDeviceType;
    }
}
