package uk.ac.ebi.pride.mongodb.archive.model;

/**
 * Pride Archive Field Enums this is used to Query the PRIDE Archive and other resources.
 *
 * @author ypriverol
 */
public enum PrideFieldEnum {

    ACCESSION(PrideArchiveField.ACCESSION, new String[]{PrideArchiveField.PRIDE_FILE_COLLECTION_NAME, PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME, PrideArchiveField.PRIDE_PSM_COLLECTION_NAME}),
    PROJECT_TILE(PrideArchiveField.PROJECT_TILE, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME}),
    ADDITIONAL_ATTRIBUTES(PrideArchiveField.ADDITIONAL_ATTRIBUTES, new String[]{PrideArchiveField.PRIDE_PROJECTS_COLLECTION_NAME, PrideArchiveField.PRIDE_FILE_COLLECTION_NAME, PrideArchiveField.PRIDE_PSM_COLLECTION_NAME});

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
