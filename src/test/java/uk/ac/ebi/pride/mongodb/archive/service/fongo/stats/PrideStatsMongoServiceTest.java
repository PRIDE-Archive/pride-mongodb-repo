package uk.ac.ebi.pride.mongodb.archive.service.fongo.stats;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.archive.dataprovider.utils.Tuple;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectFongoTestConfig;
import uk.ac.ebi.pride.mongodb.archive.model.stats.MongoPrideStats;
import uk.ac.ebi.pride.mongodb.archive.model.stats.PrideStatsKeysConstants;
import uk.ac.ebi.pride.mongodb.archive.service.stats.PrideStatsMongoService;

import java.util.*;

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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectFongoTestConfig.class})
public class PrideStatsMongoServiceTest {


    @Autowired
    private PrideStatsMongoService service;

    private Date date;

    private List<Tuple<String, Integer>> monthlySubmissions;

    @Test
    public void save() {
        insertDataset();
    }

    private void insertDataset(){
        Map<String, List<Tuple<String, Integer>>> submissionStatMap = new HashMap<>();
        monthlySubmissions = new ArrayList<>();
        monthlySubmissions.add(new Tuple<>("2004", 20));
        monthlySubmissions.add(new Tuple<>("2006", 3));
        monthlySubmissions.add(new Tuple<>("2007", 23));
        monthlySubmissions.add(new Tuple<>("2008", 43));
        submissionStatMap.put("SUBMISSIONS_PER_YEAR", monthlySubmissions);
        date = new Date();
        MongoPrideStats stat = MongoPrideStats.
                builder()
                .estimationDate(date)
                .submissionsCount(submissionStatMap)
                .build();
        service.save(stat);
    }

    @Test
    public void findStatsBydate() {
        insertDataset();
        Optional<MongoPrideStats> findDatasets = service.findStatsBydate(date);
        findDatasets.ifPresent(mongoPrideStats -> System.out.println(mongoPrideStats.getEstimationDate()));
        Assert.assertTrue(findDatasets.isPresent());

    }

    @Test
    public void updateSubmissionCountStats() {
        insertDataset();
        monthlySubmissions.add(new Tuple<>("2009", 400));
        service.updateSubmissionCountStats(date, PrideStatsKeysConstants.SUBMISSIONS_PER_YEAR, monthlySubmissions);
        Optional<MongoPrideStats> findDatasets = service.findStatsBydate(date);
        findDatasets.ifPresent(mongoPrideStats -> System.out.println(mongoPrideStats.getSubmissionsCount().size()));
        Assert.assertEquals(5, findDatasets.get().getSubmissionsCount().get(PrideStatsKeysConstants.SUBMISSIONS_PER_YEAR.getStatsKey()).size());
    }

    @Test
    public void updateSubmissionComplexStats() {

    }

    @Test
    public void findLastGeneratedStats() {
        insertDataset();
        MongoPrideStats findDatasets = service.findLastGeneratedStats();
        if(findDatasets != null)
            System.out.println(findDatasets.getEstimationDate());
        assert findDatasets != null;
        Assert.assertEquals(4, Objects.requireNonNull(findDatasets).getSubmissionsCount().get(PrideStatsKeysConstants.SUBMISSIONS_PER_YEAR.getStatsKey()).size());
    }

}