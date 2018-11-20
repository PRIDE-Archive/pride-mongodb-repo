package uk.ac.ebi.pride.mongodb.archive.service.msruns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.archive.dataprovider.msrun.MsRunProvider;
import uk.ac.ebi.pride.archive.dataprovider.utils.ProjectFileCategoryConstants;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.msrun.MongoPrideMSRun;
import uk.ac.ebi.pride.mongodb.archive.repo.msruns.PrideMSRunMongoRepository;
import uk.ac.ebi.pride.mongodb.archive.transformers.MSRunTransfromer;
import uk.ac.ebi.pride.utilities.obo.OBOMapper;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author ypriverol on 20/11/2018.
 */
@Service
@Slf4j
public class PrideMsRunMongoService implements IMSRunService {

    final
    PrideMSRunMongoRepository msRunRepository;

    OBOMapper psiOBOMapper;

    @Autowired
    private MongoOperations mongo;

    @Autowired
    public PrideMsRunMongoService(PrideMSRunMongoRepository msRunRepository) {
        this.msRunRepository = msRunRepository;
        try {
            psiOBOMapper = OBOMapper.getPSIMSInstance();
        } catch (URISyntaxException e) {
            log.debug("An error has occurred when creating the PSI-MS ontology");
        }
    }


    /**
     * We can update an existing {@link MongoPrideMSRun}
     * @param mongoPrideMSRun the new MongoPrideMSRun
     * @return Optional
     */
    @Override
    public Optional<MongoPrideMSRun> updateMSRun(MongoPrideMSRun mongoPrideMSRun){
        Optional<MongoPrideMSRun> file = msRunRepository.findMsRunByAccession(mongoPrideMSRun.getAccession());
        if(file.isPresent()){
            mongoPrideMSRun = msRunRepository.save(mongoPrideMSRun);
        }else if(mongoPrideMSRun.getId() != null){
            mongoPrideMSRun = msRunRepository.save(mongoPrideMSRun);
        }
        return Optional.of(mongoPrideMSRun);
    }

    /**
     * Find all the MSruns for an specific project accession
     * @param projectAccession Project Accession
     * @return List of {@link MongoPrideMSRun}
     */
    @Override
    public List<MongoPrideMSRun> getMSRunsByProject(String projectAccession){
        return msRunRepository.filterMSRunByProjectAccession(projectAccession);
    }

    /**
     * Set the metadata of the MSRun
     * @param msRunMetadata {@link MsRunProvider} msRun Metadata
     * @param accession Accession of the {@link MongoPrideMSRun}
     * @return Optional
     */
    public Optional<MongoPrideMSRun> updateMSRunMetadata(MsRunProvider msRunMetadata, String accession) {

        Optional<MongoPrideMSRun> msRunOptional = msRunRepository.findMsRunByAccession(accession);

        if(msRunOptional.isPresent()){
            MongoPrideMSRun msRun = MSRunTransfromer.transformMetadata(msRunOptional.get(), msRunMetadata, psiOBOMapper);
            msRun = msRunRepository.save(msRun);
            return Optional.of(msRun);
        }
        return Optional.empty();
    }

    /**
     * Find a corresponding msRun by the accession.
     * @param accession Accession of the msRuns
     * @return Optional MSRun
     */
    public Optional<MongoPrideMSRun> findMSRunByAccession(String accession) {
        return msRunRepository.findMsRunByAccession(accession);
    }

}
