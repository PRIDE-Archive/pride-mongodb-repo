package uk.ac.ebi.pride.mongodb.molecules.service.psms;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideEvidence;
import uk.ac.ebi.pride.mongodb.molecules.repo.peptide.PridePeptideEvidenceMongoRepository;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author ypriverol
 */

@Service
@Slf4j
public class PridePeptideEvidenceMongoService {


}
