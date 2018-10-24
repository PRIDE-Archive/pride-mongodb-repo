package uk.ac.ebi.pride.mongodb.archive.model.files;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.msrun.MsRunProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;

import java.util.*;

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
@TypeAlias(PrideArchiveField.MONGO_MSRUN_ALIAS)
public class MongoPrideMSRun extends MongoPrideFile implements MsRunProvider{

    @Field(PrideArchiveField.MS_RUN_FILE_PROPERTIES)
    Set<MongoCvParam> fileProperties = new HashSet<>();

    @Field(PrideArchiveField.MS_RUN_INSTRUMENT_PROPERTIES)
    Set<MongoCvParam> instrumentProperties = new HashSet<>();

    @Field(PrideArchiveField.MS_RUN_MS_DATA)
    Set<MongoCvParam> msData = new HashSet<>();

    @Field(PrideArchiveField.MS_RUN_SCAN_SETTINGS)
    Set<MongoCvParam> scanSettings = new HashSet<>();

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

    public void addFileProperties(List<MongoCvParam> fileProperties) {
        this.fileProperties.addAll(fileProperties);
    }

    public void addInstrumentProperties(List<MongoCvParam> instrumentProperties) {
        this.instrumentProperties.addAll(instrumentProperties);
    }

    public void addMsData(List<MongoCvParam> msData) {
        this.msData.addAll(msData);
    }

    public void addScanSettings(List<MongoCvParam> scanSettings) {
        this.scanSettings.addAll(scanSettings);
    }


    @Override
    public Collection<? extends CvParamProvider> getFileProperties() {
        return fileProperties;
    }

    @Override
    public Collection<? extends CvParamProvider> getInstrumentProperties() {
        return instrumentProperties;
    }

    @Override
    public Collection<? extends CvParamProvider> getMsData() {
        return msData;
    }

    @Override
    public Collection<? extends CvParamProvider> getScanSettings() {
        return scanSettings;
    }

    public void setFileProperties(Set<MongoCvParam> fileProperties) {
        this.fileProperties = fileProperties;
    }

    public void setInstrumentProperties(Set<MongoCvParam> instrumentProperties) {
        this.instrumentProperties = instrumentProperties;
    }

    public void setMsData(Set<MongoCvParam> msData) {
        this.msData = msData;
    }

    public void setScanSettings(Set<MongoCvParam> scanSettings) {
        this.scanSettings = scanSettings;
    }
}
