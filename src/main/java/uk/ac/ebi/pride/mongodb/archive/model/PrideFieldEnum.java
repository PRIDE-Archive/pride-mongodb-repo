package uk.ac.ebi.pride.mongodb.archive.model;

/**
 * Pride Archive Field Enums this is used to Query the PRIDE Archive and other resources.
 *
 * @author ypriverol
 */
public enum PrideFieldEnum {

    ACCESSION(PrideArchiveField.ACCESSION, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME, PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME, PrideArchiveField.PRIDE_PSM_COLLECTION_NAME}),
    PROJECT_TILE(PrideArchiveField.PROJECT_TILE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    ADDITIONAL_ATTRIBUTES(PrideArchiveField.ADDITIONAL_ATTRIBUTES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME, PrideArchiveField.PRIDE_FILE_COLLECTION_NAME, PrideArchiveField.PRIDE_PSM_COLLECTION_NAME}),
    PROJECT_DESCRIPTION(PrideArchiveField.PROJECT_DESCRIPTION, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_SAMPLE_PROTOCOL(PrideArchiveField.PROJECT_SAMPLE_PROTOCOL, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_DATA_PROTOCOL(PrideArchiveField.PROJECT_DATA_PROTOCOL, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_TAGS(PrideArchiveField.PROJECT_TAGS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_KEYWORDS(PrideArchiveField.PROJECT_KEYWORDS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_DOI(PrideArchiveField.PROJECT_DOI, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_OMICS_LINKS(PrideArchiveField.PROJECT_OMICS_LINKS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_SUBMISSION_TYPE(PrideArchiveField.PROJECT_SUBMISSION_TYPE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_SUBMISSION_DATE (PrideArchiveField.SUBMISSION_DATE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_PUBLICATION_DATE (PrideArchiveField.PUBLICATION_DATE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_UPDATED_DATE (PrideArchiveField.UPDATED_DATE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_SUBMITTER(PrideArchiveField.PROJECT_SUBMITTER, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_PI_NAMES (PrideArchiveField.PROJECT_PI_NAMES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    INSTRUMENTS(PrideArchiveField.INSTRUMENTS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    SOFTWARES (PrideArchiveField.SOFTWARES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    QUANTIFICATION_METHODS(PrideArchiveField.QUANTIFICATION_METHODS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    COUNTRIES(PrideArchiveField.COUNTRIES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    SAMPLE_ATTRIBUTES_NAMES(PrideArchiveField.SAMPLE_ATTRIBUTES_NAMES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_REFERENCES(PrideArchiveField.PROJECT_REFERENCES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PROJECT_IDENTIFIED_PTM (PrideArchiveField.PROJECT_IDENTIFIED_PTM, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    PUBLIC_PROJECT (PrideArchiveField.PUBLIC_PROJECT, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    EXPERIMENTAL_FACTORS(PrideArchiveField.EXPERIMENTAL_FACTORS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    EXTERNAL_PROJECT_ACCESSIONS (PrideArchiveField.EXTERNAL_PROJECT_ACCESSIONS, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}),
    EXTERNAL_ANALYSIS_ACCESSIONS(PrideArchiveField.EXTERNAL_ANALYSIS_ACCESSIONS, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}),
    FILE_CATEGORY(PrideArchiveField.FILE_CATEGORY, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}),
    FILE_SOURCE_FOLDER(PrideArchiveField.FILE_SOURCE_FOLDER, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}),
    FILE_MD5_CHECKSUM(PrideArchiveField.FILE_MD5_CHECKSUM, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}),
    FILE_PUBLIC_LOCATIONS (PrideArchiveField.FILE_PUBLIC_LOCATIONS, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}),
    FILE_SIZE_MB (PrideArchiveField.FILE_SIZE_MB,new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}),
    FILE_EXTENSION (PrideArchiveField.FILE_EXTENSION,new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}),
    FILE_NAME (PrideArchiveField.FILE_NAME,new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}),
    FILE_IS_COMPRESS (PrideArchiveField.FILE_NAME, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME});

    private String fieldName;
    private String[] collections;

    PrideFieldEnum(java.lang.String fieldName, java.lang.String[] collections) {
        this.fieldName = fieldName;
        this.collections = collections;
    }

    public java.lang.String getFieldName() {
        return fieldName;
    }

    public void setFieldName(java.lang.String fieldName) {
        this.fieldName = fieldName;
    }

    public java.lang.String[] getCollections() {
        return collections;
    }

    public void setCollections(java.lang.String[] collections) {
        this.collections = collections;
    }
}
