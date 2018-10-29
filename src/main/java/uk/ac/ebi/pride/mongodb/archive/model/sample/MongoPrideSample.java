package uk.ac.ebi.pride.mongodb.archive.model.sample;

import lombok.Builder;
import lombok.Data;
import uk.ac.ebi.pride.archive.dataprovider.common.ITuple;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleProvider;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;

import java.util.Collection;
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
public class MongoPrideSample implements SampleProvider {

    String accession;

    Collection<Tuple<MongoCvParam, MongoCvParam>> properties;

    @Override
    public Comparable getAccession() {
        return accession;
    }

    @Override
    public Collection<ITuple<CvParamProvider, CvParamProvider>> getSampleProperties() {
        return properties
                .stream()
                .map( x-> new Tuple<CvParamProvider, CvParamProvider>(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
    }
}
