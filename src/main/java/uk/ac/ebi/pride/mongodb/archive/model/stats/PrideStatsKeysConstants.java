package uk.ac.ebi.pride.mongodb.archive.model.stats;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * Type of Statistics that will be run in the pipeline.
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 27/06/2018.
 */
public enum PrideStatsKeysConstants {

    SUBMISSIONS_PER_MONTH("PRIDE_SUBMISSIONS_PER_MONTH"),
    SUBMISSIONS_PER_YEAR("PUBLIC_SUBMISSIONS_PER_YEAR");

    public String statsKey;

    PrideStatsKeysConstants(String statsKey) {
        this.statsKey = statsKey;
    }


}
