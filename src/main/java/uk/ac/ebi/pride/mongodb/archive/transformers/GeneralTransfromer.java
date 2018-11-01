package uk.ac.ebi.pride.mongodb.archive.transformers;

import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleMSRunTuple;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleProvider;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;
import uk.ac.ebi.pride.mongodb.archive.model.sample.MongoPrideSample;
import uk.ac.ebi.pride.mongodb.archive.model.sample.MongoSampleMSRun;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
 * @author ypriverol on 23/10/2018.
 */
public class GeneralTransfromer {

    public static MongoCvParam transformCvParam(CvParamProvider param){
        return new MongoCvParam(param.getCvLabel(),param.getAccession(), param.getName(), param.getValue());
    }

    public static MongoPrideSample transformSample(SampleProvider originalSample) {
        if (originalSample != null) {
            List<Tuple<MongoCvParam, MongoCvParam>> properties = originalSample.getSampleProperties()
                    .stream()
                    .map(x -> new Tuple<>(new MongoCvParam(x.getKey()), new MongoCvParam(x.getValue())))
                    .collect(Collectors.toList());
            return MongoPrideSample.builder()
                    .accession(((String) originalSample.getAccession()))
                    .properties(properties).build();
        }
        return null;
    }

    public static MongoSampleMSRun transformSampleMsRun(SampleMSRunTuple sampleMSRunTuple) {
        if(sampleMSRunTuple != null){
            CvParamProvider fractionMongo = sampleMSRunTuple.getFractionIdentifier();
            CvParamProvider labelMongo = sampleMSRunTuple.getSampleLabel();
            CvParamProvider technicalRep = sampleMSRunTuple.getTechnicalReplicateIdentifier();

            // Capture the Fraction information
            MongoCvParam fraction = null;
            if(fractionMongo != null)
                fraction = new MongoCvParam(fractionMongo.getCvLabel(), fractionMongo.getAccession(),fractionMongo.getName(), fractionMongo.getValue());

            //Capture the Labeling
            MongoCvParam label = null;
            if(labelMongo != null)
                label = new MongoCvParam(labelMongo.getCvLabel(), labelMongo.getAccession(),labelMongo.getName(), labelMongo.getValue());

            //Capture the Labeling
            MongoCvParam rep = null;
            if(technicalRep != null)
                rep = new MongoCvParam(technicalRep.getCvLabel(), technicalRep.getAccession(),technicalRep.getName(), technicalRep.getValue());

            List<Tuple<MongoCvParam, MongoCvParam>> additionalProperties = new ArrayList<>();
            if(sampleMSRunTuple.getAdditionalProperties() != null)
                additionalProperties = sampleMSRunTuple.getAdditionalProperties()
                        .stream().map( x-> {
                    CvParamProvider key = x.getKey();
                    CvParamProvider value = x.getValue();
                    if(key != null && value != null){
                        return new Tuple<>(new MongoCvParam(key.getCvLabel(), key.getAccession(), key.getName(), key.getValue()), new MongoCvParam(value.getCvLabel(), value.getAccession(), value.getName(), value.getValue()));
                    }
                    return null;
                })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            return MongoSampleMSRun
                    .builder()
                    .sampleAccession((String) sampleMSRunTuple.getKey())
                    .msrunAccession((String) sampleMSRunTuple.getValue())
                    .fractionIdentifier(fraction)
                    .technicalReplicateIdentifier(rep)
                    .sampleLabel(label)
                    .additionalProperties(additionalProperties)
                    .build();

        }
        return null;
    }
}
