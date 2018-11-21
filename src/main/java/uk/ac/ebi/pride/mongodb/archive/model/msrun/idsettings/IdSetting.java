package uk.ac.ebi.pride.mongodb.archive.model.msrun.idsettings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IdSetting implements Serializable {

    @JsonProperty("id")
    String id;

    @JsonProperty(PrideArchiveField.MS_RUN_ID_SETTINGS_FIXED_MODIFICATIONS)
    List<Modification> fixedModifications;

    @JsonProperty(PrideArchiveField.MS_RUN_ID_SETTINGS_VARIABLE_MODIFICATIONS)
    List<Modification> variableModifications;

    @JsonProperty(PrideArchiveField.MS_RUN_ID_SETTINGS_ENZYMES)
    List<Enzyme> enzymes;

    @JsonProperty(PrideArchiveField.MS_RUN_ID_SETTINGS_FRAGMENT_TOLERANCE)
    List<ToleranceData> fragmentTolerance;

    @JsonProperty(PrideArchiveField.MS_RUN_ID_SETTINGS_PARENT_TOLERANCE)
    List<ToleranceData> parentTolerance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Modification> getFixedModifications() {
        return fixedModifications;
    }

    public void setFixedModifications(List<Modification> fixedModifications) {
        this.fixedModifications = fixedModifications;
    }

    public List<Modification> getVariableModifications() {
        return variableModifications;
    }

    public void setVariableModifications(List<Modification> variableModifications) {
        this.variableModifications = variableModifications;
    }

    public List<Enzyme> getEnzymes() {
        return enzymes;
    }

    public void setEnzymes(List<Enzyme> enzymes) {
        this.enzymes = enzymes;
    }

    public List<ToleranceData> getFragmentTolerance() {
        return fragmentTolerance;
    }

    public void setFragmentTolerance(List<ToleranceData> fragmentTolerance) {
        this.fragmentTolerance = fragmentTolerance;
    }

    public List<ToleranceData> getParentTolerance() {
        return parentTolerance;
    }

    public void setParentTolerance(List<ToleranceData> parentTolerance) {
        this.parentTolerance = parentTolerance;
    }
}
