package uk.ac.ebi.pride.mongodb.archive.model.projects;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectProvider;
import uk.ac.ebi.pride.archive.dataprovider.reference.Reference;
import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.Contact;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Pride project information collections contains the information for the corresponding project in PRIDE including information about the title, accession, samples, etc. Please
 * read the specification of a PRIDE project here:
 *
 * @author Yasset Perez-Riverol
 */
@Document(collection = PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME)
@Data
@SuperBuilder
@TypeAlias("MongoPrideProject")
@AllArgsConstructor
public class MongoPrideProject implements ProjectProvider, PrideArchiveField {
    public MongoPrideProject() {
    }

    @Id
    @Indexed(name = PrideArchiveField.ID)
    protected ObjectId id;

    /**
     * Project Accession in PRIDE
     **/
    @Indexed(unique = true, name = PrideArchiveField.ACCESSION)
    protected String accession;

    /**
     * Title of the Project
     **/
    @Field(value = PrideArchiveField.PROJECT_TILE)
    protected String title;

    /**
     * PRIDE Project short description
     **/
    @Field(value = PrideArchiveField.PROJECT_DESCRIPTION)
    protected String description;

    /**
     * This property defines a relation between files in the Project.
     * - The first value of the {@link Triple} defines the parent File Accession
     * - The second value of the {@link Triple} defines the child File Accession
     * - The third value of the {@link Triple} is a {@link CvParam} that defines the relation between files
     **/
    @Indexed(name = FILE_RELATIONS_IN_PROJECT)
    protected List<Triple<String, String, CvParam>> submittedFileRelations;

    /**
     * Sample Processing
     **/
    @Field(value = PrideArchiveField.PROJECT_SAMPLE_PROTOCOL)
    protected String sampleProcessing;

    /**
     * Data Processing Protocol
     **/
    @Field(value = PrideArchiveField.PROJECT_DATA_PROTOCOL)
    protected String dataProcessing;

    /**
     * This is using an abstraction of the User, in this case MongoDB only will retrieve the information related with the userContact
     **/
    @Indexed(name = PROJECT_SUBMITTER)
    protected Collection<Contact> submitters;

    /**
     * This returns a list of head of labs PIs ralted with the experiment
     **/
    @Indexed(name = PROJECT_PI_NAMES)
    @Getter(AccessLevel.NONE)
    protected Collection<Contact> headLab;

    /**
     * List of keywords added by the user
     **/
    @Indexed(name = PROJECT_KEYWORDS)
    protected Collection<String> keywords;

    /**
     * This are tags provided by the curator of PRIDE
     **/
    @Indexed(name = PROJECT_TAGS)
    protected Collection<String> projectTags;

    /* This are CVParams to describe the type of the experiment */
    @Indexed(name = QUANTIFICATION_METHODS)
    @Getter(AccessLevel.NONE)
    protected Collection<CvParam> quantificationMethods;

    /**
     * Submission Type for the experiment, defaults value can be read here {@link uk.ac.ebi.pride.archive.dataprovider.utils.SubmissionTypeConstants}
     */
    @Indexed(name = PROJECT_SUBMISSION_TYPE)
    protected String submissionType;

    /**
     * Publication Date
     **/
    @Indexed(name = PUBLICATION_DATE)
    protected Date publicationDate;

    /**
     * Submission Date
     **/
    @Indexed(name = SUBMISSION_DATE)
    protected Date submissionDate;

    /**
     * Updated date
     **/
    @Indexed(name = UPDATED_DATE)
    protected Date updatedDate;

    /**
     * List of PTMs for the corresponding Project, this PTMs are globally annotated by the user, it does'nt mean that they have been found in proteins of peptides.
     */
    @Indexed(name = PROJECT_IDENTIFIED_PTM)
    protected Collection<CvParam> ptmList;

    /**
     * Samples description is a generic information about all the samples in the experiment.
     */
    @Field(value = SAMPLE_ATTRIBUTES_NAMES)
    protected List<Tuple<CvParam, Set<CvParam>>> samplesDescription;

    /**
     * Experimental Factors
     **/
    @Field(value = EXPERIMENTAL_FACTORS)
    protected List<Tuple<CvParam, Set<CvParam>>> experimentalFactors;

    /**
     * General description about the instruments used in the experiment.
     */
    @Indexed(name = INSTRUMENTS)
    @Getter(AccessLevel.NONE)
    protected Collection<CvParam> instruments;

