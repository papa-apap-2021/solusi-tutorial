package apap.tutorial.emsidi.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CabangDetail {
    private String status;

    @JsonProperty("cabang-license")
    private Integer cabangLicense;

    @JsonProperty("valid-until")
    private Date validUntil;
}
