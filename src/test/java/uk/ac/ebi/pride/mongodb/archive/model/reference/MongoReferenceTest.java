package uk.ac.ebi.pride.mongodb.archive.model.reference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.pride.archive.dataprovider.reference.Reference;

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

    Reference ref;

    @BeforeEach
    public void setUp() {
        ref = new Reference("Uszkoreit, Julian, et al. \"PIA: An intuitive protein inference engine with a web-based user interface.\" Journal of proteome research 14.7 (2015): 2988-2997.",25938255,"10.1021/acs.jproteome.5b00121");

    }

    @Test
    public void mongReferenceTest(){
        System.out.println(ref.toString());
        Assertions.assertEquals(25938255, ref.getPubmedId());
    }

    @Test
    public void mongoRefTes(){
        Reference reference = new Reference("Uszkoreit, Julian, et al. \"PIA: An intuitive protein inference engine with a web-based user interface.\" Journal of proteome research 14.7 (2015): 2988-2997.",25938255, "10.1021/acs.jproteome.5b00121" );
        System.out.println(reference.toString());
    }
}