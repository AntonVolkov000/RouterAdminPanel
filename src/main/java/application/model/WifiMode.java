package application.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wifi_modes")
public class WifiMode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mode_id")
    private Long modeId;

    @Column(name = "mode_name")
    private String modeName ;

    @OneToMany(mappedBy = "mode")
    private List<Wifi> wifis;

    public Long getModeId() {
        return modeId;
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
}
