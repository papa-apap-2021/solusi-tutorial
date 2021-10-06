package apap.tutorial.pergipergi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "travel_agensi")
public class TravelAgensiModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noAgensi;

    @NotNull
    @Size(max=30)
    @Column(name="nama_agensi", nullable = false)
    private String namaAgensi;

    @NotNull
    @Size(max=30)
    @Column(name="alamat_agensi", nullable = false)
    private String alamatAgensi;

    @NotNull
    @Size(max=30)
    @Column(name="no_telepon_agensi", nullable = false)
    private String noTeleponAgensi;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuBuka;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuTutup;

    //Relasi dengan TourGuideModel
    @OneToMany(mappedBy = "agensi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TourGuideModel> listTourGuide;

    //Relasi dengan DestinasiModel
    @ManyToMany
    @JoinTable(
            name = "agensi_destinasi",
            joinColumns = @JoinColumn(name = "no_agensi"),
            inverseJoinColumns = @JoinColumn(name = "no_destinasi"))
    List<DestinasiModel> listDestinasi;

    public Long getNoAgensi() {
        return noAgensi;
    }

    public void setNoAgensi(Long noAgensi) {
        this.noAgensi = noAgensi;
    }

    public String getNamaAgensi() {
        return namaAgensi;
    }

    public void setNamaAgensi(String namaAgensi) {
        this.namaAgensi = namaAgensi;
    }

    public String getAlamatAgensi() {
        return alamatAgensi;
    }

    public void setAlamatAgensi(String alamatAgensi) {
        this.alamatAgensi = alamatAgensi;
    }

    public String getNoTeleponAgensi() {
        return noTeleponAgensi;
    }

    public void setNoTeleponAgensi(String noTeleponAgensi) {
        this.noTeleponAgensi = noTeleponAgensi;
    }

    public LocalTime getWaktuBuka() {
        return waktuBuka;
    }

    public void setWaktuBuka(LocalTime waktuBuka) {
        this.waktuBuka = waktuBuka;
    }

    public LocalTime getWaktuTutup() {
        return waktuTutup;
    }

    public void setWaktuTutup(LocalTime waktuTutup) {
        this.waktuTutup = waktuTutup;
    }

    public List<TourGuideModel> getListTourGuide() {
        return listTourGuide;
    }

    public void setListTourGuide(List<TourGuideModel> listTourGuide) {
        this.listTourGuide = listTourGuide;
    }

    public List<DestinasiModel> getListDestinasi() {
        return listDestinasi;
    }

    public void setListDestinasi(List<DestinasiModel> listDestinasi) {
        this.listDestinasi = listDestinasi;
    }
}
