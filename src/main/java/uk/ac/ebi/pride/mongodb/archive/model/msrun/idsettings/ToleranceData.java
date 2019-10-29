package uk.ac.ebi.pride.mongodb.archive.model.msrun.idsettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
public class ToleranceData {

    @JsonProperty("tolerance")
    private DefaultCvParam tolerance;

    @JsonProperty("unit")
    private DefaultCvParam unit;

    public DefaultCvParam getTolerance() {
        return tolerance;
    }

    public void setTolerance(DefaultCvParam tolerance) {
        this.tolerance = tolerance;
    }

    public DefaultCvParam getUnit() {
        return unit;
    }

    public void setUnit(DefaultCvParam unit) {
        this.unit = unit;
    }
}
