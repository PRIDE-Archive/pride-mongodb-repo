package uk.ac.ebi.pride.mongodb.archive.service.samples;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleMSRunTuple;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleProvider;
import uk.ac.ebi.pride.mongodb.archive.model.sample.MongoPrideExperimentalDesign;
import uk.ac.ebi.pride.mongodb.archive.model.sample.MongoPrideSample;
import uk.ac.ebi.pride.mongodb.archive.repo.samples.PrideMongoSampleRepository;
import uk.ac.ebi.pride.mongodb.archive.service.psms.PridePSMMongoService;
import uk.ac.ebi.pride.mongodb.archive.transformers.GeneralTransfromer;

import java.util.ArrayList;
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
@Service
@Slf4j
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

    public List<MongoPrideSample> updateSamplesByProjectAccession(String accession, List<? extends SampleProvider> samples) {
        MongoPrideExperimentalDesign existingExp = sampleRepository.findByProjectAccession(accession);
        if(existingExp == null)
            existingExp = MongoPrideExperimentalDesign
                    .builder()
                    .samples(new ArrayList<>())
                    .sampleMSRunTuples(new ArrayList<>())
                    .accession(accession).build();
        existingExp.setSamples(samples.stream().map(GeneralTransfromer::transformSample).collect(Collectors.toList()));
        existingExp = sampleRepository.save(existingExp);
        return existingExp.getSamples();

    }

    public Collection<? extends SampleMSRunTuple> updateSamplesMRunProjectAccession(String accession, List<? extends SampleMSRunTuple> samples) {
        MongoPrideExperimentalDesign existingExp = sampleRepository.findByProjectAccession(accession);
        if(existingExp == null){
            existingExp = MongoPrideExperimentalDesign
                    .builder()
                    .samples(new ArrayList<>())
                    .sampleMSRunTuples(new ArrayList<>())
                    .accession(accession).build();
        }
        existingExp.setSampleMSRunTuples(samples.stream().map(GeneralTransfromer::transformSampleMsRun).collect(Collectors.toList()));
        return existingExp.getSampleMSrun();
    }
}
