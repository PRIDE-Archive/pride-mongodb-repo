package uk.ac.ebi.pride.mongodb.archive.model.assay;

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
import uk.ac.ebi.pride.archive.dataprovider.assay.AssayProvider;
import uk.ac.ebi.pride.archive.dataprovider.assay.AssayType;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.utilities.term.CvTermReference;

import java.util.*;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author ypriverol on 01/11/2018.
 */

@Data
@Builder
@Document(collection = PrideArchiveField.PRIDE_ASSAY_COLLECTION_NAME)
@TypeAlias("MongoPrideAssay")
public class MongoPrideAssay implements PrideArchiveField, AssayProvider {

    @Id
    @Indexed(name = ID)
    ObjectId id;

    /** The project accessions are related with the following File **/
    @Indexed(name = EXTERNAL_PROJECT_ACCESSIONS)
    Set<String> projectAccessions;

    /** The analysis accessions are related with the following File **/
    @Indexed(name = EXTERNAL_ANALYSIS_ACCESSIONS)
    Set<String> analysisAccessions;

    /** The analysis accessions are related with the following File **/
    @Indexed(name = ASSAY_ACCESSION, unique = true)
    String accession;

    @Indexed(name = ASSAY_FILE_NAME)
    String fileName;

    /** Accession generated for each File **/
    @Field(value = ASSAY_TITLE)
    @Getter(AccessLevel.NONE)
    String title;

    /** Accession generated for each File **/
    //@Indexed(name = ASSAY)
    @Getter(AccessLevel.NONE)
    AssayType assayType;

    @Field(value = ASSAY_DESCRIPTION)
    String assayDescription;

    @Field(value = ASSAY_DATA_ANALYSIS_SOFTWARE)
    Set<CvParam> dataAnalysisSoftwares;

    @Field(value = ASSAY_DATA_ANALYSIS_DATABASE)
    CvParam dataAnalysisDatabase;

    @Field(value = ASSAY_DATA_ANALYSIS_RESULTS)
    Set<CvParam> summaryResults;

    @Field(value = ASSAY_DATA_ANALYSIS_PROTOCOL)
    Set<CvParam> dataAnalysisProperties;

    @Field(value = ASSAY_DATA_ANALYSIS_PTMS)
    List<Tuple<CvParam, Integer>> ptmsResults;

    @Field(value = ASSAY_FILES)
    List<MongoAssayFile> assayFiles;

    @Field(PrideArchiveField.QUALITY_ESTIMATION_METHOD)
    private Set<CvParam> qualityEstimationMethods;

    @Indexed(name = PrideArchiveField.IS_VALIDATED)
    private Boolean isValid;

    @Override
    public Collection<CvParam> getAdditionalProperties() {
        return dataAnalysisProperties;
    }

    @Override
    public AssayType getAssayType() {
        return assayType;
    }

    /**
     * This method allows to retrieve the number of identified peptides in the Assay.
     * @return Number of Identified Peptides
     */
    public Optional<Integer> getNumberIdentifiedPeptides(){
        return getSummaryResultProperty(CvTermReference.PRIDE_NUMBER_ID_PEPTIDES.getAccession());
    }

    /**
     * This method allows to retrieve the number of identified proteins in the Assay
     * @return Number of Identified Proteins
     */
    public Optional<Integer> getNumberIdentifiedProteins(){
        return getSummaryResultProperty(CvTermReference.PRIDE_NUMBER_ID_PROTEINS.getAccession());
    }

    /**
     * This method allows to retrieve the number of identified modified peptides
     * @return Number of Modified peptides
     */
    public Optional<Integer> getNumberModifiedPeptides(){
        return getSummaryResultProperty(CvTermReference.PRIDE_NUMBER_MODIFIED_PEPTIDES.getAccession());
    }

    /**
     * This method allows to retrieve the number of PSMs
     * @return Number of PMSs
     */
    public Optional<Integer> getNumberPSMs(){
        return getSummaryResultProperty(CvTermReference.PRIDE_NUMBER_ID_PSMS.getAccession());
    }

    private Optional<Integer> getSummaryResultProperty( String propertyAccession){
        if(summaryResults != null ){
            Optional<CvParam> numberPeptides = summaryResults.stream()
                    .filter(x -> x.getAccession().equalsIgnoreCase(propertyAccession))
                    .findFirst();
            if(numberPeptides.isPresent()){
                return Optional.of(Integer.parseInt(numberPeptides.get().getValue()));
            }
        }
        return Optional.empty();
    }
    /**
     * Return the list of software that has been used for the analysis.
     * @return
     */
    public Optional<? extends Collection<? extends CvParamProvider>> getAnalyzeSoftware(){
        if(dataAnalysisSoftwares != null && dataAnalysisSoftwares.size()>0)
            return Optional.of(dataAnalysisSoftwares);
        return Optional.empty();
    }

    /**
     * Get the PTM information from the Assay
     * @return Get the files from the Assay.
     */
    public Optional<? extends Collection<? extends Tuple<? extends CvParamProvider, Integer>>> getPTMSummaryResults(){
        if(ptmsResults != null && ptmsResults.size() > 0)
            return Optional.of(ptmsResults);
        return Optional.empty();
    }

}
