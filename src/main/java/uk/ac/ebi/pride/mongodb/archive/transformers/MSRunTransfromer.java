package uk.ac.ebi.pride.mongodb.archive.transformers;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.archive.dataprovider.msrun.MsRunProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.msrun.MongoPrideMSRun;
import uk.ac.ebi.pride.archive.dataprovider.msrun.idsettings.IdSetting;
import uk.ac.ebi.pride.utilities.ols.web.service.cache.OntologyCacheService;

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
 * @author ypriverol on 23/10/2018.
 */
@Slf4j
public class MSRunTransfromer {

    public static MongoPrideMSRun transformMetadata(MongoPrideMSRun msRunProvider, MsRunProvider metadata, OntologyCacheService ontologyCacheService){
        if(metadata != null){
            if(metadata.getInstrumentProperties() != null){
                Set<CvParam> mongoCvParams = metadata.getInstrumentProperties()
                        .stream()
                        .filter(x -> ontologyCacheService.isTermExisting(x.getAccession(),x.getCvLabel()))
                        .map(x -> new CvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toSet());
                if(mongoCvParams.isEmpty())
                    log.info("Non of the CVTerms provided in Instrument Properties are supported by PRIDE Database");
                msRunProvider.addInstrumentProperties(mongoCvParams);
            }
            if(metadata.getFileProperties() != null){
                Set<CvParam> mongoCvParams = metadata.getFileProperties()
                        .stream()
                        .filter(x -> ontologyCacheService.isTermExisting(x.getAccession(),x.getCvLabel()))
                        .map(x -> new CvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toSet());
                if(mongoCvParams.isEmpty())
                    log.info("Non of the CVTerms provided in File Properties are supported by PRIDE Database");
                msRunProvider.addFileProperties(mongoCvParams);
            }
            if(metadata.getMsData() != null){
                Set<CvParam> mongoCvParams = metadata.getMsData()
                        .stream()
                        .filter(x -> ontologyCacheService.isTermExisting(x.getAccession(),x.getCvLabel()))
                        .map(x -> new CvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toSet());
                if(mongoCvParams.isEmpty())
                    log.info("Non of the CVTerms provided in Ms Data are supported by PRIDE Database");
                msRunProvider.addMsData(mongoCvParams);
            }
            if(metadata.getScanSettings() != null){
                Set<CvParam> mongoCvParams = metadata.getScanSettings()
                        .stream()
                        .filter(x -> ontologyCacheService.isTermExisting(x.getAccession(),x.getCvLabel()))
                        .map(x -> new CvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toSet());
                msRunProvider.addScanSettings(mongoCvParams);
                if(mongoCvParams.isEmpty())
                    log.info("Non of the CVTerms provided in Scan Settings are supported by PRIDE Database");
            }
            if(metadata.getIdSettings() != null){
                Set<IdSetting> idSettings = metadata.getIdSettings()
                        .stream()
                        .map(x -> (IdSetting)x)
                        .collect(Collectors.toSet());
                msRunProvider.addIdSettings(idSettings);
                if(idSettings.isEmpty())
                    log.info("ID Settings have not been supplied");
            }
        }

        return msRunProvider;

    }

    public static MongoPrideMSRun transformMSRun(MongoPrideFile mongoPrideFile) {
        return new MongoPrideMSRun(mongoPrideFile);
    }
}
