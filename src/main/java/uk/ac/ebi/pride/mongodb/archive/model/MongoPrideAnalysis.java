package uk.ac.ebi.pride.mongodb.archive.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.AnalysisProvider;
import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.software.SoftwareProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;

import java.util.*;

/**
 * The analysis collection aims to provide a mechanisms to included multiple reanalysis for an specific dataset.
 *
 * @author ypriverol
 */
@Data
@Builder
@Document(collection = PrideArchiveField.PRIDE_ANALYSIS_COLLECTION)
public class MongoPrideAnalysis implements PrideArchiveField, AnalysisProvider {

    @Id
    @Indexed(name = PrideArchiveField.ID)
    ObjectId id;

    /** Project Analysis Accession**/
    @Indexed(unique = true, name = PrideArchiveField.ACCESSION)
    String accession;

    /** This is a List of Project Accessions related with the Analysis **/
    @Indexed(name = PrideArchiveField.EXTERNAL_PROJECT_ACCESSIONS)
    Set<String> externalProjectAccessions;


    /** Title of the Project **/
    @Indexed(name = PrideArchiveField.PROJECT_TILE)
    String title;

    /** PRIDE Project short description **/
    @Indexed(name = PrideArchiveField.PROJECT_DESCRIPTION)
    private String description;

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

    /** Publication Date **/
    @Indexed(name = PUBLICATION_DATE)
    private Date publicationDate;

    /** Submission Date **/
    @Indexed(name = SUBMISSION_DATE)
    private Date submissionDate;

    /** Updated date **/
    @Indexed(name = UPDATED_DATE)
    private Date updatedDate;

    /** List of PTMs for the corresponding Project, this PTMs are globallly annotated by the user, it does'nt mean that they have been found in proteins of peptides. */
    @Indexed(name = PROJECT_IDENTIFIED_PTM)
    private Collection<CvParamProvider> ptmList;

    @Indexed(name = EXPERIMENTAL_FACTORS)
    private Map<CvParamProvider, List<CvParamProvider>> experimentalFactors;

    /** General description about the instruments used in the experiment. */
    @Indexed(name = INSTRUMENTS)
    @Getter(AccessLevel.NONE)
    private Collection<CvParamProvider> instruments;

    /** General software information in CVParams terms **/
    @Indexed(name = SOFTWARES)
    private Collection<SoftwareProvider> softwareList;

    /** References related with the dataset in manuscript and papers **/
    @Indexed(name = PROJECT_REFERENCES)
    @Getter(AccessLevel.NONE)
    private Collection<ReferenceProvider> references;

    /** Additional Attributes **/
    @Indexed(name = ADDITIONAL_ATTRIBUTES)
    private Collection<CvParamProvider> attributes;

    @Override
    public String getAccession() {
        return accession;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Collection<? extends String> getSubmitters() {
        return null;
    }

    @Override
    public Collection<? extends String> getHeadLab() {
        return null;
    }

    @Override
    public Collection<? extends String> getKeywords() {
        return null;
    }

    @Override
    public Collection<? extends String> getProjectTags() {
        return null;
    }

    @Override
    public Collection<? extends String> getPtms() {
        return null;
    }

    @Override
    public Collection<? extends String> getSoftwares() {
        return null;
    }

    @Override
    public Collection<? extends String> getQuantificationMethods() {
        return null;
    }

    @Override
    public Collection<? extends String> getReferences() {
        return null;
    }

    @Override
    public String getSubmissionType() {
        return null;
    }

    @Override
    public Date getSubmissionDate() {
        return null;
    }

    @Override
    public Date getPublicationDate() {
        return null;
    }

    @Override
    public Date getUpdateDate() {
        return null;
    }

    @Override
    public List<Comparable> getProjectAccessions() {
        return null;
    }

    @Override
    public boolean isOriginallySubmitted() {
        return false;
    }

    @Override
    public <T extends ContactProvider> T getSubmitter() {
        return null;
    }

    @Override
    public Comparable getId() {
        return null;
    }

    @Override
    public Collection<? extends String> getAdditionalAttributesStrings() {
        return null;
    }
}
