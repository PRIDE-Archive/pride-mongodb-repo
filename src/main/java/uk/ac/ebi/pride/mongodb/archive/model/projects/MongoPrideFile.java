package uk.ac.ebi.pride.mongodb.archive.model.projects;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.file.FileProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;
import uk.ac.ebi.pride.utilities.util.Tuple;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The PRIDE Archive Project File provides information around all the files provided into one PRIDE Project.
 *
 * The combination between FileName and Project Accessions should be unique.
 *
 * @author ypriverol
 */
@Data
@Builder
@Document(collection = PrideArchiveField.PRIDE_FILE_COLLECTION_NAME)
public class MongoPrideFile implements PrideArchiveField, FileProvider {

    @Id
    @Indexed(name = ID)
    ObjectId id;

    /** The project accessions are related with the following File **/
    @Indexed(name = EXTERNAL_PROJECT_ACCESSIONS)
    Set<String> projectAccessions;

    /** The analysis accessions are releated with the following File **/
    @Indexed(name = EXTERNAL_ANALYSIS_ACCESSIONS)
    Set<String> analysisAccessions;

    /** Accession generated for each File **/
    @Indexed(name = ACCESSION, unique = true)
    @Getter(AccessLevel.NONE)
    String accession;

    @Indexed(name = FILE_CATEGORY)
    @Getter(AccessLevel.NONE)
    MongoCvParam fileCategory;

    @Indexed(name = FILE_SOURCE_TYPE)
    String fileSourceType;

    @Indexed(name = FILE_SOURCE_FOLDER)
    String fileSourceFolder;

    /** Checksum for the specific File provided by the submission tool **/
    @Indexed(name = FILE_MD5_CHECKSUM)
    String md5Checksum;

    /** Public File Locations in CVTerms**/
    @Indexed(name = FILE_PUBLIC_LOCATIONS)
    List<MongoCvParam> publicFileLocations;

    @Indexed(name = FILE_SIZE_MB)
    private long fileSizeBytes;

    @Indexed(name =  FILE_EXTENSION)
    String fileExtension;

    @Indexed(name = FILE_NAME)
    private String fileName;

    @Indexed(name = FILE_IS_COMPRESS)
    private boolean compress;

    @Indexed(name = SUBMISSION_DATE)
    private Date submissionDate;

    @Indexed(name = PUBLICATION_DATE)
    private Date publicationDate;

    @Indexed(name = UPDATED_DATE)
    private Date updatedDate;

    @Indexed(name = ADDITIONAL_ATTRIBUTES)
    List<MongoCvParam> additionalAttributes;

    @Indexed(name = ACCESSION_SUBMISSION_FILE)
    Tuple<String, String> accessionSubmissionFile;

    @Override
    public String getAccession() {
        return accession;
    }

    /** Category in CvParam **/
    @Override
    public CvParamProvider getFileCategory() {
        return fileCategory;
    }

    @Override
    public String getFolderSource() {
        return fileSourceFolder;
    }

    @Override
    public String getFileSourceType(){
        return fileSourceType;
    }

    @Override
    public List<? extends CvParamProvider> getPublicFileLocation() {
        return publicFileLocations;
    }

    @Override
    public long getFileSizeInBytes() {
        return fileSizeBytes;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getFileExtension() {
        return fileExtension;
    }

    @Override
    public String getMd5Checksum() {
        return md5Checksum;
    }

    @Override
    public Comparable getId() {
        return id;
    }

    @Override
    public Collection<? extends String> getAdditionalAttributesStrings() {
        List<String> attributes = new ArrayList<>();
        if(additionalAttributes != null)
            attributes = additionalAttributes.stream().map(CvParamProvider::getName).collect(Collectors.toList());
        return attributes;
    }

    @Override
    public String toString() {
        return "MongoPrideFile{" +
                "projectAccessions=" + projectAccessions +
                ", analysisAccessions=" + analysisAccessions +
                ", accession='" + accession + '\'' +
                ", fileCategory=" + fileCategory +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
