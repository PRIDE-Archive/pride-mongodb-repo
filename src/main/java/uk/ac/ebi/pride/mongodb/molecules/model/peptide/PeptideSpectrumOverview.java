package uk.ac.ebi.pride.mongodb.molecules.model.peptide;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PeptideSpectrumOverview {
    int charge;
    double precursorMass;
    String usi;
}
