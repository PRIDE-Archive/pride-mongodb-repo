package uk.ac.ebi.pride.mongodb.molecules.model.protein;


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
import uk.ac.ebi.pride.archive.dataprovider.data.protein.ProteinDetailProvider;
import uk.ac.ebi.pride.archive.dataprovider.data.ptm.IdentifiedModificationProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@Document(collection = PrideArchiveField.PRIDE_PROTEIN_COLLECTION_NAME)
@CompoundIndexes({@CompoundIndex(name = "assay_protein_index",
        unique = true,
        def = "{'" + PrideArchiveField.PROTEIN_ASSAY_ACCESSION + "' : 1, '"
                + PrideArchiveField.PROTEIN_REPORTED_ACCESSION +"' : 1}")
})
@JsonIgnoreProperties(ignoreUnknown = true)

public class PrideMongoProteinEvidence implements PrideArchiveField, ProteinDetailProvider {

    /** Generated accession **/
    @Id
    @Indexed(name = PrideArchiveField.ID)
    @JsonIgnore
    private ObjectId id;


    /** Reported File ID is the Identifier of the File mzTab in PRIDE **/
    @Indexed(name = PrideArchiveField.PROTEIN_REPORTED_ACCESSION)
    private String reportedAccession;

    /** Accession in Reported File **/
    @Indexed(name = PrideArchiveField.PROTEIN_ASSAY_ACCESSION)
    private String assayAccession;

    /** External Project that contains the PSM **/
    @Indexed(name = EXTERNAL_PROJECT_ACCESSION)
    private String projectAccession;

    /** Peptide Sequence **/
    @Field(PrideArchiveField.PROTEIN_SEQUENCE)
    private String proteinSequence;


    /** Uniprot protein identifier mapper **/
    @Indexed(name = PrideArchiveField.UNIPROT_MAPPED_PROTEIN_ACCESSION)
    private String uniprotMappedProteinAccession;

    /** Ensembl protein identifier mapper **/
    @Indexed( name = PrideArchiveField.ENSEMBL_MAPPED_PROTEIN_ACCESSION)
    private String ensemblMappedProteinAccession;

    /** Ensembl protein identifier mapper **/
    @Field(PrideArchiveField.PROTEIN_GROUP_MEMBERS)
    private Set<String> proteinGroupMembers;

    /** Ensembl protein identifier mapper **/
    @Field(PrideArchiveField.PROTEIN_DESCRIPTION)
    private String proteinDescription;

    /** Additional Attributes, Contains also scores**/
    @Field(PrideArchiveField.ADDITIONAL_ATTRIBUTES)
    private List<DefaultCvParam> additionalAttributes;

    @Field(PrideArchiveField.PROTEIN_MODIFICATIONS)
    private List<IdentifiedModificationProvider> ptms;

    @Field(PrideArchiveField.BEST_SEARCH_ENGINE)
    private DefaultCvParam bestSearchEngineScore;

    @Field(PrideArchiveField.QUALITY_ESTIMATION_METHOD)
    private List<DefaultCvParam> qualityEstimationMethods;

    @Indexed(name = PrideArchiveField.IS_VALIDATED)
    private Boolean isValid;

    @Indexed( name = PrideArchiveField.IS_DECOY)
    private boolean isDecoy;

    @Indexed(name = PrideArchiveField.NUMBER_PEPTIDEEVIDENCES)
    private Integer numberPeptides;

    @Indexed( name = PrideArchiveField.NUMBER_PSMS)
    private Integer numberPSMs;

    @Indexed( name = PrideArchiveField.PROTEIN_COVERAGE)
    private double sequenceCoverage;

    @Override
    public String getUniprotMapping() {
        return uniprotMappedProteinAccession;
    }

    @Override
    public String getEnsemblMapping() {
        return ensemblMappedProteinAccession;
    }

    @Override
    public Set<String> getProteinGroupMembers() {
        return proteinGroupMembers;
    }

    @Override
    public String getSubmittedSequence() {
        return proteinSequence;
    }

    @Override
    @JsonIgnore
    public Collection<? extends IdentifiedModificationProvider> getIdentifiedModifications() {
        return ptms;
    }

    @Override
    @JsonIgnore
    public String getAccession() {
        return reportedAccession;
    }

    @Override
    @JsonIgnore
    public String getDescription() {
        return proteinDescription;
    }

    @Override
    public Comparable getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public Collection<? extends String> getAdditionalAttributesStrings() {
        return (additionalAttributes != null)? additionalAttributes.stream().map(CvParamProvider::getName).collect(Collectors.toList()): Collections.emptyList() ;
    }

    /**
     * This method add an attribute as {@link CvParamProvider} to the list of attributes.
     * @param attribute Attribute in {@link CvParamProvider}
     */
    public void addAttribute(DefaultCvParam attribute){
        if(additionalAttributes == null)
            additionalAttributes = new ArrayList<>();
        additionalAttributes.add(attribute);
    }

    /**
     * Add a to the list of modifications of a Protein.
     * @param modification {@link IdentifiedModificationProvider}
     */
    public void addIdentifiedModification(IdentifiedModificationProvider modification){
        if(ptms == null)
            ptms = new ArrayList<>();
        ptms.add(modification);
    }

    @Override
    public String toString() {
        return "PrideMongoProteinEvidence{" +
                "id=" + id +
                ", reportedAccession='" + reportedAccession + '\'' +
                ", assayAccession='" + assayAccession + '\'' +
                ", projectAccession='" + projectAccession + '\'' +
                ", proteinSequence='" + proteinSequence + '\'' +
                ", uniprotMappedProteinAccession='" + uniprotMappedProteinAccession + '\'' +
                ", ensemblMappedProteinAccession='" + ensemblMappedProteinAccession + '\'' +
                ", proteinGroups=" + proteinGroupMembers +
                ", proteinDescription='" + proteinDescription + '\'' +
                ", additionalAttributes=" + additionalAttributes +
                ", ptms=" + ptms +
                ", bestSearchEngineScore=" + bestSearchEngineScore +
                ", isDecoy=" + isDecoy +
                '}';
    }
}
