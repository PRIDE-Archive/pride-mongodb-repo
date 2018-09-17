package uk.ac.ebi.pride.mongodb.archive.model.files;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.msrun.MsRunProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;

import java.util.List;
import java.util.Objects;

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
public class MongoPrideMSRun extends MongoPrideFile implements MsRunProvider{

    @Field(PrideArchiveField.MS_RUN_FILE_PROPERTIES)
    List<MongoCvParam> fileProperties;

    @Field(PrideArchiveField.MS_RUN_INSTRUMENT_PROPERTIES)
    List<MongoCvParam> instrumentProperties;

    @Field(PrideArchiveField.MS_RUN_MS_DATA)
    List<MongoCvParam> msData;

    @Field(PrideArchiveField.MS_RUN_SCAN_SETTINGS)
    List<MongoCvParam> scanSettings;

    /**
     * A {@link MongoPrideFile} that contains the general information of a File but without the
     * MSRun information.
     *
     * @param prideFile {@link MongoPrideFile}
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MongoPrideMSRun that = (MongoPrideMSRun) o;
        return Objects.equals(fileProperties, that.fileProperties) &&
                Objects.equals(instrumentProperties, that.instrumentProperties) &&
                Objects.equals(msData, that.msData) &&
                Objects.equals(scanSettings, that.scanSettings);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), fileProperties, instrumentProperties, msData, scanSettings);
    }
}
