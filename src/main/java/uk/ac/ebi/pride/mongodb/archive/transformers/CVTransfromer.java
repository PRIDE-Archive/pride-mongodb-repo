package uk.ac.ebi.pride.mongodb.archive.transformers;

import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;

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
public class CVTransfromer {

    public static MongoCvParam transformCvParam(CvParamProvider param){
        return new MongoCvParam(param.getCvLabel(),param.getAccession(), param.getName(), param.getValue());
    }
}
