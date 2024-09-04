package uk.ac.ebi.pride.mongodb.archive.service.stats;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;

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
public class CategoryStatsTest {

    CategoryStats stats;

    @BeforeEach
    public void setUp() {
        stats = CategoryStats
                .builder()
                .category(new Tuple<>("Number od Submissions", 30000))
                .build();
    }

    @Test
    public void CategoryTest(){
        System.out.println(stats.toString());
        Assertions.assertEquals(new Tuple<>("Number od Submissions", 30000), stats.getCategory());
    }

    @Test
    public void hashCodeTest() {
        System.out.println(stats.hashCode());
    }
}