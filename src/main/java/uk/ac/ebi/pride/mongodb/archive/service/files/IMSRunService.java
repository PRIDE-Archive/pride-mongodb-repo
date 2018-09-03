package uk.ac.ebi.pride.mongodb.archive.service.files;

import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideMSRun;

import java.util.List;
import java.util.Optional;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 *
 * This interface manage all the {@link uk.ac.ebi.pride.archive.dataprovider.msrun.MsRunProvider} in the file collection including: delete/update/insert/select
 * of MSRun information.
 *
 * @author ypriverol on 03/09/2018.
 */
public interface IMSRunService {

    /**
     * We can update an existing {@link MongoPrideFile} as {@link MongoPrideMSRun} .
     * @param mongoPrideMSRun the new MongoPrideMSRun
     * @return Optional
     */
    public Optional<MongoPrideMSRun> updateMSRun(MongoPrideMSRun mongoPrideMSRun);

    /**
     * Find all the MSruns for an specific project accession
     * @param projectAccession Project Accession
     * @return List of {@link MongoPrideMSRun}
     */
    public List<MongoPrideMSRun> getMSRunsByProject(String projectAccession);



}
