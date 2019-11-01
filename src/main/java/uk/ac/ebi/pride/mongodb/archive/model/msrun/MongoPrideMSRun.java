package uk.ac.ebi.pride.mongodb.archive.model.msrun;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.msrun.MsRunProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.archive.dataprovider.msrun.idsettings.IdSetting;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 *
 * The {@link MongoPrideMSRun} implements inheritance from {@link MongoPrideMSRun} following this implementation:
 *
 * https://medium.com/@mladen.maravic/spring-data-mongodb-my-take-on-inheritance-support-102361c08e3d
 *
 *
 * @author ypriverol on 29/08/2018.
 */

@Data
@Builder
@Document(collection = PrideArchiveField.PRIDE_MSRUN_COLLECTION_NAME)
@TypeAlias(PrideArchiveField.MONGO_MSRUN_DOCUMENT_ALIAS)
public class MongoPrideMSRun implements MsRunProvider, PrideArchiveField{

    @Id
    @Indexed(name = ID)
    ObjectId id;

    /** The project accessions are related with the following File **/
    @Indexed(name = EXTERNAL_PROJECT_ACCESSIONS)
    Set<String> projectAccessions;

    /** The analysis accessions are releated with the following File **/
    @Indexed(name = EXTERNAL_ANALYSIS_ACCESSIONS)
    Set<String> analysisAccessions;

    /** Accession generated for each MSRun **/
    @Indexed(name = ACCESSION, unique = true)
    @Getter(AccessLevel.NONE)
    String accession;

    @Indexed(name = FILE_NAME)
    protected String fileName;

    @Indexed(name = FILE_SIZE_MB)
    protected long fileSizeBytes;

    @Field(PrideArchiveField.MS_RUN_FILE_PROPERTIES)
    Set<CvParam> fileProperties = new HashSet<>();

    @Field(PrideArchiveField.MS_RUN_INSTRUMENT_PROPERTIES)
    Set<CvParam> instrumentProperties = new HashSet<>();

    @Field(PrideArchiveField.MS_RUN_MS_DATA)
    Set<CvParam> msData = new HashSet<>();

    @Field(PrideArchiveField.MS_RUN_SCAN_SETTINGS)
    Set<CvParam> scanSettings = new HashSet<>();

    @Field(PrideArchiveField.ADDITIONAL_ATTRIBUTES)
    List<CvParam> additionalAttributes;

    @Field(PrideArchiveField.MS_RUN_ID_SETTINGS)
    Set<IdSetting> idSettings = new HashSet<>();

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
        fileName = prideFile.getFileName();
        additionalAttributes = prideFile.getAdditionalAttributes();
        fileSizeBytes = prideFile.getFileSizeBytes();
    }

    /**
     * Full constructor of the {@link MongoPrideMSRun}
     * @param id {@link ObjectId} for mongo
     * @param projectAccessions Project accessions
     * @param analysisAccessions Analysis Accessions
     * @param accession Accession of the MSRun
     * @param fileName file name
     * @param fileSizeBytes File size in bytes
     * @param fileProperties file properties
     * @param instrumentProperties instrument properties
     * @param msData MS Data
     * @param scanSettings Scan properties
     * @param additionalAttributes additional properties
     * @param idSettings idnetification Settings
     */
    public MongoPrideMSRun(ObjectId id, Set<String> projectAccessions, Set<String> analysisAccessions, String accession, String fileName, long fileSizeBytes, Set<CvParam> fileProperties, Set<CvParam> instrumentProperties, Set<CvParam> msData, Set<CvParam> scanSettings, List<CvParam> additionalAttributes, Set<IdSetting> idSettings) {
        this.id = id;
        this.projectAccessions = projectAccessions;
        this.analysisAccessions = analysisAccessions;
        this.accession = accession;
        this.fileName = fileName;
        this.fileSizeBytes = fileSizeBytes;
        this.fileProperties = fileProperties;
        this.instrumentProperties = instrumentProperties;
        this.msData = msData;
        this.scanSettings = scanSettings;
        this.additionalAttributes = additionalAttributes;
        this.idSettings = idSettings;
    }

    public MongoPrideMSRun() {
    }

    public String getAccession() {
        return accession;
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
                Objects.equals(scanSettings, that.scanSettings) &&
                Objects.equals(idSettings, that.idSettings);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), fileProperties, instrumentProperties, msData, scanSettings, idSettings);
    }

    public void addFileProperties(Set<CvParam> fileProperties) {
        this.fileProperties.addAll(fileProperties);
    }

    public void addInstrumentProperties(Set<CvParam> instrumentProperties) {
        this.instrumentProperties.addAll(instrumentProperties);
    }

    public void addMsData(Set<CvParam> msData) {
        this.msData.addAll(msData);
    }

    public void addScanSettings(Set<CvParam> scanSettings) {
        this.scanSettings.addAll(scanSettings);
    }

    public void addIdSettings(Set<IdSetting> idSettings) {
        this.idSettings.addAll(idSettings);
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

    public Set<IdSetting> getIdSettings() {
        return idSettings;
    }

    public void setFileProperties(Set<CvParam> fileProperties) {
        this.fileProperties = fileProperties;
    }

    public void setInstrumentProperties(Set<CvParam> instrumentProperties) {
        this.instrumentProperties = instrumentProperties;
    }

    public void setMsData(Set<CvParam> msData) {
        this.msData = msData;
    }

    public void setScanSettings(Set<CvParam> scanSettings) {
        this.scanSettings = scanSettings;
    }

    @Override
    public Collection<? extends String> getAdditionalAttributesStrings() {
        List<String> attributes = new ArrayList<>();
        if (additionalAttributes != null)
            attributes = additionalAttributes.stream().map(CvParamProvider::getName).collect(Collectors.toList());
        return attributes;
    }

    public void setIdSettings(Set<IdSetting> idSettings) {
        this.idSettings = idSettings;
    }
}
