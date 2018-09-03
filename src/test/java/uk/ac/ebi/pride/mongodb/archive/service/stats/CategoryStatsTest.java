package uk.ac.ebi.pride.mongodb.archive.service.stats;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.archive.dataprovider.utils.Tuple;

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
public class CategoryStatsTest {

    CategoryStats stats;

    @Before
    public void setUp() throws Exception {
        stats = CategoryStats
                .builder()
                .category(new Tuple<>("Number od Submissions", 30000))
                .build();
    }

    @Test
    public void CategoryTest(){
        System.out.println(stats.toString());
        Assert.assertEquals(new Tuple<>("Number od Submissions", 30000), stats.getCategory());
    }
}