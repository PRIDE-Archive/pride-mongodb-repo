package uk.ac.ebi.pride.mongodb.molecules.model.psm;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.List;


@Data
@Builder
@Document(collection = PrideArchiveField.PRIDE_PSM_COLLECTION_NAME)
public class PrideMongoPsmSummaryEvidence implements PrideArchiveField{

    /** Generated accession **/
    @Id
    @Indexed(name = PrideArchiveField.ID)
    private ObjectId id;

    /** Accession Provided by PRIDE Pipelines **/
    @Indexed(name = PrideArchiveField.USI, unique = true)
    String usi;

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
    private List<DefaultCvParam> additionalAttributes;

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
