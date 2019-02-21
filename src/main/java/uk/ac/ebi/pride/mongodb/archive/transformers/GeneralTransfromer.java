package uk.ac.ebi.pride.mongodb.archive.transformers;

import uk.ac.ebi.pride.archive.dataprovider.sample.ISampleMSRunRow;
import uk.ac.ebi.pride.mongodb.archive.model.sample.MongoISampleMSRunRow;

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

    public static MongoISampleMSRunRow transformSampleMsRun(ISampleMSRunRow sampleMSRunRow) {
        if(sampleMSRunRow != null){
            return new MongoISampleMSRunRow(sampleMSRunRow.getSampleAccession(), sampleMSRunRow.getProjectAccession(), sampleMSRunRow.getMsRunAccession(), sampleMSRunRow.getFractionIdentifierCvParam(), sampleMSRunRow.getSampleLabel(), sampleMSRunRow.getLabelReagent(), sampleMSRunRow.getSampleProperties(), sampleMSRunRow.getMSRunProperties());
        }
        return null;

    }
}
