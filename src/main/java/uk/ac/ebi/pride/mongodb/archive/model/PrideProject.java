package uk.ac.ebi.pride.mongodb.archive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.assay.AssayProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectTagProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.SubmissionType;
import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;

import java.util.Collection;
import java.util.Date;


/**
 * Pride project information collections contains the information for the corresponding project in PRIDE including information about the title, accession, samples, etc. Please
 * read the specification of a PRIDE dataset here:
 *
 * @author: Yasset Perez-Riverol
 * @version $Id$
 */
@Document(collection = "project")

public class PrideProject implements ProjectProvider{

    /** Project Accession in PRIDE**/
    @Id
    String id;

    /** Title of the Project **/
    String title;

    /** PRIDE Project short description **/
    private String description;

    /** Sample Processing **/
    private String sampleProcessing;

    /** Data Processing Protocol **/
    private String dataProcessing;

    /** This is using an abstraction of the User, in this case MongoDB only will retrieve the information related with the userContact **/
    @DBRef
    private ContactProvider submitter;

    /** This returns a list of head of labs PIs ralted with the experiment **/
    private Collection<ContactProvider> headLab;

    /**
     * Return the PRIDE Accession, Accession of the dataset across all PRIDE PXDXXXXX or PRDXXXX. This id is created by the
     * PRIDE Pipelines.
     * @return PRIDE Project Accession
     */
    @Override
    public Comparable getId() {
        return id;
    }

    /**
     * In some cases is better to retrieve the Id of the dataset by the accession. This function retrieve the same value that
     * getId function.
     * @return Project Accession
     */
    @Override
    public String getAccession() {
        return id;
    }

    /**
     * @return  Project Title in PRIDE
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * @return Short Project Description
     */
    @Override
    public String getProjectDescription() {
        return description;
    }

    /**
     * @return Sample Processing
     */
    @Override
    public String getSampleProcessingProtocol() {
        return sampleProcessing;
    }

    /**
     * @return Data Processing
     */
    @Override
    public String getDataProcessingProtocol() {
        return dataProcessing;
    }

    /**
     * @return Submitter
     */
    @Override
    public ContactProvider getSubmitter() {
        return submitter;
    }

    @Override
    public Collection<ContactProvider> getHeadLab() {
        return headLab;
    }

    @Override
    public Collection<? extends String> getKeywords() {
        return null;
    }

    @Override
    public Collection<? extends ProjectTagProvider> getProjectTags() {
        return null;
    }

    @Override
    public Collection<? extends CvParamProvider> getExperimentTypes() {
        return null;
    }

    @Override
    public SubmissionType getSubmissionType() {
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
    public Collection<? extends CvParamProvider> getPtms() {
        return null;
    }

    @Override
    public Collection<? extends CvParamProvider> getSamples() {
        return null;
    }

    @Override
    public Collection<? extends CvParamProvider> getInstruments() {
        return null;
    }

    @Override
    public Collection<? extends CvParamProvider> getSoftware() {
        return null;
    }

    @Override
    public Collection<? extends CvParamProvider> getQuantificationMethods() {
        return null;
    }

    @Override
    public Collection<? extends ReferenceProvider> getReferences() {
        return null;
    }

    @Override
    public String getDoi() {
        return null;
    }

    @Override
    public Collection<? extends String> getOtherOmicsLink() {
        return null;
    }

    @Override
    public Collection<? extends String> getReanalysisIds() {
        return null;
    }

    @Override
    public int getNumAssays() {
        return 0;
    }

    @Override
    public boolean isPublicProject() {
        return false;
    }

    @Override
    public Collection<? extends AssayProvider> getAssays() {
        return null;
    }


    @Override
    public Collection<? extends ParamProvider> getParams() {
        return null;
    }

}
