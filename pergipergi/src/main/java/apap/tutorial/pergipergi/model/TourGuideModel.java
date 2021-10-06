package apap.tutorial.pergipergi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tour_guide")
public class TourGuideModel  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noTourGuide;

    @NotNull
    @Size(max=30)
    @Column(name="nama_tour_guide", nullable = false)
    private String namaTourGuide;

    @NotNull
    @Column(name="jenis_kelamin", nullable = false)
    private Integer jenisKelamin;

    //Relasi dengan AgensiModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "no_agensi", referencedColumnName = "noAgensi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TravelAgensiModel agensi;

    public Long getNoTourGuide() {
        return noTourGuide;
    }

    public void setNoTourGuide(Long noTourGuide) {
        this.noTourGuide = noTourGuide;
    }

    public String getNamaTourGuide() {
        return namaTourGuide;
    }

    public void setNamaTourGuide(String namaTourGuide) {
        this.namaTourGuide = namaTourGuide;
    }

    public Integer getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(Integer jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public TravelAgensiModel getAgensi() {
        return agensi;
    }

    public void setAgensi(TravelAgensiModel agensi) {
        this.agensi = agensi;
    }
}
