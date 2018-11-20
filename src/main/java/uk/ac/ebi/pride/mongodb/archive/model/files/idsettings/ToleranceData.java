package uk.ac.ebi.pride.mongodb.archive.model.files.idsettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;

public class ToleranceData {

    @JsonProperty("tolerance")
    private MongoCvParam tolerance;

    @JsonProperty("unit")
    private MongoCvParam unit;

    public MongoCvParam getTolerance() {
        return tolerance;
    }

    public void setTolerance(MongoCvParam tolerance) {
        this.tolerance = tolerance;
    }

    public MongoCvParam getUnit() {
        return unit;
    }

    public void setUnit(MongoCvParam unit) {
        this.unit = unit;
    }
}
