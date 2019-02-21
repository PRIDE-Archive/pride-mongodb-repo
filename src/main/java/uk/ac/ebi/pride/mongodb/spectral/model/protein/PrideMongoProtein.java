package uk.ac.ebi.pride.mongodb.spectral.model.protein;


import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.data.protein.ProteinDetailProvider;
import uk.ac.ebi.pride.archive.dataprovider.data.ptm.DefaultIdentifiedModification;
import uk.ac.ebi.pride.archive.dataprovider.data.ptm.IdentifiedModificationProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.utilities.util.Tuple;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@Document(collection = PrideArchiveField.PRIDE_PROTEIN_COLLECTION_NAME)
public class PrideMongoProtein implements PrideArchiveField, ProteinDetailProvider {

    /** Generated accession **/
    @Id
    @Indexed(name = PrideArchiveField.ID)
    private ObjectId id;

    /** Accession Provided by PRIDE Pipelines **/
    @Indexed(name = ACCESSION, unique = true)
    String accession;

    /** Reported File ID is the Identifier of the File mzTab in PRIDE **/
    @Field(value = PrideArchiveField.REPORTED_FILE_ID)
    private String reportedFileID;

    /** Accession in Reported File **/
    @Indexed(name = ACCESSION_IN_REPORTED_FILE)
    private String accessionInReportedFile;

    /** External Project that contains the PSM **/
    @Indexed(name = EXTERNAL_PROJECT_ACCESSION)
    private String projectAccession;

    /** Peptide Sequence **/
    @Field(PrideArchiveField.PROTEIN_SEQUENCE)
    private String proteinSequence;


    /** Uniprot protein identifier mapper **/
    @Field(PrideArchiveField.UNIPROT_MAPPED_PROTEIN_ACCESSION)
    private String uniprotMappedProteinAccession;

    /** Ensembl protein identifier mapper **/
    @Field(PrideArchiveField.ENSEMBL_MAPPED_PROTEIN_ACCESSION)
    private String ensemblMappedProteinAccession;

    /** Ensembl protein identifier mapper **/
    @Field(PrideArchiveField.PROTEIN_GROUP_MEMBERS)
    private Set<String> proteinGroups;

    /** Ensembl protein identifier mapper **/
    @Field(PrideArchiveField.PROTEIN_DESCRIPTION)
    private String proteinDescription;

    /** Additional Attributes **/
    @Field(PrideArchiveField.ADDITIONAL_ATTRIBUTES)
    private List<CvParamProvider> additionalAttributes;

    @Field(PrideArchiveField.PROTEIN_MODIFICATIONS)
    private List<IdentifiedModificationProvider> ptms;

    @Field(PrideArchiveField.BEST_SEARCH_ENGINE)
    private CvParamProvider bestSearchEngineScore;

    @Field(PrideArchiveField.MSRUN_PROPERTIES)
    private List<Tuple<CvParamProvider, List<CvParamProvider>>> msRunProperties;

    @Field(PrideArchiveField.PROTEIN_DECOY)
    private boolean decoy;

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
        return proteinGroups;
    }

    @Override
    public String getSubmittedSequence() {
        return proteinSequence;
    }

    @Override
    public Collection<? extends IdentifiedModificationProvider> getIdentifiedModifications() {
        return ptms;
    }

    @Override
    public String getAccession() {
        return accessionInReportedFile;
    }

    @Override
    public String getDescription() {
        return proteinDescription;
    }

    @Override
    public Comparable getId() {
        return accessionInReportedFile;
    }

    @Override
    public Collection<? extends String> getAdditionalAttributesStrings() {
        return (additionalAttributes != null)? additionalAttributes.stream().map(CvParamProvider::getName).collect(Collectors.toList()): Collections.emptyList() ;
    }

    /**
     * This method add an attribute as {@link CvParamProvider} to the list of attributes.
     * @param attribute Attribute in {@link CvParamProvider}
     */
    public void addAttribute(CvParamProvider attribute){
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
        return "PrideMongoProtein{" +
                "id=" + id +
                ", accession='" + accession + '\'' +
                ", reportedFileID='" + reportedFileID + '\'' +
                ", accessionInReportedFile='" + accessionInReportedFile + '\'' +
                ", projectAccession='" + projectAccession + '\'' +
                ", proteinSequence='" + proteinSequence + '\'' +
                ", uniprotMappedProteinAccession='" + uniprotMappedProteinAccession + '\'' +
                ", ensemblMappedProteinAccession='" + ensemblMappedProteinAccession + '\'' +
                ", proteinGroups=" + proteinGroups +
                ", proteinDescription='" + proteinDescription + '\'' +
                ", additionalAttributes=" + additionalAttributes +
                ", ptms=" + ptms +
                '}';
    }
}
