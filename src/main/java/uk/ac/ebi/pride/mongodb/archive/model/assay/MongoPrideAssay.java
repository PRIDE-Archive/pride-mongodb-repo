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
import uk.ac.ebi.pride.archive.dataprovider.common.ITuple;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    /** The analysis accessions are releated with the following File **/
    @Indexed(name = EXTERNAL_ANALYSIS_ACCESSIONS)
    Set<String> analysisAccessions;

    /** The analysis accessions are related with the following File **/
    @Indexed(name = ASSAY_FILE_ACCESSIONS)
    List<String> fileAccessions;

    /** Accession generated for each File **/
    @Indexed(name = ACCESSION, unique = true)
    @Getter(AccessLevel.NONE)
    String accession;

    /** Accession generated for each File **/
    @Field(value = PROJECT_TILE)
    @Getter(AccessLevel.NONE)
    String title;

    /** Accession generated for each File **/
    @Indexed(name = ACCESSION, unique = true)
    @Getter(AccessLevel.NONE)
    AssayType assayType;


    @Field(value = ADDITIONAL_ATTRIBUTES)
    List<MongoCvParam> additionalProperties;

    @Field(value = SAMPLE_ATTRIBUTES_NAMES)
    List<Tuple<MongoCvParam, MongoCvParam>> sampleProperties;


    @Override
    public Collection<? extends ITuple<? extends CvParamProvider, ? extends CvParamProvider>> getSampleProperties() {
        if(additionalProperties != null){
            return sampleProperties.stream().map( x-> new Tuple<>((CvParamProvider) x.getKey(), (CvParamProvider) x.getValue())).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<MongoCvParam> getAdditionalProperties() {
        return additionalProperties;
    }

    @Override
    public AssayType getAssayType() {
        return assayType;
    }

}
