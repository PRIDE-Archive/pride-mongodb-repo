package uk.ac.ebi.pride.mongodb.molecules.model.psm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.Set;


@Data
@Builder
@Document(collection = PrideArchiveField.PRIDE_PSM_COLLECTION_NAME)
@CompoundIndexes({@CompoundIndex(name = "psm_project_assay_peptide_index",
        unique = false,
        def = "{'" + PrideArchiveField.EXTERNAL_PROJECT_ACCESSION + "' : 1, '" + PrideArchiveField.PROTEIN_ASSAY_ACCESSION +"' : 1, '" + PrideArchiveField.PEPTIDE_SEQUENCE + "' : 1 }"), @CompoundIndex(name = "project_inverse_psm", unique = false,
        def = "{'" + PrideArchiveField.EXTERNAL_PROJECT_ACCESSION + "' : -1, '" + PrideArchiveField.PEPTIDE_SEQUENCE + "' : 1 }"
)
      })
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrideMongoPsmSummaryEvidence implements PrideArchiveField{

    /** Generated accession **/
    @Id
    @Indexed(name = PrideArchiveField.ID)
    @JsonIgnore
    private ObjectId id;

    /** Accession Provided by PRIDE Pipelines **/
    @Indexed(name = PrideArchiveField.USI, unique = true)
    String usi;

    /** Accession Provided by PRIDE Pipelines **/
    @Indexed(name = PrideArchiveField.SPECTRA_USI)
    String spectraUsi;

    /** Accession in Reported File **/
    @Field(value = PrideArchiveField.PROTEIN_ASSAY_ACCESSION)
    private String assayAccession;

    /** External Project that contains the PSM **/
    @Indexed(name = PrideArchiveField.EXTERNAL_PROJECT_ACCESSION)
    private String projectAccession;

    /** Peptide Sequence **/
    @Indexed(name = PrideArchiveField.PEPTIDE_SEQUENCE)
    private String peptideSequence;

    /** Modified  Sequence **/
    @Indexed(name = PrideArchiveField.MODIFIED_PEPTIDE_SEQUENCE)
    private String modifiedPeptideSequence;

    /** Additional Attributes **/
    @Field(value = PrideArchiveField.ADDITIONAL_ATTRIBUTES)
    private Set<CvParam> additionalAttributes;

    @Indexed( name = PrideArchiveField.IS_DECOY)
    private Boolean isDecoy;

    @Indexed(name = PrideArchiveField.IS_VALIDATED)
    private Boolean isValid;

    @Indexed (name = PrideArchiveField.CHARGE)
    private Integer charge;

    @Indexed( name = PrideArchiveField.PRECURSOR_MASS)
    private Double precursorMass;

    @Field (value = PrideArchiveField.PSM_SUMMARY_FILE)
    private String fileName;


}
