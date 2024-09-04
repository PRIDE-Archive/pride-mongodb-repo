package uk.ac.ebi.pride.mongodb.archive.service.samples;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.archive.dataprovider.sample.ISampleMSRunRow;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleProvider;
import uk.ac.ebi.pride.mongodb.archive.model.sample.MongoPrideExperimentalDesign;
import uk.ac.ebi.pride.mongodb.archive.model.sample.MongoISampleMSRunRow;
import uk.ac.ebi.pride.mongodb.archive.repo.samples.PrideMongoExperimentalDesign;
import uk.ac.ebi.pride.mongodb.archive.transformers.GeneralTransfromer;

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
@Service
@Slf4j
public class PrideSampleMongoService {

    final PrideMongoExperimentalDesign prideMongoExperimentalDesign;

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PrideSampleMongoService.class);

    public PrideSampleMongoService(PrideMongoExperimentalDesign prideMongoExperimentalDesign) {
        this.prideMongoExperimentalDesign = prideMongoExperimentalDesign;
    }

    /**
     * Get samples by Project Accession
     * @param accession Project Accession
     */
    public Collection<? extends SampleProvider> getSamplesByProjectAccession(String accession){
        MongoPrideExperimentalDesign experimentalDesign = prideMongoExperimentalDesign.findByProjectAccession(accession);
        if(experimentalDesign != null){
            return experimentalDesign.getSamples();
        }
        return Collections.emptyList();
    }

    /**
     * Get samples by Project Accession
     * @param accession Project Accession
     */
    public List<MongoISampleMSRunRow> getSamplesMRunProjectAccession(String accession){
        MongoPrideExperimentalDesign experimentalDesign = prideMongoExperimentalDesign.findByProjectAccession(accession);
        if(experimentalDesign != null){
            return experimentalDesign.getSampleMSRunTuples();
        }
        return Collections.emptyList();
    }

    public Collection<? extends ISampleMSRunRow> updateSamplesMRunProjectAccession(String accession, List<? extends ISampleMSRunRow> samples) {
        MongoPrideExperimentalDesign existingExp = prideMongoExperimentalDesign.findByProjectAccession(accession);
        if(existingExp == null){
            existingExp = MongoPrideExperimentalDesign
                    .builder()
                    .projectAccession(accession)
                    .build();
            existingExp.setSampleMSRunTuples(samples);
        }
        existingExp.setSampleMSRunTuples(samples.stream().map(GeneralTransfromer::transformSampleMsRun).collect(Collectors.toList()));
        prideMongoExperimentalDesign.save(existingExp);
        return existingExp.getSampleMSrun();
    }
}
