package apap.tutorial.cineplux.model;

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
@Table(name = "film")
public class FilmModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noFilm;

    @NotNull
    @Size(max = 30)
    @Column(nullable = false)
    private String namaFilm;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false)
    private String deskripsiFilm;

    @NotNull
    @Column(nullable = false)
    private Integer isDisplaying;

    //Relasi dengan BioskopModel
    @ManyToMany(mappedBy = "listFilm")
    List<BioskopModel> listBioskop;

}
