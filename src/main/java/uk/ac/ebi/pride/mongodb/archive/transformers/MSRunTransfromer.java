package uk.ac.ebi.pride.mongodb.archive.transformers;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.archive.dataprovider.msrun.MsRunProvider;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideMSRun;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;
import uk.ac.ebi.pride.utilities.obo.OBOMapper;

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
 * @author ypriverol on 23/10/2018.
 */
@Slf4j
public class MSRunTransfromer {

    public static MongoPrideMSRun transformMetadata(MongoPrideMSRun msRunProvider, MsRunProvider metadata, OBOMapper oboMapper){
        if(metadata != null){
            if(metadata.getInstrumentProperties() != null){
                List<MongoCvParam> mongoCvParams = metadata.getInstrumentProperties()
                        .stream()
                        .filter(x -> oboMapper.getTermByAccession(x.getAccession()) != null)
                        .map(x -> new MongoCvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toList());
                if(mongoCvParams.isEmpty())
                    log.info("Non of the CVTerms provided in Instrument Properties are supported by PRIDE Database");
                msRunProvider.addInstrumentProperties(mongoCvParams);
            }
            if(metadata.getFileProperties() != null){
                List<MongoCvParam> mongoCvParams = metadata.getFileProperties()
                        .stream()
                        .filter(x -> oboMapper.getTermByAccession(x.getAccession()) != null)
                        .map(x -> new MongoCvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toList());
                if(mongoCvParams.isEmpty())
                    log.info("Non of the CVTerms provided in File Properties are supported by PRIDE Database");
                msRunProvider.addFileProperties(mongoCvParams);
            }
            if(metadata.getMsData() != null){
                List<MongoCvParam> mongoCvParams = metadata.getMsData()
                        .stream()
                        .filter(x -> oboMapper.getTermByAccession(x.getAccession()) != null)
                        .map(x -> new MongoCvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toList());
                if(mongoCvParams.isEmpty())
                    log.info("Non of the CVTerms provided in Ms Data are supported by PRIDE Database");
                msRunProvider.addMsData(mongoCvParams);
            }
            if(metadata.getScanSettings() != null){
                List<MongoCvParam> mongoCvParams = metadata.getScanSettings()
                        .stream()
                        .filter(x -> oboMapper.getTermByAccession(x.getAccession()) != null)
                        .map(x -> new MongoCvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toList());
                msRunProvider.addScanSettings(mongoCvParams);
                if(mongoCvParams.isEmpty())
                    log.info("Non of the CVTerms provided in Scan Settings are supported by PRIDE Database");
            }
        }

        return msRunProvider;

    }

    public static MongoPrideMSRun transformMSRun(MongoPrideFile mongoPrideFile) {
        return new MongoPrideMSRun(mongoPrideFile);
    }
}
