package uk.ac.ebi.pride.mongodb.archive.model.sample;

import lombok.Builder;
import lombok.Data;
import uk.ac.ebi.pride.archive.dataprovider.common.ITuple;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleMSRunTuple;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;

import java.util.Collection;
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
public class MongoSampleMSRun implements SampleMSRunTuple {

    String sampleAccession;
    String msrunAccession;

    MongoCvParam sampleLabel;
    MongoCvParam fractionIdentifier;
    MongoCvParam technicalReplicateIdentifier;
    List<Tuple<MongoCvParam, MongoCvParam>> additionalProperties;

    @Override
    public Comparable getKey() {
        return sampleAccession;
    }

    @Override
    public Comparable getValue() {
        return msrunAccession;
    }

    @Override
    public CvParamProvider getSampleLabel() {
        return sampleLabel;
    }

    @Override
    public CvParamProvider getFractionIdentifier() {
        return fractionIdentifier;
    }

    @Override
    public CvParamProvider getTechnicalReplicateIdentifier() {
        return technicalReplicateIdentifier;
    }

    @Override
    public Collection<? extends ITuple<? extends CvParamProvider, ? extends CvParamProvider>> getAdditionalProperties() {
        if(additionalProperties != null){
            return additionalProperties.stream().map( x-> new Tuple<>((CvParamProvider) x.getKey(), (CvParamProvider) x.getValue())).collect(Collectors.toList());
        }
        return null;
    }
}
