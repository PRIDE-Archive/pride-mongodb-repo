package uk.ac.ebi.pride.mongodb.archive.model.projects;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectProvider;
import uk.ac.ebi.pride.archive.dataprovider.reference.DefaultReference;
import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.user.DefaultContact;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Pride project information collections contains the information for the corresponding project in PRIDE including information about the title, accession, samples, etc. Please
 * read the specification of a PRIDE project here:
 *
 * @author Yasset Perez-Riverol
 *
 */
@Document(collection = PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME)
@Data
@Builder
@TypeAlias("MongoPrideProject")
public class MongoPrideProject implements ProjectProvider, PrideArchiveField {

    @Id
    @Indexed(name = PrideArchiveField.ID)
    ObjectId id;

    /** Project Accession in PRIDE**/
    @Indexed(unique = true, name = PrideArchiveField.ACCESSION)
    String accession;

    /** Title of the Project **/
    @Field(value = PrideArchiveField.PROJECT_TILE)
    String title;

    /** PRIDE Project short description **/
    @Field(value = PrideArchiveField.PROJECT_DESCRIPTION)
    private String description;

    /** This property defines a relation between files in the Project.
     *  - The first value of the {@link Triple} defines the parent File Accession
     *  - The second value of the {@link Triple} defines the child File Accession
     *  - The third value of the {@link Triple} is a {@link DefaultCvParam} that defines the relation between files
     *  **/
    @Indexed(name = FILE_RELATIONS_IN_PROJECT)
    List<Triple<String, String, DefaultCvParam>> submittedFileRelations;

    /** Sample Processing **/
    @Field(value = PrideArchiveField.PROJECT_SAMPLE_PROTOCOL)
    private String sampleProcessing;

    /** Data Processing Protocol **/
    @Field(value = PrideArchiveField.PROJECT_DATA_PROTOCOL)
    private String dataProcessing;

    /** This is using an abstraction of the User, in this case MongoDB only will retrieve the information related with the userContact **/
    @Indexed(name = PROJECT_SUBMITTER)
    private Collection<DefaultContact> submitters;

    /** This returns a list of head of labs PIs ralted with the experiment **/
    @Indexed(name = PROJECT_PI_NAMES)
    @Getter(AccessLevel.NONE)
    private Collection<DefaultContact> headLab;

    /** List of keywords added by the user **/
    @Indexed(name = PROJECT_KEYWORDS)
    private Collection<String> keywords;

    /** This are tags provided by the curator of PRIDE **/
    @Indexed(name = PROJECT_TAGS)
    private Collection<String> projectTags;

    /* This are CVParams to describe the type of the experiment */
    @Indexed(name = QUANTIFICATION_METHODS)
    @Getter(AccessLevel.NONE)
    private Collection<DefaultCvParam> quantificationMethods;

    /** Submission Type for the experiment, defaults value can be read here {@link uk.ac.ebi.pride.archive.dataprovider.utils.SubmissionTypeConstants} */
    @Indexed( name = PROJECT_SUBMISSION_TYPE)
    private String submissionType;

    /** Publication Date **/
    @Indexed(name = PUBLICATION_DATE)
    private Date publicationDate;

    /** Submission Date **/
    @Indexed(name = SUBMISSION_DATE)
    private Date submissionDate;

    /** Updated date **/
    @Indexed(name = UPDATED_DATE)
    private Date updatedDate;

    /** List of PTMs for the corresponding Project, this PTMs are globally annotated by the user, it does'nt mean that they have been found in proteins of peptides. */
    @Indexed(name = PROJECT_IDENTIFIED_PTM)
    private Collection<DefaultCvParam> ptmList;

    /** Samples description is a generic information about all the samples in the experiment. */
    @Field(value = SAMPLE_ATTRIBUTES_NAMES)
    private List<Tuple<DefaultCvParam, List<DefaultCvParam>>> samplesDescription;

    /** Experimental Factors **/
    @Field(value = EXPERIMENTAL_FACTORS)
    private List<Tuple<DefaultCvParam, List<DefaultCvParam>>> experimentalFactors;

    /** General description about the instruments used in the experiment. */
    @Indexed(name = INSTRUMENTS)
    @Getter(AccessLevel.NONE)
    private Collection<DefaultCvParam> instruments;

    /** General software information in CVParams terms **/
    @Indexed(name = SOFTWARES)
    private Collection<DefaultCvParam> softwareList;

    /** References related with the dataset in manuscript and papers **/
    @Field(value = PROJECT_REFERENCES)
    @Getter(AccessLevel.NONE)
    private Collection<DefaultReference> references;

    /** Additional Attributes **/
    @Indexed(name = ADDITIONAL_ATTRIBUTES)
    private Collection<DefaultCvParam> attributes;

    /** Project DOI for complete Submissions */
    @Indexed(name = PROJECT_DOI)
    private String doi;

    /** Other Omics Type **/
    @Indexed(name = PROJECT_OMICS_LINKS)
    private List<String> omicsLinks;

    /** Countries involve in the Submission **/
    @Indexed(name = COUNTRIES)
    private List<String> countries;

    /** Type of Project: True if is Public, False if is Private **/
    @Indexed(name = PUBLIC_PROJECT)
    private boolean publicProject;

