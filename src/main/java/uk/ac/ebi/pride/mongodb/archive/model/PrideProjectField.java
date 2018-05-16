package uk.ac.ebi.pride.mongodb.archive.model;

/**
 * @author ypriverol
 */
public interface PrideProjectField {

    String ID = "id";

    /** Project Accession **/
    String ACCESSION = "accession";

    /** Project Title **/
    String PROJECT_TILE = "project_title";

    /** Additional Attributes Accessions **/
    String ADDITIONAL_ATTRIBUTES = "additional_attributes";

    /** Project Description **/
    String PROJECT_DESCRIPTION = "project_description";

    /** Sample Protocol **/
    String PROJECT_SAMPLE_PROTOCOL = "project_sample_protocol";

    /** Data Processing Protocol **/
    String PROJECT_DATA_PROTOCOL = "project_data_protocol";

    /** Project Tags **/
    String PROJECT_TAGS = "project_tags";

    /** Keywords **/
    String PROJECT_KEYWORDS = "project_keywords";

    /** PROJECT DOI **/
    String PROJECT_DOI = "project_doi";

    /** PROJECT OMICS **/
    String PROJECT_OMICS_LINKS = "project_other_omics";

    /** Submission Type **/
    String PROJECT_SUBMISSION_TYPE = "project_submission_type";

    /** Submission Date **/
    String PROJECT_SUBMISSION_DATE = "submission_date";

    /** Publication Date **/
    String PROJECT_PUBLICATION_DATE = "publication_date";

    /** Update Date **/
    String PROJECT_UPDATED_DATE = "updated_date";

    /** Submitter FirstName **/
    String PROJECT_SUBMITTER = "submitters";

    /** List of Lab Head Names **/
    String PROJECT_PI_NAMES = "lab_heads";

    /** List of instruments Ids*/
    String INSTRUMENTS = "instruments";

    String SOFTWARES = "softwares";

    String QUANTIFICATION_METHODS = "quantification_methods";

    /** This field store all the countries associated with the experiment **/
    String COUNTRIES = "countries";


    /** Sample metadata names **/
    String SAMPLE_ATTRIBUTES_NAMES = "sample_attributes";

    /** References related with the project **/
    String PROJECT_REFERENCES = "project_references";

    /** Identified PTMs in the Project**/
    String PROJECT_IDENTIFIED_PTM_STRING = "project_identified_ptms";

    String PRIDE_PROJECTS_COLLECTION_NAME = "pride_projects";

    String PUBLIC_PROJECT = "public_project";

    /** Experimental Factors **/
    String EXPERIMENTAL_FACTORS = "experimental_factors";

}
