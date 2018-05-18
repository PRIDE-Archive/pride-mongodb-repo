package uk.ac.ebi.pride.mongodb.archive.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectProvider;
import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;


import java.util.*;
import java.util.stream.Collectors;


/**
 * Pride project information collections contains the information for the corresponding project in PRIDE including information about the title, accession, samples, etc. Please
 * read the specification of a PRIDE project here:
 *
 * @author: Yasset Perez-Riverol
 * @version $Id$
 */
@Document(collection = PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME)
@Data
@Builder
public class MongoPrideProject implements ProjectProvider, PrideArchiveField {

    @Id
    @Indexed(name = PrideArchiveField.ID)
    ObjectId id;

    /** Project Accession in PRIDE**/
    @Indexed(unique = true, name = PrideArchiveField.ACCESSION)
    String accession;

    /** Title of the Project **/
    @Indexed(name = PrideArchiveField.PROJECT_TILE)
    String title;

    /** PRIDE Project short description **/
    @Indexed(name = PrideArchiveField.PROJECT_DESCRIPTION)
    private String description;

    /** Sample Processing **/
    @Indexed(name = PrideArchiveField.PROJECT_SAMPLE_PROTOCOL)
    private String sampleProcessing;

    /** Data Processing Protocol **/
    @Indexed(name = PrideArchiveField.PROJECT_DATA_PROTOCOL)
    private String dataProcessing;

    /** This is using an abstraction of the User, in this case MongoDB only will retrieve the information related with the userContact **/
    @Indexed(name = PROJECT_SUBMITTER)
    private Collection<ContactProvider> submitters;

    /** This returns a list of head of labs PIs ralted with the experiment **/
    @Indexed(name = PROJECT_PI_NAMES)
    @Getter(AccessLevel.NONE)
    private Collection<ContactProvider> headLab;

    /** List of keywords added by the user **/
    @Indexed(name = PROJECT_KEYWORDS)
    private Collection<String> keywords;

    /** This are tags provided by the curator of PRIDE **/
    @Indexed(name = PROJECT_TAGS)
    private Collection<String> projectTags;

    /* This are CVparams to describe the type of the experiment */
    @Indexed(name = QUANTIFICATION_METHODS)
    @Getter(AccessLevel.NONE)
    private Collection<CvParamProvider> quantificationMethods;

    /** Submission Type for the experiment, defaults value can be read here {@link uk.ac.ebi.pride.archive.dataprovider.utils.SubmissionTypeConstants} */
    @Indexed( name = PROJECT_SUBMISSION_TYPE)
    private String submissionType;

    /** Publication Date **/
    @Indexed(name = PROJECT_PUBLICATION_DATE)
    private Date publicationDate;

    /** Submission Date **/
    @Indexed(name = PROJECT_SUBMISSION_DATE)
    private Date submissionDate;

    /** Updated date **/
    @Indexed(name = PROJECT_UPDATED_DATE)
    private Date updatedDate;

    /** List of PTMs for the corresponding Project, this PTMs are globallly annotated by the user, it does'nt mean that they have been found in proteins of peptides. */
    @Indexed(name = PROJECT_IDENTIFIED_PTM)
    private Collection<CvParamProvider> ptmList;

    /**
     * Samples description is a generic information about all the samples in the experiment. Import: These details do not have detail information:
     * Not specific to any Sample.
     */
    @Indexed(name = SAMPLE_ATTRIBUTES_NAMES)
    private Map<CvParamProvider, List<CvParamProvider>> samplesDescription;

    @Indexed(name = EXPERIMENTAL_FACTORS)
    private Map<CvParamProvider, List<CvParamProvider>> experimentalFactors;

    /** General description about the instruments used in the experiment. */
    @Indexed(name = INSTRUMENTS)
    @Getter(AccessLevel.NONE)
    private Collection<CvParamProvider> instruments;

    /** General software information in CVParams terms **/
    @Indexed(name = SOFTWARES)
    private Collection<CvParamProvider> softwareList;

    /** References related with the dataset in manuscript and papers **/
    @Indexed(name = PROJECT_REFERENCES)
    @Getter(AccessLevel.NONE)
    private Collection<ReferenceProvider> references;

    /** Additional Attributes **/
    @Indexed(name = ADDITIONAL_ATTRIBUTES)
    private Collection<CvParamProvider> attributes;

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
    public String getProjectDescription() {
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
        return this.submitters.stream().map(ContactProvider::getName).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends String> getPtms() {
        return this.ptmList.stream().map(CvParamProvider::getName).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends String> getSoftwares() {
        return this.softwareList.stream().map(CvParamProvider::getName).collect(Collectors.toList());
    }

    @Override
    public Optional<String> getDoi() {
        return Optional.of(this.doi);
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
    public Collection<? extends String> getExperimentalFactors() {
        return null;
    }

    @Override
    public Collection<? extends String> getCountries() {
        return this.countries;
    }

    @Override
    public Collection<? extends String> getAllAffiliations() {
        Set<String> affiliations = new HashSet<>(submitters.stream().map(ContactProvider::getAffiliation).collect(Collectors.toList()));
        affiliations.addAll(headLab.stream().map(ContactProvider::getAffiliation).collect(Collectors.toList()));
        return affiliations;
    }

    @Override
    public Collection<? extends String> getSampleAttributes() {
        return null;
    }

    @Override
    public Collection<? extends String> getAdditionalAttributesStrings() {
        return null;
    }

    @Override
    public Comparable getId() {
        return id;
    }

    @Override
    public Collection<? extends String> getHeadLab() {
        return headLab.stream().map(ContactProvider::getName).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends String> getInstruments() {
        return instruments.stream().map(CvParamProvider::getName).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends String> getQuantificationMethods() {
        return quantificationMethods.stream().map(CvParamProvider::getName).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends String> getReferences() {
        return references.stream().map(ReferenceProvider::getReferenceLine).collect(Collectors.toList());
    }
}
