package uk.ac.ebi.pride.mongodb.archive.model.reference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author ypriverol on 03/09/2018.
 */
public class MongoReferenceTest {

    MongoReference ref;

    @Before
    public void setUp() throws Exception {
        ref = MongoReference.builder()
                .doi("10.1021/acs.jproteome.5b00121")
                .pubmedID(25938255)
                .referenceLine("Uszkoreit, Julian, et al. \"PIA: An intuitive protein inference engine with a web-based user interface.\" Journal of proteome research 14.7 (2015): 2988-2997.")
                .build();

    }

    @Test
    public void mongReferenceTest(){
        System.out.println(ref.toString());
        Assert.assertEquals(25938255, ref.getPubmedId());
    }

    @Test
    public void mongoRefTes(){
        MongoReference reference = new MongoReference("Uszkoreit, Julian, et al. \"PIA: An intuitive protein inference engine with a web-based user interface.\" Journal of proteome research 14.7 (2015): 2988-2997.",25938255, "10.1021/acs.jproteome.5b00121" );
        System.out.println(reference.toString());
    }
}