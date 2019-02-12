package uk.ac.ebi.pride.mongodb.archive.service.msruns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.archive.dataprovider.msrun.MsRunProvider;
import uk.ac.ebi.pride.archive.dataprovider.utils.ProjectFileCategoryConstants;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.msrun.MongoPrideMSRun;
import uk.ac.ebi.pride.mongodb.archive.model.msrun.idsettings.IdSetting;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;
import uk.ac.ebi.pride.mongodb.archive.repo.msruns.PrideMSRunMongoRepository;
import uk.ac.ebi.pride.mongodb.archive.transformers.MSRunTransfromer;
import uk.ac.ebi.pride.utilities.obo.OBOMapper;

import java.net.URISyntaxException;
import java.util.*;
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
     * Set the metadata of the MSRun
     * @param msRunFieldData A JSON string with part of MSRun data
     * @param accession Accession of the {@link MongoPrideMSRun}
     * @return Optional
     */
    public Optional<MongoPrideMSRun> updateMSRunMetadataParts(String fieldName, MsRunProvider msRunFieldData, String accession) {

        Optional<MongoPrideMSRun> msRunOptional = msRunRepository.findMsRunByAccession(accession);
        Set<MongoCvParam> mongoCvParams = new HashSet<>();
        if(msRunOptional.isPresent()) {
            MongoPrideMSRun msRun = msRunOptional.get();
            switch (fieldName) {
                case PrideArchiveField.MS_RUN_FILE_PROPERTIES:
                    mongoCvParams = (Set<MongoCvParam>) msRunFieldData.getFileProperties();
                    msRun.setFileProperties(mongoCvParams);
                    break;
                case PrideArchiveField.MS_RUN_INSTRUMENT_PROPERTIES:
                    mongoCvParams = (Set<MongoCvParam>) msRunFieldData.getInstrumentProperties();
                    msRun.setInstrumentProperties(mongoCvParams);
                    break;
                case PrideArchiveField.MS_RUN_MS_DATA:
                    mongoCvParams = (Set<MongoCvParam>) msRunFieldData.getMsData();
                    msRun.setMsData(mongoCvParams);
                    break;
                case PrideArchiveField.MS_RUN_SCAN_SETTINGS:
                    mongoCvParams = (Set<MongoCvParam>) msRunFieldData.getScanSettings();
                    msRun.setScanSettings(mongoCvParams);
                    break;
                case PrideArchiveField.MS_RUN_ID_SETTINGS:
                    msRun.setIdSettings(msRunFieldData.getIdSettings()
                            .stream().map(x -> (IdSetting)x).collect(Collectors.toSet()));
                    break;
            }
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


    private List<MongoCvParam> processCVParams(Set<MongoCvParam> mongoCvParams) {

        return mongoCvParams
            .stream()
            .filter(x -> psiOBOMapper.getTermByAccession(x.getAccession()) != null)
            .map(x -> new MongoCvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
            .collect(Collectors.toList());
    }

    /**
     * Delete all Files
     */
    public void deleteAll(){
        msRunRepository.deleteAll();
    }

    public boolean deleteByAccession(String accession){
        try{
            List<MongoPrideMSRun> prideMSRunFilesList = msRunRepository.findByProjectAccessions(Arrays.asList(accession));
            for(MongoPrideMSRun prideFile : prideMSRunFilesList){
                msRunRepository.delete(prideFile);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
