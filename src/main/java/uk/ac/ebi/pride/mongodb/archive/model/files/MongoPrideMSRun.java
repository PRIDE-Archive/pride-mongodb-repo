package uk.ac.ebi.pride.mongodb.archive.model.files;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;
import uk.ac.ebi.pride.utilities.util.Tuple;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 *
 * The {@link MongoPrideMSRun} implements inheritance from {@link MongoPrideFile} following this implementation:
 *
 * https://medium.com/@mladen.maravic/spring-data-mongodb-my-take-on-inheritance-support-102361c08e3d
 *
 *
 * @author ypriverol on 29/08/2018.
 */

@Document(collection = PrideArchiveField.PRIDE_FILE_COLLECTION_NAME)
@Data
@TypeAlias(PrideArchiveField.MONGO_MSRUN_ALIAS)
public class MongoPrideMSRun extends MongoPrideFile{

    @Field(PrideArchiveField.MS_RUN_FILE_PROPERTIES)
    List<MongoCvParam> fileProperties;

    @Field(PrideArchiveField.MS_RUN_INSTRUMENT_PROPERTIES)
    List<MongoCvParam> instrumentProperties;

    @Field(PrideArchiveField.MS_RUN_MS_DATA)
    List<MongoCvParam> msData;

    @Field(PrideArchiveField.MS_RUN_SCAN_SETTINGS)
    List<MongoCvParam> scanSettings;

//    /**
//     * Default constructor that map the {@link MongoPrideFile} Constructor
//     * @param id ID of the file
//     * @param projectAccessions Project Accessions.
//     * @param analysisAccessions Analysis Accessions
//     * @param accession Accession of the File
//     * @param fileCategory File Category {@link uk.ac.ebi.pride.utilities.term.CvTermReference}
//     * @param fileSourceType File source type
//     * @param fileSourceFolder File source Folder
//     * @param md5Checksum CheckSum
//     * @param publicFileLocations public locations
//     * @param fileSizeBytes file Size
//     * @param fileExtension file Extensions
//     * @param fileName File Name
//     * @param compress IS compress
//     * @param submissionDate Submission Date
//     * @param publicationDate Publication Date
//     * @param updatedDate Updated Date
//     * @param additionalAttributes Additional Attributes
//     * @param accessionSubmissionFile Accession Submission File.
//     */
//    public MongoPrideMSRun(ObjectId id, Set<String> projectAccessions,
//                           Set<String> analysisAccessions, String accession,
//                           MongoCvParam fileCategory, String fileSourceType,
//                           String fileSourceFolder, String md5Checksum, List<MongoCvParam> publicFileLocations,
//                           long fileSizeBytes, String fileExtension, String fileName, boolean compress,
//                           Date submissionDate, Date publicationDate, Date updatedDate, List<MongoCvParam> additionalAttributes,
//                           Tuple<String, String> accessionSubmissionFile) {
//        super(id, projectAccessions, analysisAccessions, accession, fileCategory, fileSourceType, fileSourceFolder, md5Checksum, publicFileLocations, fileSizeBytes, fileExtension, fileName, compress, submissionDate, publicationDate, updatedDate, additionalAttributes, accessionSubmissionFile);
//    }
//

    public MongoPrideMSRun(MongoPrideFile prideFile) {
        super();
        id = new ObjectId(prideFile.getId().toString());
        projectAccessions = prideFile.getProjectAccessions();
        analysisAccessions = prideFile.getAnalysisAccessions();
        accession = prideFile.getAccession();
        fileCategory = (MongoCvParam) prideFile.getFileCategory();
        fileSourceType = prideFile.getFileSourceType();
        fileSourceFolder = prideFile.getFileSourceFolder();
        md5Checksum = prideFile.getMd5Checksum();
        publicFileLocations = prideFile.getPublicFileLocations();
        fileSizeBytes = prideFile.getFileSizeBytes();
        fileExtension = prideFile.getFileExtension();
        fileName = prideFile.getFileName();
        compress = prideFile.isCompress();
        submissionDate = prideFile.getSubmissionDate();
        publicationDate = prideFile.getPublicationDate();
        updatedDate =     prideFile.getUpdatedDate();
        additionalAttributes = prideFile.getAdditionalAttributes();
        accessionSubmissionFile = prideFile.getAccessionSubmissionFile();
    }

    public MongoPrideMSRun() {
        super();
    }
}
