package uk.ac.ebi.pride.mongodb.archive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.assay.identification.IdentificationAssayProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.project.ProjectProvider;
import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.archive.dataprovider.utils.Tuple;

import java.util.Collection;
import java.util.Date;
import java.util.Map;


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

    /** List of keywords added by the user **/
    private Collection<String> keywords;

    /** This are tags provided by the curator of PRIDE **/
    private Collection<String> projectTags;

    /* This are CVparams to describe the type of the experiment */
    private Collection<? extends CvParamProvider> experimentTypes;

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
    private Collection<CvParamProvider> ptmList;

    /**
     * Samples description is a generic information about all the samples in the experiment. Import: These details do not have detail information:
     * Not specific to any Sample.
     */
    private Collection<CvParamProvider> samplesDescription;

    /**
     * General description about the instruments used in the experiment.
     */
    private Collection<CvParamProvider> instruments;

    /** General software information in CVParams terms **/
    private Collection<CvParamProvider> softwareList;

    /** References related with the dataset in manuscript and papers **/
    private Collection<ReferenceProvider> references;

    /** Quantification methods used in the dataset / project **/
    private Collection<CvParamProvider> quantificationMethods;

    private Collection<CvParamProvider> attributes;

    /** DOI Assigned by proteomeXchange **/
    private String doi;

    /** Other omics datasets
     *  Todo: This needs to be refined because a
     * **/
    private Collection<String> otherOmicsLinks;

    /**
     * Accessions to other datasets that reanalyzed, The structure of the reanalysis is the following: <Accession, DatabaseName, URL>
     */
    private Map<Tuple, String> reanalysisAccessions;

    /** Is a public project or private **/
    private boolean publicProject;

    /** Is the number of result files **/
    private Collection<IdentificationAssayProvider> identificationAssays;


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
        return keywords;
    }

    @Override
    public Collection<String> getProjectTags() {
        return projectTags;
    }

    @Override
    public Collection<? extends CvParamProvider> getExperimentTypes() {
        return experimentTypes;
    }

    @Override
    public String getSubmissionType() {
        return submissionType;
    }

    @Override
    public Date getSubmissionDate() {
        return submissionDate;
    }

    @Override
    public Date getPublicationDate() {
        return publicationDate;
    }

    @Override
    public Date getUpdateDate() {
        return updatedDate;
    }

    @Override
    public Collection<CvParamProvider> getPtms() {
        return ptmList;
    }

    @Override
    public Collection<CvParamProvider> getSamplesDescription() {
        return samplesDescription;
    }

    @Override
    public Collection<? extends CvParamProvider> getInstruments() {
        return instruments;
    }

    @Override
    public Collection<? extends CvParamProvider> getSoftwares() {
        return softwareList;
    }

    @Override
    public Collection<? extends CvParamProvider> getQuantificationMethods() {
        return quantificationMethods;
    }

    @Override
    public Collection<ReferenceProvider> getReferences() {
        return references;
    }

    @Override
    public String getDoi() {
        return doi;
    }

    @Override
    public Collection<String> getOtherOmicsLink() {
        return otherOmicsLinks;
    }

    @Override
    public Map<? extends Tuple, ? extends String> getReanalysis() {
        return reanalysisAccessions;
    }

    @Override
    public boolean isPublicProject() {
        return publicProject;
    }

    @Override
    public Collection<? extends ParamProvider> getParams() {
        return attributes;
    }

    @Override
    public int numberOfIdentificationAssays() {
        return identificationAssays.size();
    }

    @Override
    public Collection<? extends IdentificationAssayProvider> getIdentificationAssays() {
        return identificationAssays;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSampleProcessing(String sampleProcessing) {
        this.sampleProcessing = sampleProcessing;
    }

    public void setDataProcessing(String dataProcessing) {
        this.dataProcessing = dataProcessing;
    }

    public void setSubmitter(ContactProvider submitter) {
        this.submitter = submitter;
    }

    public void setHeadLab(Collection<ContactProvider> headLab) {
        this.headLab = headLab;
    }

    public void setKeywords(Collection<String> keywords) {
        this.keywords = keywords;
    }

    public void setProjectTags(Collection<String> projectTags) {
        this.projectTags = projectTags;
    }

    public void setExperimentTypes(Collection<? extends CvParamProvider> experimentTypes) {
        this.experimentTypes = experimentTypes;
    }

    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setPtmList(Collection<CvParamProvider> ptmList) {
        this.ptmList = ptmList;
    }

    public void setSamplesDescription(Collection<CvParamProvider> samplesDescription) {
        this.samplesDescription = samplesDescription;
    }

    public void setInstruments(Collection<CvParamProvider> instruments) {
        this.instruments = instruments;
    }

    public void setSoftwareList(Collection<CvParamProvider> softwareList) {
        this.softwareList = softwareList;
    }

    public void setReferences(Collection<ReferenceProvider> references) {
        this.references = references;
    }

    public void setQuantificationMethods(Collection<CvParamProvider> quantificationMethods) {
        this.quantificationMethods = quantificationMethods;
    }

    public void setAttributes(Collection<CvParamProvider> attributes) {
        this.attributes = attributes;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setOtherOmicsLinks(Collection<String> otherOmicsLinks) {
        this.otherOmicsLinks = otherOmicsLinks;
    }

    public void setReanalysisAccessions(Map<Tuple, String> reanalysisAccessions) {
        this.reanalysisAccessions = reanalysisAccessions;
    }

    public void setPublicProject(boolean publicProject) {
        this.publicProject = publicProject;
    }

    public void setIdResultList(Collection<IdentificationAssayProvider> idResultList) {
        this.idResultList = idResultList;
    }
}
