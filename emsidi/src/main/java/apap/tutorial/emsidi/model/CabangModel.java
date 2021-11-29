package apap.tutorial.emsidi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "cabang")
public class CabangModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noCabang;

    @NotNull
    @Size(max=30)
    @Column(name="nama_cabang", nullable = false)
    private String namaCabang;

    @NotNull
    @Size(max=30)
    @Column(name="alamat_cabang", nullable = false)
    private String alamatCabang;

    @NotNull
    @Size(max=30)
    @Column(name="no_telepon_cabang", nullable = false)
    private String noTeleponCabang;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuBuka;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuTutup;

//    Relasi dengan PegawaiModel
    @OneToMany(mappedBy = "cabang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties
    private List<PegawaiModel> listPegawai;

//    Relasi dengan MenuModel
    @ManyToMany
    @JoinTable(
            name = "cabang_menu",
            joinColumns = @JoinColumn(name = "no_cabang"),
            inverseJoinColumns = @JoinColumn(name = "no_menu"))
    @JsonIgnoreProperties
    List<MenuModel> listMenu;
}

