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

    EVIDENCES_IN_ARCHIVE("EVIDENCES_IN_ARCHIVE"),
    SUBMISSIONS_PER_MONTH("SUBMISSIONS_PER_MONTH"),
    SUBMISSIONS_PER_YEAR("SUBMISSIONS_PER_YEAR"),
    SUBMISSIONS_PER_INSTRUMENTS("SUBMISSIONS_PER_INSTRUMENT"),
    SUBMISSIONS_PER_DISEASES("SUBMISSIONS_PER_DISEASES"),
    SUBMISSIONS_PER_ORGANISM("SUBMISSIONS_PER_ORGANISM"),
    SUBMISSIONS_PER_ORGANISM_PART("SUBMISSIONS_PER_ORGANISM_PART"),
    SUBMISSIONS_PER_COUNTRY("SUBMISSIONS_PER_COUNTRY"),
    SUBMISSIONS_PER_CATEGORIES("SUBMISSIONS_PER_CATEGORIES"),
    SUBMISSIONS_PER_MODIFICATIONS("SUBMISSIONS_PER_MODIFICATIONS");


    public String statsKey;

    PrideStatsKeysConstants(String statsKey) {
        this.statsKey = statsKey;
    }

    public String getStatsKey() {
        return statsKey;
    }
}
