package uk.ac.ebi.pride.mongodb.archive.model.sample;

import lombok.Builder;
import lombok.Data;
import uk.ac.ebi.pride.archive.dataprovider.common.ITuple;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
import uk.ac.ebi.pride.archive.dataprovider.sample.ISampleMSRunRow;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
 * @author ypriverol on 29/10/2018.
 */
@Builder
@Data
public class MongoISampleMSRunRow implements ISampleMSRunRow {

    /**
     * Sample Accession for each biological replicate.
     */
    String sampleAccession;

    /**
     * Project Accession that contains the Sample.
     */
    String projectAccession;

    /**
     * MsRun Accession
     */
    String msRunAccession;

    /**
     * Fraction Accession
     */
    CvParamProvider fractionAccession;

    /**
     * Sample Label
     */
    private CvParamProvider sampleLabel;

    /**
     * Sample Reagent
     */
    private CvParamProvider sampleReagent;

    /**
     * Sample Metadata in {@link CvParamProvider}.
     */
    List<Tuple<DefaultCvParam, DefaultCvParam>> sampleProperties;

    /**
     * MsRun Terms in {@link CvParamProvider}.
     */
    List<Tuple<DefaultCvParam, DefaultCvParam>> msRunProperties;

    public MongoISampleMSRunRow() {
    }

    public MongoISampleMSRunRow(Comparable sampleAccession, Comparable projectAccession, Comparable msRunAccession,
                                CvParamProvider fractionAccession, CvParamProvider sampleLabel, CvParamProvider labelReagent,
                                Collection<? extends ITuple<? extends CvParamProvider, ? extends CvParamProvider>> sampleProperties,
                                Collection<? extends ITuple<? extends CvParamProvider, ? extends CvParamProvider>> msRunProperties) {
        this.sampleAccession = sampleAccession.toString();
        this.projectAccession = projectAccession.toString();
        this.msRunAccession = msRunAccession.toString();
        this.fractionAccession = fractionAccession;
        this.sampleLabel = sampleLabel;
        this.sampleReagent = labelReagent;
        this.sampleProperties = sampleProperties.stream().map( x -> {
            CvParamProvider key = x.getKey();
            CvParamProvider value = x.getValue();
            return new Tuple<>(new DefaultCvParam(key.getCvLabel(), key.getAccession(), key.getName(), key.getValue()),
                    new DefaultCvParam(value.getCvLabel(), value.getAccession(), value.getName(), value.getValue()));
        }).collect(Collectors.toList());

        this.msRunProperties = (msRunProperties !=null)?msRunProperties.stream().map( x -> {
            CvParamProvider key = x.getKey();
            CvParamProvider value = x.getValue();
            return new Tuple<>(new DefaultCvParam(key.getCvLabel(), key.getAccession(), key.getName(), key.getValue()),
                    new DefaultCvParam(value.getCvLabel(), value.getAccession(), value.getName(), value.getValue()));
        }).collect(Collectors.toList()): Collections.emptyList();

    }

    public String getSampleAccession() {
        return sampleAccession;
    }

    @Override
    public String getMsRunAccession() {
        return msRunAccession;
    }

    @Override
    public String getProjectAccession() {
        return projectAccession;
    }

    @Override
    public CvParamProvider getSampleLabel() {
        return sampleLabel;
    }

    @Override
    public CvParamProvider getFractionIdentifierCvParam() {
        return fractionAccession;
    }

    @Override
    public String getFractionAccession() {
        return (fractionAccession != null)? fractionAccession.getValue():null;
    }

    @Override
    public Collection<? extends ITuple<? extends CvParamProvider, ? extends CvParamProvider>> getSampleProperties() {
        return sampleProperties;
    }

    @Override
    public CvParamProvider getLabelReagent() {
        return sampleReagent;
    }

    @Override
    public Collection<? extends ITuple<? extends CvParamProvider, ? extends CvParamProvider>> getMsRunProperties() {
        return msRunProperties;
    }

    @Override
    public Comparable getUniqueKey() {
        String result =   (projectAccession != null ? projectAccession + "-" : "");
        result = result + (sampleAccession != null ? sampleAccession + "-":"") ;
        result = result + (msRunAccession != null ? msRunAccession + "-": "");
        result = result + (fractionAccession != null ? fractionAccession + "-":"");
        result = result + (sampleLabel != null ? sampleLabel.getAccession()+"-":"");
        result = result + (sampleReagent != null ? sampleReagent.getAccession():"");
        return result;
    }


}
