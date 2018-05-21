package uk.ac.ebi.pride.mongodb.archive.model;

/**
 * @author ypriverol
 */
public interface PrideArchiveField {

    String ID = "id";

    /** Project Accession **/
    String ACCESSION = "accession";

    /** Project Title **/
    String PROJECT_TILE = "title";

    /** Additional Attributes Accessions **/
    String ADDITIONAL_ATTRIBUTES = "additionalAttributes";

    /** Project Description **/
    String PROJECT_DESCRIPTION = "description";

    /** Sample Protocol **/
    String PROJECT_SAMPLE_PROTOCOL = "sampleProtocol";

    /** Data Processing Protocol **/
    String PROJECT_DATA_PROTOCOL = "dataProtocol";

    /** Project Tags **/
    String PROJECT_TAGS = "tags";

    /** Keywords **/
    String PROJECT_KEYWORDS = "keywords";

    /** PROJECT DOI **/
    String PROJECT_DOI = "project_doi";

    /** PROJECT OMICS **/
    String PROJECT_OMICS_LINKS = "project_other_omics";

    /** Submission Type **/
    String PROJECT_SUBMISSION_TYPE = "submissionType";

    /** Submission Date **/
    String SUBMISSION_DATE = "submissionDate";

    /** Publication Date **/
    String PUBLICATION_DATE = "publicationDate";

    /** Update Date **/
    String UPDATED_DATE = "updatedDate";

    /** Submitter FirstName **/
    String PROJECT_SUBMITTER = "submitters";

    /** List of Lab Head Names **/
    String PROJECT_PI_NAMES = "lab_heads";

    /** List of instruments Ids*/
    String INSTRUMENTS = "instruments";

    String SOFTWARES = "softwares";

    String QUANTIFICATION_METHODS = "quantificationMethods";

    /** This field store all the countries associated with the experiment **/
    String COUNTRIES = "countries";

    /** Sample metadata names **/
    String SAMPLE_ATTRIBUTES_NAMES = "sample_attributes";

    /** References related with the project **/
    String PROJECT_REFERENCES = "project_references";

    /** Identified PTMs in the Project**/
    String PROJECT_IDENTIFIED_PTM = "ptmList";

    /** Collections Name **/
    String PRIDE_PROJECTS_COLLECTION_NAME = "pride_projects";
    String PRIDE_FILE_COLLECTION_NAME = "pride_files";

    String PUBLIC_PROJECT = "public_project";

    /** Experimental Factors **/
    String EXPERIMENTAL_FACTORS = "experimentalFactors";

    /** External Project accessions that use this following file **/
    String EXTERNAL_PROJECT_ACCESSIONS = "externalProjectAccessions";

    /** External Project Analysis Accessions that use the file **/
    String EXTERNAL_ANALYSIS_ACCESSIONS = "pride_analysis_accessions";

    //** File Fields **/
    String FILE_CATEGORY = "file_category";
    String FILE_SOURCE_FOLDER = "file_source_folder";
    String FILE_MD5_CHECKSUM = "file_md5_checksum";
    String FILE_PUBLIC_LOCATIONS = "file_public_locations";
    String FILE_SIZE_MB = "file_size_mb";
    String FILE_EXTENSION = "file_extension";
    String FILE_NAME = "file_name";
    String FILE_IS_COMPRESS = "file_is_compress";

    /** PSM Collections **/
    String PRIDE_PSM_COLLECTION_NAME = "pride_psms";
    String SPECTRUM_ACCESSION = "spectrum_accession";
    String PEPTIDE_SEQUENCE = "peptide_sequence";
    String REPORTED_PROTEIN_ACCESISION = "reported_protein_accession";

    /** PRIDE Analysis Collection **/
    String PRIDE_ANALYSIS_COLLECTION = "pride_analysis_collection";
}
