package uk.ac.ebi.pride.mongodb.archive.model;

import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
import uk.ac.ebi.pride.archive.dataprovider.reference.ReferenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.user.DefaultContact;

import java.util.Arrays;
import java.util.Date;

/**
 * Pride Archive Field Enums this is used to Query the PRIDE Archive and other resources.
 *
 * @author ypriverol
 */
public enum PrideFieldEnum {

    ACCESSION(PrideArchiveField.ACCESSION, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME, PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME, PrideArchiveField.PRIDE_PEPTIDE_COLLECTION_NAME}, String.class),
    PROJECT_TILE(PrideArchiveField.PROJECT_TILE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    ADDITIONAL_ATTRIBUTES(PrideArchiveField.ADDITIONAL_ATTRIBUTES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME, PrideArchiveField.PRIDE_FILE_COLLECTION_NAME, PrideArchiveField.PRIDE_PEPTIDE_COLLECTION_NAME}, DefaultCvParam.class),
    PROJECT_DESCRIPTION(PrideArchiveField.PROJECT_DESCRIPTION, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    PROJECT_SAMPLE_PROTOCOL(PrideArchiveField.PROJECT_SAMPLE_PROTOCOL, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    PROJECT_DATA_PROTOCOL(PrideArchiveField.PROJECT_DATA_PROTOCOL, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    PROJECT_TAGS(PrideArchiveField.PROJECT_TAGS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    PROJECT_KEYWORDS(PrideArchiveField.PROJECT_KEYWORDS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    PROJECT_DOI(PrideArchiveField.PROJECT_DOI, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    PROJECT_OMICS_LINKS(PrideArchiveField.PROJECT_OMICS_LINKS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    PROJECT_SUBMISSION_TYPE(PrideArchiveField.PROJECT_SUBMISSION_TYPE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    PROJECT_SUBMISSION_DATE (PrideArchiveField.SUBMISSION_DATE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME},Date.class),
    PROJECT_PUBLICATION_DATE (PrideArchiveField.PUBLICATION_DATE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, Date.class),
    PROJECT_UPDATED_DATE (PrideArchiveField.UPDATED_DATE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, Date.class),
    PROJECT_SUBMITTER(PrideArchiveField.PROJECT_SUBMITTER, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, DefaultContact.class),
    PROJECT_PI_NAMES (PrideArchiveField.PROJECT_PI_NAMES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, DefaultCvParam.class),
    INSTRUMENTS(PrideArchiveField.INSTRUMENTS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, DefaultCvParam.class),
    SOFTWARES (PrideArchiveField.SOFTWARES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME},DefaultCvParam.class),
    QUANTIFICATION_METHODS(PrideArchiveField.QUANTIFICATION_METHODS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME},DefaultCvParam.class),
    COUNTRIES(PrideArchiveField.COUNTRIES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    SAMPLE_ATTRIBUTES_NAMES(PrideArchiveField.SAMPLE_ATTRIBUTES_NAMES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME},String.class),
    PROJECT_REFERENCES(PrideArchiveField.PROJECT_REFERENCES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME},ReferenceProvider.class),
    PROJECT_IDENTIFIED_PTM (PrideArchiveField.PROJECT_IDENTIFIED_PTM, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME},String.class),
    PUBLIC_PROJECT (PrideArchiveField.PUBLIC_PROJECT, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, String.class),
    EXPERIMENTAL_FACTORS(PrideArchiveField.EXPERIMENTAL_FACTORS, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}, DefaultCvParam.class),
    EXTERNAL_PROJECT_ACCESSIONS (PrideArchiveField.EXTERNAL_PROJECT_ACCESSIONS, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}, String.class),
    EXTERNAL_ANALYSIS_ACCESSIONS(PrideArchiveField.EXTERNAL_ANALYSIS_ACCESSIONS, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}, String.class),
    FILE_CATEGORY(PrideArchiveField.FILE_CATEGORY, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}, String.class),
    FILE_SOURCE_FOLDER(PrideArchiveField.FILE_SOURCE_TYPE, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}, String.class),
    FILE_MD5_CHECKSUM(PrideArchiveField.FILE_MD5_CHECKSUM, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}, String.class),
    FILE_PUBLIC_LOCATIONS (PrideArchiveField.FILE_PUBLIC_LOCATIONS, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}, String.class),
    FILE_SIZE_MB (PrideArchiveField.FILE_SIZE_MB,new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}, String.class),
    FILE_EXTENSION (PrideArchiveField.FILE_EXTENSION,new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME},String.class),
    FILE_NAME (PrideArchiveField.FILE_NAME,new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME}, String.class),
    FILE_IS_COMPRESS (PrideArchiveField.FILE_NAME, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME},Boolean.class);

    private String fieldName;
    private Class classType;
    private String[] collections;

    PrideFieldEnum(String fieldName, String[] collections, Class classType) {
        this.classType = classType;
        this.fieldName = fieldName;
        this.collections = collections;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String[] getCollections() {
        return collections;
    }

    public void setCollections(String[] collections) {
        this.collections = collections;
    }

    public Class getClassType() {
        return classType;
    }

    @Override
    public String toString() {
        return "PrideFieldEnum{" +
                "fieldName='" + getFieldName() + '\'' +
                ", classType=" + getClassType() +
                ", collections=" + Arrays.toString(getCollections()) +
                '}';
    }
}
