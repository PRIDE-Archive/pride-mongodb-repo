package uk.ac.ebi.pride.mongodb.archive.service.samples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleMSRunTuple;
import uk.ac.ebi.pride.mongodb.archive.model.sample.MongoPrideExperimentalDesign;
import uk.ac.ebi.pride.mongodb.archive.model.sample.MongoPrideSample;
import uk.ac.ebi.pride.mongodb.archive.repo.samples.PrideMongoSampleRepository;
import uk.ac.ebi.pride.mongodb.archive.service.psms.PridePSMMongoService;

import java.util.Collection;
import java.util.List;

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
public class PrideSampleMongoService {

    final PrideMongoSampleRepository sampleRepository;

    @Autowired
    private MongoOperations mongo;

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PridePSMMongoService.class);

    @Autowired
    public PrideSampleMongoService(PrideMongoSampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    /**
     * Get samples by Project Accession
     * @param accession Project Accession
     * @return List of {@link MongoPrideSample}
     */
    public List<MongoPrideSample> getSamplesByProjectAccession(String accession){
        MongoPrideExperimentalDesign experimentalDesign = sampleRepository.findByProjectAccession(accession);
        if(experimentalDesign != null){
            return experimentalDesign.getSamples();
        }
        return null;
    }

    /**
     * Get samples by Project Accession
     * @param accession Project Accession
     * @return List of {@link MongoPrideSample}
     */
    public Collection<? extends SampleMSRunTuple> getSamplesMRunProjectAccession(String accession){
        MongoPrideExperimentalDesign experimentalDesign = sampleRepository.findByProjectAccession(accession);
        if(experimentalDesign != null){
            return experimentalDesign.getSampleMSrun();
        }
        return null;
    }
}
