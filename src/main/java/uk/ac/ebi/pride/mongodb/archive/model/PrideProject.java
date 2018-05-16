package uk.ac.ebi.pride.mongodb.archive.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectProvider;
import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.archive.dataprovider.utils.Tuple;


import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


/**
 * Pride project information collections contains the information for the corresponding project in PRIDE including information about the title, accession, samples, etc. Please
 * read the specification of a PRIDE dataset here:
 *
 * @author: Yasset Perez-Riverol
 * @version $Id$
 */
@Document(collection = "project")
@Data
public class PrideProject implements ProjectProvider, PrideProjectField{

    @Id
    @Indexed(name = PrideProjectField.ID)
    ObjectId id;

    /** Project Accession in PRIDE**/
    @Indexed(unique = true, name = PrideProjectField.ACCESSION)
    String accession;

    /** Title of the Project **/
    @Indexed(name = PrideProjectField.PROJECT_TILE)
    String title;

    /** PRIDE Project short description **/
    @Indexed(name = PrideProjectField.PROJECT_DESCRIPTION)
    private String description;

    /** Sample Processing **/
    @Indexed(name = PrideProjectField.PROJECT_SAMPLE_PROTOCOL)
    private String sampleProcessing;

    /** Data Processing Protocol **/
    @Indexed(name = PrideProjectField.PROJECT_DATA_PROTOCOL)
    private String dataProcessing;

    /** This is using an abstraction of the User, in this case MongoDB only will retrieve the information related with the userContact **/
    @Indexed(name = PROJECT_SUBMITTER)
    private Collection<ContactProvider> submitters;

    /** This returns a list of head of labs PIs ralted with the experiment **/
    @Indexed(name = PROJECT_PI_NAMES)
    private Collection<ContactProvider> headLab;

    /** List of keywords added by the user **/
    @Indexed(name = PROJECT_KEYWORDS)
    private Collection<String> keywords;

    /** This are tags provided by the curator of PRIDE **/
    @Indexed(name = PROJECT_TAGS)
    private Collection<String> projectTags;

    /* This are CVparams to describe the type of the experiment */
    @Indexed(name = QUANTIFICATION_METHODS)
    private Collection<CvParamProvider> quantificationMethods;

    /** Submission Type for the experiment, defaults value can be read here {@link uk.ac.ebi.pride.archive.dataprovider.utils.SubmissionTypeConstants} */
    private String submissionType;

    /** Publication Date **/
    private Date publicationDate;

    /** Submission Date **/
    private Date submissionDate;

    /** Updated date **/
    private Date updatedDate;

    /**
     *  List of PTMs for the corresponding Project, this PTMs are globallly annotated by the user, it does'nt mean that they have been found in proteins of peptides.
     */
    @Indexed(name = PROJECT_IDENTIFIED_PTM_STRING)
    private Collection<CvParamProvider> ptmList;

    /**
     * Samples description is a generic information about all the samples in the experiment. Import: These details do not have detail information:
     * Not specific to any Sample.
     */
    @Indexed(name = SAMPLE_ATTRIBUTES_NAMES)
    private Collection<CvParamProvider> samplesDescription;

    /**
     * General description about the instruments used in the experiment.
     */
    @Indexed(name = INSTRUMENTS)
    private Collection<CvParamProvider> instruments;

    /** General software information in CVParams terms **/
    @Indexed(name = SOFTWARES)
    private Collection<CvParamProvider> softwareList;

    /** References related with the dataset in manuscript and papers **/
    @Indexed(name = PROJECT_REFERENCES)
    private Collection<ReferenceProvider> references;

    @Indexed(name = ADDITIONAL_ATTRIBUTES)
    private Collection<CvParamProvider> attributes;

    @Override
    public String getAccession() {
        return accession;
    }

    @Override
    public String getProjectDescription() {
        return null;
    }

    @Override
    public String getSampleProcessingProtocol() {
        return null;
    }

    @Override
    public String getDataProcessingProtocol() {
        return null;
    }

    @Override
    public Collection<? extends String> getSubmitters() {
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
    public Optional<String> getDoi() {
        return Optional.empty();
    }

    @Override
    public Collection<? extends String> getOtherOmicsLink() {
        return null;
    }

    @Override
    public boolean isPublicProject() {
        return false;
    }

    @Override
    public Date getUpdateDate() {
        return null;
    }

    @Override
    public Collection<? extends String> getExperimentalFactors() {
        return null;
    }

    @Override
    public Collection<? extends String> getCountries() {
        return null;
    }

    @Override
    public Collection<? extends String> getAllAffiliations() {
        return null;
    }

    @Override
    public Collection<? extends String> getSampleAttributes() {
        return null;
    }

    @Override
    public Collection<? extends String> getAdditionalAttributes() {
        return null;
    }

    @Override
    public Comparable getId() {
        return null;
    }
}
