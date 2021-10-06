package apap.tutorial.pergipergi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "destinasi")
public class DestinasiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noDestinasi;

    @NotNull
    @Size(max=30)
    @Column(name="negara_destinasi", nullable = false)
    private String negaraDestinasi;

    @Column(name="is_bebas_visa", nullable = false)
    private Boolean isBebasVisa;

    //Relasi dengan AgensiModel
    @ManyToMany(mappedBy = "listDestinasi")
    List<TravelAgensiModel> listTravelAgensi;

    public Long getNoDestinasi() {
        return noDestinasi;
    }

    public void setNoDestinasi(Long noDestinasi) {
        this.noDestinasi = noDestinasi;
    }

    public String getNegaraDestinasi() {
        return negaraDestinasi;
    }

    public void setNegaraDestinasi(String negaraDestinasi) {
        this.negaraDestinasi = negaraDestinasi;
    }

    public Boolean getBebasVisa() {
        return isBebasVisa;
    }

    public void setBebasVisa(Boolean bebasVisa) {
        isBebasVisa = bebasVisa;
    }

    public List<TravelAgensiModel> getListTravelAgensi() {
        return listTravelAgensi;
    }

    public void setListTravelAgensi(List<TravelAgensiModel> listTravelAgensi) {
        this.listTravelAgensi = listTravelAgensi;
    }
}
