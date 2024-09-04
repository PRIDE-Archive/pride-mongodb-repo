package uk.ac.ebi.pride.mongodb.archive.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
public class PrideFieldEnumTest {

    @Test
    public void prideFieldEnumTest(){

        for(PrideFieldEnum prideFieldEnum: PrideFieldEnum.values())
            System.out.println(prideFieldEnum.toString());

        Assertions.assertTrue(PrideFieldEnum.values().length > 0);
    }
}