    @Override
    public String getAccession() {
        return accession;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getSampleProcessingProtocol() {
        return this.sampleProcessing;
    }

    @Override
    public String getDataProcessingProtocol() {
        return this.dataProcessing;
    }

    @Override
    public Collection<? extends String> getSubmitters() {
        Collection<String> submitters = Collections.EMPTY_LIST;
        if(this.submitters != null && !this.submitters.isEmpty())
            submitters = this.submitters.stream().map(DefaultContact::getName).collect(Collectors.toList());
        return submitters;
    }

    @Override
    public Collection<? extends String> getPtms() {
        Collection<String> ptms = Collections.EMPTY_LIST;
        if(this.ptmList != null && !this.ptmList.isEmpty())
            ptms = this.ptmList.stream().map(DefaultCvParam::getName).collect(Collectors.toList());
        return ptms;
    }

    @Override
    public Collection<? extends String> getSoftwares() {
        Collection<String> softList = Collections.EMPTY_LIST;
        if(this.softwareList != null && !this.softwareList.isEmpty())
            softList = this.softwareList.stream().map(DefaultCvParam::getName).collect(Collectors.toList());
        return softList;
    }

    @Override
    public Optional<String> getDoi() {
        return Optional.ofNullable(this.doi);
    }

    @Override
    public Collection<? extends String> getOtherOmicsLink() {
        return this.omicsLinks;
    }

    @Override
    public boolean isPublicProject() {
        return this.publicProject;
    }

    @Override
    public Date getUpdateDate() {
        return publicationDate;
    }

    @Override
    public Collection<String> getExperimentalFactors() {
        return null;
    }

    @Override
    public Collection<String> getCountries() {
        Collection<String> countries = Collections.EMPTY_LIST;
        if(this.countries != null && !this.countries.isEmpty())
            countries = this.countries;
        return countries;
    }

    @Override
    public Collection<String> getAllAffiliations() {
        Collection<String> affiliations = Collections.EMPTY_LIST;
        if(this.submitters != null && !this.submitters.isEmpty())
            affiliations = this.submitters.stream().map(DefaultContact::getAffiliation).collect(Collectors.toSet());
        if(this.headLab != null && !this.headLab.isEmpty())
            affiliations.addAll(headLab.stream().map(DefaultContact::getAffiliation).collect(Collectors.toList()));
        return affiliations;
    }

    @Override
    public Collection<String> getSampleAttributes() {
        return null;
    }

    @Override
    public Collection<String> getAdditionalAttributesStrings() {
        return null;
    }

    @Override
    public Comparable getId() {
        return id;
    }

    @Override
    public Collection<String> getHeadLab() {
        Collection<String> headLab = Collections.EMPTY_LIST;
        if(this.headLab != null && !this.headLab.isEmpty())
            headLab =  this.headLab.stream().map(DefaultContact::getName).collect(Collectors.toList());
        return headLab;
    }

    @Override
    public Collection<String> getInstruments() {
        Collection<String> instruments = Collections.EMPTY_LIST;
        if(this.instruments != null && !this.instruments.isEmpty())
            instruments = this.instruments.stream().map(DefaultCvParam::getName).collect(Collectors.toList());
        return instruments;
    }

    @Override
    public Collection<String> getQuantificationMethods() {
        Collection<String> quantificationMethods = Collections.EMPTY_LIST;
        if(this.quantificationMethods != null && !this.quantificationMethods.isEmpty())
            quantificationMethods = this.quantificationMethods.stream().map(DefaultCvParam::getName).collect(Collectors.toList());
        return quantificationMethods;
    }

    @Override
    public Collection<String> getReferences() {
        Collection<String> references = Collections.EMPTY_LIST;
        if(this.references != null && !this.references.isEmpty())
            references = this.references.stream().map(DefaultReference::getReferenceLine).collect(Collectors.toList());
        return references;
    }

    /**
     * Return the Lab heads.
     * @return ContactProvider for all Lab Heads
     */
    public Collection<? extends ContactProvider> getLabHeadContacts(){
        return headLab;
    }

    /**
     * Return the Submitters
     * @return ContactProvider for all Submitters
     */
    public Collection<? extends ContactProvider> getSubmittersContacts(){
        return submitters;
    }

    /**
     * Get the Instruments in {@link DefaultCvParam}
     * @return
     */
    public Collection<? extends CvParamProvider> getInstrumentsCvParams(){
        return instruments;
    }

    /**
     * Get quantification parameters using the Cvterms
     * @return
     */
    public Collection<? extends CvParamProvider> getQuantificationParams(){ return quantificationMethods; }

    /**
     * Get complete references in Cvterms
     * @return
     */
    public Collection<? extends ReferenceProvider> getCompleteReferences(){
        return references;
    }

    /**
     * Get te list of softwares in CvParams
     * @return software list
     */
    public Collection<? extends CvParamProvider> getSoftwareParams(){
        return softwareList;
    }

    @Override
    public String toString() {
        return "MongoPrideProject{" +
                "id=" + getId() +
                ", accession='" + getAccession() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", keywords=" + getKeywords() +
                ", projectTags=" + getProjectTags() +
                ", quantificationMethods=" + getQuantificationMethods() +
                ", submissionType='" + getSubmissionType() + '\'' +
                ", submissionDate=" + getSubmissionDate() +
                ", publicProject=" + isPublicProject() +
                '}';
    }
}