    /**
     * General software information in CVParams terms
     **/
    @Indexed(name = SOFTWARES)
    protected Collection<CvParam> softwareList;

    @Indexed(name = EXPERIMENT_TYPES)
    protected Collection<CvParam> experimentTypes;

    /**
     * References related with the dataset in manuscript and papers
     **/
    @Field(value = PROJECT_REFERENCES)
    @Getter(AccessLevel.NONE)
    protected Collection<Reference> references;

    /**
     * Additional Attributes
     **/
    @Indexed(name = ADDITIONAL_ATTRIBUTES)
    protected Collection<CvParam> attributes;

    /**
     * Project DOI for complete Submissions
     */
    @Indexed(name = PROJECT_DOI)
    protected String doi;

    /**
     * Other Omics Type
     **/
    @Indexed(name = PROJECT_OMICS_LINKS)
    protected List<String> omicsLinks;

    /**
     * Countries involve in the Submission
     **/
    @Indexed(name = COUNTRIES)
    protected List<String> countries;

    /**
     * Type of Project: True if is Public, False if is Private
     **/
    @Indexed(name = PUBLIC_PROJECT)
    protected boolean publicProject;

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
        if (this.submitters != null && !this.submitters.isEmpty())
            submitters = this.submitters.stream().map(Contact::getName).collect(Collectors.toList());
        return submitters;
    }

    @Override
    public Collection<? extends String> getPtms() {
        Collection<String> ptms = Collections.EMPTY_LIST;
        if (this.ptmList != null && !this.ptmList.isEmpty())
            ptms = this.ptmList.stream().map(CvParam::getName).collect(Collectors.toList());
        return ptms;
    }

    @Override
    public Collection<? extends String> getSoftwares() {
        Collection<String> softList = Collections.EMPTY_LIST;
        if (this.softwareList != null && !this.softwareList.isEmpty())
            softList = this.softwareList.stream().map(CvParam::getName).collect(Collectors.toList());
        return softList;
    }

    public Collection<? extends String> getExperimentTypeStrings() {
        Collection<String> exList = Collections.EMPTY_LIST;
        if (this.experimentTypes != null && !this.experimentTypes.isEmpty())
            exList = this.experimentTypes.stream().map(CvParam::getName).collect(Collectors.toList());
        return exList;
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
        if (this.countries != null && !this.countries.isEmpty())
            countries = this.countries;
        return countries;
    }

    @Override
    public Collection<String> getAllAffiliations() {
        Collection<String> affiliations = Collections.EMPTY_LIST;
        if (this.submitters != null && !this.submitters.isEmpty())
            affiliations = this.submitters.stream().map(Contact::getAffiliation).collect(Collectors.toSet());
        if (this.headLab != null && !this.headLab.isEmpty())
            affiliations.addAll(headLab.stream().map(Contact::getAffiliation).collect(Collectors.toList()));
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
        if (this.headLab != null && !this.headLab.isEmpty())
            headLab = this.headLab.stream().map(Contact::getName).collect(Collectors.toList());
        return headLab;
    }

    public Collection<Contact> getHeadLabContacts() {
        return this.headLab;
    }

    @Override
    public Collection<String> getInstruments() {
        Collection<String> instruments = Collections.EMPTY_LIST;
        if (this.instruments != null && !this.instruments.isEmpty())
            instruments = this.instruments.stream().map(CvParam::getName).collect(Collectors.toList());
        return instruments;
    }

    @Override
    public Collection<String> getQuantificationMethods() {
        Collection<String> quantificationMethods = Collections.EMPTY_LIST;
        if (this.quantificationMethods != null && !this.quantificationMethods.isEmpty())
            quantificationMethods = this.quantificationMethods.stream().map(CvParam::getName).collect(Collectors.toList());
        return quantificationMethods;
    }

    @Override
    public Collection<String> getReferences() {
        Collection<String> references = Collections.EMPTY_LIST;
        if (this.references != null && !this.references.isEmpty())
            references = this.references.stream().map(Reference::getReferenceLine).collect(Collectors.toList());
        return references;
    }

    public Collection<Reference> getReferencesWithPubmed() {
        return this.references;
    }

    /**
     * Return the Lab heads.
     *
     * @return ContactProvider for all Lab Heads
     */
    public Collection<? extends ContactProvider> getLabHeadContacts() {
        return headLab;
    }

    /**
     * Return the Submitters
     *
     * @return ContactProvider for all Submitters
     */
    public Collection<? extends ContactProvider> getSubmittersContacts() {
        return submitters;
    }

    /**
     * Get the Instruments in {@link CvParam}
     *
     * @return
     */
    public Collection<? extends CvParamProvider> getInstrumentsCvParams() {
        return instruments;
    }

    /**
     * Get quantification parameters using the Cvterms
     *
     * @return
     */
    public Collection<? extends CvParamProvider> getQuantificationParams() {
        return quantificationMethods;
    }

    /**
     * Get complete references in Cvterms
     *
     * @return
     */
    public Collection<? extends ReferenceProvider> getCompleteReferences() {
        return references;
    }

    /**
     * Get te list of softwares in CvParams
     *
     * @return software list
     */
    public Collection<? extends CvParamProvider> getSoftwareParams() {
        return softwareList;
    }

    @Override
    public String toString() {
        String prefix = "MongoPrideProject{";
        if (this instanceof MongoImportedProject) {
            prefix = "MongoImportedProject{";
        }
        return prefix +
                "id=" + id +
                ", accession='" + accession + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", submittedFileRelations=" + submittedFileRelations +
                ", sampleProcessing='" + sampleProcessing + '\'' +
                ", dataProcessing='" + dataProcessing + '\'' +
                ", submitters=" + submitters +
                ", headLab=" + headLab +
                ", keywords=" + keywords +
                ", projectTags=" + projectTags +
                ", quantificationMethods=" + quantificationMethods +
                ", submissionType='" + submissionType + '\'' +
                ", publicationDate=" + publicationDate +
                ", submissionDate=" + submissionDate +
                ", updatedDate=" + updatedDate +
                ", ptmList=" + ptmList +
                ", samplesDescription=" + samplesDescription +
                ", experimentalFactors=" + experimentalFactors +
                ", instruments=" + instruments +
                ", softwareList=" + softwareList +
                ", references=" + references +
                ", attributes=" + attributes +
                ", doi='" + doi + '\'' +
                ", omicsLinks=" + omicsLinks +
                ", countries=" + countries +
                ", publicProject=" + publicProject +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoPrideProject that = (MongoPrideProject) o;
        return publicProject == that.publicProject &&
                Objects.equals(accession, that.accession) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                equalsCollection(submittedFileRelations, that.submittedFileRelations) &&
                Objects.equals(sampleProcessing, that.sampleProcessing) &&
                Objects.equals(dataProcessing, that.dataProcessing) &&
                equalsCollection(submitters, that.submitters) &&
                equalsCollection(headLab, that.headLab) &&
                equalsCollection(keywords, that.keywords) &&
                equalsCollection(projectTags, that.projectTags) &&
                equalsCollection(quantificationMethods, that.quantificationMethods) &&
                Objects.equals(submissionType, that.submissionType) &&
                equalsDate(publicationDate, that.publicationDate) &&
                equalsDate(submissionDate, that.submissionDate) &&
                equalsDate(updatedDate, that.updatedDate) &&
                equalsCollection(ptmList, that.ptmList) &&
                equalsCollection(samplesDescription, that.samplesDescription) &&
                equalsCollection(experimentalFactors, that.experimentalFactors) &&
                equalsCollection(instruments, that.instruments) &&
                equalsCollection(softwareList, that.softwareList) &&
                equalsCollection(references, that.references) &&
                equalsCollection(attributes, that.attributes) &&
                Objects.equals(doi, that.doi) &&
                equalsCollection(omicsLinks, that.omicsLinks) &&
                equalsCollection(getCountries(), that.getCountries());
    }

    @Override
    public int hashCode() {
        return Objects.hash(accession, title, description, submittedFileRelations, sampleProcessing, dataProcessing, submitters, headLab, keywords, projectTags, quantificationMethods, submissionType, publicationDate, submissionDate, updatedDate, ptmList, samplesDescription, experimentalFactors, instruments, softwareList, references, attributes, doi, omicsLinks, countries, publicProject);
    }

    public boolean equalsCollection(Collection a, Collection b) {
        return (a == b) || (a != null && b != null && Objects.equals(new HashSet<>(a), new HashSet<>(b)));
    }

    public boolean equalsDate(Date a, Date b) {
        return ((a == b) || (a != null && b != null && a.getTime() == b.getTime()));
    }
}
