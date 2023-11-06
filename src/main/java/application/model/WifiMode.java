package application.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wifi_mode")
public class WifiMode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mode_id")
    private Long modeId;

    @Column(name = "mode_name")
    private String modeName ;

    @OneToMany(mappedBy = "wifiMode")
    private List<Wifi> wifis;

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public List<Wifi> getWifis() {
        return wifis;
    }

    public void setWifis(List<Wifi> wifis) {
        this.wifis = wifis;
    }
}
