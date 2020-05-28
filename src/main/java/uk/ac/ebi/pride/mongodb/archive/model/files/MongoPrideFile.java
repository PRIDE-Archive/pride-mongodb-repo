package uk.ac.ebi.pride.mongodb.archive.model.files;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Tolerate;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.file.FileProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
@TypeAlias(PrideArchiveField.MONGO_FILE_DOCUMENT_ALIAS)
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
    String accession;

    @Indexed(name = FILE_CATEGORY)
    @Getter(AccessLevel.NONE)
    CvParam fileCategory;

    @Indexed(name = FILE_SOURCE_TYPE)
    String fileSourceType;

    @Indexed(name = FILE_SOURCE_FOLDER)
    String fileSourceFolder;

    /** Checksum for the specific File provided by the submission tool **/
    @Indexed(name = SUBMITTER_FILE_CHECKSUM)
    String submitterFileChecksum;

    @Indexed(name = FILE_CHECKSUM)
    String checksum;

    /** Public File Locations in CVTerms**/
    @Indexed(name = FILE_PUBLIC_LOCATIONS)
    Set<CvParam> publicFileLocations;

    @Indexed(name = FILE_SIZE_MB)
    protected long fileSizeBytes;

    @Indexed(name =  FILE_EXTENSION)
    protected  String fileExtension;

    @Indexed(name = FILE_NAME)
    protected String fileName;

    @Indexed(name = FILE_IS_COMPRESS)
    protected boolean compress;

    @Indexed(name = SUBMISSION_DATE)
    protected Date submissionDate;

    @Indexed(name = PUBLICATION_DATE)
    protected Date publicationDate;

    @Indexed(name = UPDATED_DATE)
    protected Date updatedDate;

    @Indexed(name = ADDITIONAL_ATTRIBUTES)
    Set<CvParam> additionalAttributes;

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
    public Set<? extends CvParamProvider> getPublicFileLocation() {
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
    public String getChecksum() {
        return checksum;
    }

    @Override
    public Comparable getId() {
        return id;
    }

    @Tolerate
    public MongoPrideFile() {
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
                "id=" + id +
                ", projectAccessions=" + projectAccessions +
                ", analysisAccessions=" + analysisAccessions +
                ", accession='" + accession + '\'' +
                ", fileCategory=" + fileCategory +
                ", fileSourceType='" + fileSourceType + '\'' +
                ", fileSourceFolder='" + fileSourceFolder + '\'' +
                ", submitterFileChecksum='" + submitterFileChecksum + '\'' +
                ", checksum='" + checksum + '\'' +
                ", publicFileLocations=" + publicFileLocations +
                ", fileSizeBytes=" + fileSizeBytes +
                ", fileExtension='" + fileExtension + '\'' +
                ", fileName='" + fileName + '\'' +
                ", compress=" + compress +
                ", submissionDate=" + submissionDate +
                ", publicationDate=" + publicationDate +
                ", updatedDate=" + updatedDate +
                ", additionalAttributes=" + additionalAttributes +
                ", accessionSubmissionFile=" + accessionSubmissionFile +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoPrideFile that = (MongoPrideFile) o;
        return fileSizeBytes == that.fileSizeBytes &&
                compress == that.compress &&
                Objects.equals(projectAccessions, that.projectAccessions) &&
                Objects.equals(analysisAccessions, that.analysisAccessions) &&
                Objects.equals(accession, that.accession) &&
                Objects.equals(fileCategory, that.fileCategory) &&
                Objects.equals(fileSourceType, that.fileSourceType) &&
                Objects.equals(fileSourceFolder, that.fileSourceFolder) &&
                Objects.equals(submitterFileChecksum, that.submitterFileChecksum) &&
                Objects.equals(checksum, that.checksum) &&
                Objects.equals(publicFileLocations, that.publicFileLocations) &&
                Objects.equals(fileExtension, that.fileExtension) &&
                Objects.equals(fileName, that.fileName) &&
                equalsDatePartOnly(publicationDate, that.publicationDate) &&
                equalsDatePartOnly(submissionDate, that.submissionDate) &&
                equalsDatePartOnly(updatedDate, that.updatedDate) &&
                Objects.equals(additionalAttributes, that.additionalAttributes) &&
                Objects.equals(accessionSubmissionFile, that.accessionSubmissionFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectAccessions, analysisAccessions, accession, fileCategory, fileSourceType, fileSourceFolder, submitterFileChecksum, checksum, publicFileLocations, fileSizeBytes, fileExtension, fileName, compress, submissionDate, publicationDate, updatedDate, additionalAttributes, accessionSubmissionFile);
    }

    private static final DateFormat DATE_FORMAT_DATE_PART = new SimpleDateFormat("yyyy-mm-dd");

//    public static boolean equalsDate(Date a, Date b) {
//        return  ((a == b) || (a != null && b != null && a.getTime() == b.getTime()));
//    }

    public static boolean equalsDatePartOnly(Date a, Date b) {
        String aStr = DATE_FORMAT_DATE_PART.format(a);
        String bStr = DATE_FORMAT_DATE_PART.format(b);
        return  ((a == b) || (a != null && b != null && aStr.equals(bStr)));
    }
}
