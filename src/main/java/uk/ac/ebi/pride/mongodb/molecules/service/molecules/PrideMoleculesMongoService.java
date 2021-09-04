package uk.ac.ebi.pride.mongodb.molecules.service.molecules;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideEvidence;
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideSummary;
import uk.ac.ebi.pride.mongodb.molecules.model.protein.PrideMongoProteinEvidence;
import uk.ac.ebi.pride.mongodb.molecules.model.psm.PrideMongoPsmSummaryEvidence;
import uk.ac.ebi.pride.mongodb.molecules.repo.peptide.PridePeptideEvidenceMongoRepository;
import uk.ac.ebi.pride.mongodb.molecules.repo.peptide.PridePeptideSummaryMongoRepository;
import uk.ac.ebi.pride.mongodb.molecules.repo.protein.PrideProteinMongoRepository;
import uk.ac.ebi.pride.mongodb.molecules.repo.psm.PridePsmSummaryEvidenceMongoRepository;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;
import uk.ac.ebi.pride.utilities.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;

@Service
@Slf4j
public class PrideMoleculesMongoService {

    final PrideProteinMongoRepository proteinMongoRepository;
    final PridePeptideEvidenceMongoRepository peptideMongoRepository;
    final PridePsmSummaryEvidenceMongoRepository psmMongoRepository;
    final PridePeptideSummaryMongoRepository pridePeptideSummaryMongoRepository;

    @Autowired
    public PrideMoleculesMongoService(PrideProteinMongoRepository proteinRepository,
                                      PridePeptideEvidenceMongoRepository peptideMongoRepository,
                                      PridePsmSummaryEvidenceMongoRepository psmMongoRepository,
                                      PridePeptideSummaryMongoRepository pridePeptideSummaryMongoRepository) {
        this.proteinMongoRepository = proteinRepository;
        this.peptideMongoRepository = peptideMongoRepository;
        this.psmMongoRepository = psmMongoRepository;
        this.pridePeptideSummaryMongoRepository = pridePeptideSummaryMongoRepository;
    }

    /**
     * This function is to find all the {@link PrideMongoProteinEvidence}
     *
     * @param projectAccession Project Accession
     * @param page             Page Number of Proteins to be retrieve .
     * @return List of {@link PrideMongoProteinEvidence}
     */
    public Page<PrideMongoProteinEvidence> findProteinsByProjectAccession(String projectAccession, Pageable page) {

        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("projectAccession=in=" + projectAccession);
        Page<PrideMongoProteinEvidence> proteins = proteinMongoRepository.filterByAttributes(filters, page);
        log.debug("The number of Proteins for the Project Accession -- " + projectAccession + " and Page -- " + page.getPageNumber() + " is -- " + proteins.getTotalElements());
        return proteins;
    }

    /**
     * Save an specific Protein in MongoDB
     *
     * @param protein {@link PrideMongoProteinEvidence}
     */
    public void saveProteinEvidences(PrideMongoProteinEvidence protein) {
        Optional<PrideMongoProteinEvidence> currentProtein = proteinMongoRepository
                .findByAccessionAndAssayAccession(protein.getReportedAccession(), protein.getAssayAccession());
        if (currentProtein.isPresent()) {
            protein.setId((ObjectId) currentProtein.get().getId());
            log.debug("The protein will be updated -- Assay: " + protein.getAssayAccession() + " Protein: "
                    + protein.getReportedAccession());
        } else {
            log.debug("New protein will be added to the database -- Assay: " + protein.getAssayAccession()
                    + " Protein: " + protein.getReportedAccession());
        }
        proteinMongoRepository.save(protein);
    }

    /**
     * This method not not find for present of the protein in the database, only try to insert it.
     *
     * @param protein {@link PrideMongoProteinEvidence}
     */
    public void insertProteinEvidences(PrideMongoProteinEvidence protein) {
        proteinMongoRepository.save(protein);
    }

    /**
     * Delete all Protein evidences from the database
     */
    public void deleteAllProteinEvidences() {
        proteinMongoRepository.deleteAll();
    }

    /**
     * Search Proteins by specific properties in the filter Query.
     *
     * @param filterQuery Query properties
     * @param page        Page to be retrieved
     * @return Page containing the {@link PrideMongoProteinEvidence}.
     */
    public Page<PrideMongoProteinEvidence> searchProteins(String filterQuery, Pageable page) {
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters(filterQuery);
        return proteinMongoRepository.filterByAttributes(filters, page);

    }

    /**
     * Find all {@link PrideMongoProteinEvidence}. This method should be executed using the Pagination.
     *
     * @param page a {@link PageRequest}
     * @return List of {@link PrideMongoProteinEvidence}
     */
    public Page<PrideMongoProteinEvidence> findAllProteinEvidences(PageRequest page) {
        return proteinMongoRepository.findAll(page);
    }

    /**
     * Find all {@link PrideMongoProteinEvidence}. This method should be executed using the Pagination.
     *
     * @param page a {@link PageRequest}
     * @return List of {@link PrideMongoProteinEvidence}
     */
    public Page<PrideMongoProteinEvidence> findAllProteinEvidences(String projectAccession, String assayAccession, String reportedAccession, PageRequest page) {

        StringJoiner filter = new StringJoiner(",");
        if (projectAccession != null && !projectAccession.isEmpty())
            filter.add("projectAccession=in=" + projectAccession);
        if (assayAccession != null && !assayAccession.isEmpty())
            filter.add("assayAccession=in=" + assayAccession);
        if (reportedAccession != null && !reportedAccession.isEmpty())
            filter.add("reportedAccession=in=" + reportedAccession);

        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters(filter.toString());
        return proteinMongoRepository.filterByAttributes(filters, page);
    }


    /**
     * This functions allows to find all the Peptides for an specific project Accession
     *
     * @param projectAccession Project Accession
     * @param page             Page to be retrieve
     * @return List of Peptides
     */
    public Page<PrideMongoPeptideEvidence> findPeptideEvidencesByProjectAccession(String projectAccession, Pageable page) {
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("projectAccession=in=" + projectAccession);
        Page<PrideMongoPeptideEvidence> psms = peptideMongoRepository.filterByAttributes(filters, page);
        log.debug("The number of PSMs for the Project Accession -- " + projectAccession + " -- " + psms.getTotalElements());
        return psms;
    }

    /**
     * This functions allows to find all the PSMs for an specific project Accession
     *
     * @param analysisAccession Analysis Accession
     * @param page              Page to be retrieve
     * @return List of PSMs
     */
    public Page<PrideMongoPeptideEvidence> findPeptideEvidencesByAssayAccession(String analysisAccession, Pageable page) {
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("analysisAccession=in=" + analysisAccession);
        Page<PrideMongoPeptideEvidence> psms = peptideMongoRepository.filterByAttributes(filters, page);
        log.debug("The number of PSMs for the Analysis Accession -- " + analysisAccession + " -- " + psms.getTotalElements());
        return psms;
    }


    /**
     * Counts how many PSMs are for a project accession.
     *
     * @param projectAccession the project accession to search for
     * @return the number of PSMs corresponding to the provided project accession
     */
    public long countByProjectAccession(String projectAccession) {
        return findPeptideEvidencesByProjectAccession(projectAccession, PageRequest.of(0, 10)).getTotalElements();
    }

    /**
     * Counts how many PSMs are for a assay accession and assay accession.
     *
     * @param assayAccession the assay accession to search for
     * @return the number of PSMs corresponding to the provided project accession
     */
    public long countByAssayAccession(String assayAccession) {
        return findPeptideEvidencesByProjectAccession(assayAccession, PageRequest.of(0, 10)).getTotalElements();
    }

    /**
     * Save an specific PSM in MongoDB
     *
     * @param peptideEvidence {@link PrideMongoPeptideEvidence}
     */
    public void savePeptideEvidence(PrideMongoPeptideEvidence peptideEvidence) {
        Optional<PrideMongoPeptideEvidence> currentPeptide = peptideMongoRepository
                .findPeptideByProteinAndAssayAccession(peptideEvidence.getProteinAccession(),
                        peptideEvidence.getAssayAccession(),
                        peptideEvidence.getPeptideAccession());
        if (currentPeptide.isPresent())
            peptideEvidence.setId(currentPeptide.get().getId());

        peptideMongoRepository.save(peptideEvidence);
    }

    /**
     * Save an specific Peptide Evidence in MongoDB
     *
     * @param peptideEvidence {@link PrideMongoPeptideEvidence}
     */
    public void insertPeptideEvidence(PrideMongoPeptideEvidence peptideEvidence) {
        peptideMongoRepository.save(peptideEvidence);
    }

    /**
     * Delete all the PSMs in the Mongo Database
     */
    public void deleteAllPeptideEvidences() {
        peptideMongoRepository.deleteAll();
    }


    public Optional<PrideMongoProteinEvidence> getProteinEvidence(String reportedAccession, String projectAccession, String assayAccession) {
        return proteinMongoRepository.findByAccessionAndAssayAccessionAndProjectAccession(reportedAccession,
                assayAccession, projectAccession);
    }

    public Page<PrideMongoPeptideEvidence> findPeptideEvidencesByProteinEvidence(String proteinAccession,
                                                                                 String projectAccession,
                                                                                 String assayAccession, PageRequest page) {

        Page<PrideMongoPeptideEvidence> peptides = peptideMongoRepository.findPeptideEvidenceByProteinEvidence(projectAccession, assayAccession, proteinAccession, page);

        return peptides;
    }

    /**
     * Return one {@link PrideMongoProteinEvidence} protein evidence for an specific project and assay.
     *
     * @param projectAccession Protect Accession
     * @param assayAccession   Assay Accession
     * @param reportedProtein  Reported protein accession
     * @return
     */
    public Optional<PrideMongoProteinEvidence> findProteinsEvidence(String projectAccession, String assayAccession,
                                                                    String reportedProtein) {
        return proteinMongoRepository.findByAccessionAndAssayAccessionAndProjectAccession(reportedProtein, assayAccession, projectAccession);
    }

    public Page<PrideMongoPeptideEvidence> findPeptideEvidences(String projectAccession, String assayAccession, String peptideSequence, String reportedProtein, String peptideAccession,
                                                                PageRequest page) {

        StringJoiner filter = new StringJoiner(",");
        if (projectAccession != null && !projectAccession.isEmpty())
            filter.add("projectAccession=in=" + projectAccession);
        if (assayAccession != null && !assayAccession.isEmpty())
            filter.add("assayAccession=in=" + assayAccession);
        if (peptideSequence != null && !peptideSequence.isEmpty())
            filter.add("peptideSequence=regex=" + peptideSequence);
        if (peptideAccession != null && !peptideAccession.isEmpty())
            filter.add("peptideAcccesion=regex=" + peptideAccession);
        if (reportedProtein != null && !reportedProtein.isEmpty())
            filter.add("proteinAccession=in=" + reportedProtein);

        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters(filter.toString());
        return peptideMongoRepository.filterByAttributes(filters, page);

    }

    /**
     * Search {@link PrideMongoPeptideEvidence} peptide evidences by USI or project accession
     *
     * @param usi       USI
     * @param accession project accession
     * @param page      Page
     * @return
     */
    public Page<PrideMongoPeptideEvidence> findPeptideEvidences(String usi, String accession, PageRequest page) {
        StringJoiner filter = new StringJoiner(",");
        if (accession != null && !accession.isEmpty())
            filter.add("projectAccession=in=" + accession);

        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters(filter.toString());
        return peptideMongoRepository.filterByAttributes(filters, page);
    }

    public Optional<PrideMongoPeptideEvidence> findPeptideEvidence(String projectAccession, String assayAccession,
                                                                   String reportedProtein, String peptideAccession) {
        return peptideMongoRepository.findPeptideByProteinAndAssayAccession(reportedProtein, assayAccession, peptideAccession);
    }

    public Set<String> findProteinAccessionByProjectAccessions(String projectAccession) {
        return peptideMongoRepository.findProteinAccessionByProjectAccessions(projectAccession);
    }

    public Set<String> findPeptideSequenceByProjectAccessions(String projectAccession) {
        return peptideMongoRepository.findPeptideSequenceByProjectAccessions(projectAccession);
    }

    /**
     * List all the peptide Evidecnes.
     *
     * @param page
     * @return
     */
    public Page<PrideMongoPeptideEvidence> listPeptideEvidences(PageRequest page) {
        return peptideMongoRepository.findAll(page);
    }

    /**
     * This method not not find for present of the protein in the database, only try to insert it.
     *
     * @param psmSummaryEvidence {@link PrideMongoPsmSummaryEvidence}
     */
    public void insertPsmSummaryEvidence(PrideMongoPsmSummaryEvidence psmSummaryEvidence) {
        psmMongoRepository.save(psmSummaryEvidence);
    }

    public void savePsmSummaryEvidence(PrideMongoPsmSummaryEvidence psmMongo) {
        Optional<PrideMongoPeptideEvidence> currentPSM = psmMongoRepository
                .findPsmSummaryByUsi(psmMongo.getUsi());
        currentPSM.ifPresent(prideMongoPeptideEvidence -> psmMongo.setId(prideMongoPeptideEvidence.getId()));

        psmMongoRepository.save(psmMongo);

    }

    public Page<PrideMongoPsmSummaryEvidence> findPsmSummaryEvidences(String projectAccession,
                                                                      String assayAccession,
                                                                      String unmodifiedPeptideSequence,
                                                                      String modifiedPeptideSequence,
                                                                      PageRequest page) {

        StringJoiner filter = new StringJoiner(",");
        if (!StringUtils.isEmpty(projectAccession))
            filter.add(PrideArchiveField.EXTERNAL_PROJECT_ACCESSION + "==" + projectAccession);
        if (!StringUtils.isEmpty(unmodifiedPeptideSequence))
            filter.add(PrideArchiveField.PEPTIDE_SEQUENCE + "==" + unmodifiedPeptideSequence);
        if (!StringUtils.isEmpty(modifiedPeptideSequence))
            filter.add(PrideArchiveField.MODIFIED_PEPTIDE_SEQUENCE + "==" + modifiedPeptideSequence);
        if (!StringUtils.isEmpty(assayAccession))
            filter.add(PrideArchiveField.PROTEIN_ASSAY_ACCESSION + "==" + assayAccession);

        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters(filter.toString());
        return psmMongoRepository.filterByAttributes(filters, page);

    }

    public Page<PrideMongoPsmSummaryEvidence> findPsmSummaryEvidences(String projectAccession,
                                                                      String fileName,
                                                                      String scan,
                                                                      String peptideSequence,
                                                                      Integer charge,
                                                                      String modification,
                                                                      String peptidoform,
                                                                      PageRequest page) {


        Criteria criteria = new Criteria();

        if (!StringUtils.isEmpty(projectAccession))
            criteria = criteria.andOperator(new Criteria(PrideArchiveField.EXTERNAL_PROJECT_ACCESSION).is(projectAccession));
        if (!StringUtils.isEmpty(fileName))
            criteria = criteria.andOperator(new Criteria(PrideArchiveField.FILE_NAME).regex(fileName));
        if (!StringUtils.isEmpty(scan))
            criteria = criteria.andOperator(new Criteria(PrideArchiveField.USI).regex("scan:" + scan + ":"));
        if (!StringUtils.isEmpty(peptideSequence))
            criteria = criteria.andOperator(new Criteria(PrideArchiveField.PEPTIDE_SEQUENCE).is(peptideSequence));
        if (charge != null)
            criteria = criteria.andOperator(new Criteria(PrideArchiveField.CHARGE).is(charge));
        if (!StringUtils.isEmpty(modification))
            criteria = criteria.andOperator(new Criteria(PrideArchiveField.USI).regex(modification));
//        if (!StringUtils.isEmpty(peptidoform)) //TODO peptidoform should be a separate field in DB..regex is too slow
//            criteria = criteria.andOperator(new Criteria(PrideArchiveField.USI).regex(peptidoform.replaceAll("\\[", "\\\\[")));

        return psmMongoRepository.filterByAttributes(criteria, page);
    }

    public Page<PrideMongoPsmSummaryEvidence> findPsmSummaryEvidences(List<String> usis,
                                                                      PageRequest page) {
        return psmMongoRepository.findPsmSummaryEvidencesByUsis(usis, page);

    }

    public Page<PrideMongoPsmSummaryEvidence> listPsmSummaryEvidences(PageRequest pageRequest) {
        return psmMongoRepository.findAll(pageRequest);
    }

    public Page<PrideMongoPeptideSummary> findPeptideSummary(String peptideSequence, String proteinAccession, PageRequest pageRequest) {
        if (proteinAccession != null && proteinAccession.trim().length() > 0) {
            if (peptideSequence == null || peptideSequence.trim().isEmpty()) {
                return pridePeptideSummaryMongoRepository.findByProteinAccession(proteinAccession, pageRequest);
            }
            return pridePeptideSummaryMongoRepository.findByPeptideSequenceAndProteinAccession(peptideSequence, proteinAccession, pageRequest);
        }
        if (peptideSequence == null || peptideSequence.trim().isEmpty()) {
            return pridePeptideSummaryMongoRepository.findAll(pageRequest);
        }
        return pridePeptideSummaryMongoRepository.findByPeptideSequence(peptideSequence, pageRequest);
    }

    public PrideMongoPeptideSummary findPeptideSummary(String peptideSequence, String proteinAccession) {
        return pridePeptideSummaryMongoRepository.
                findByPeptideSequenceAndProteinAccession(peptideSequence, proteinAccession);
    }

    public Page<PrideMongoPeptideSummary> findPeptideSummaryBypeptideSequenceOrProteinAccession(String peptideSequence, String proteinAccession, PageRequest pageRequest) {
        return pridePeptideSummaryMongoRepository.
                findByPeptideSequenceOrProteinAccession(peptideSequence, proteinAccession, pageRequest);
    }

    public Page<PrideMongoPeptideSummary> findAllPeptideSummary(PageRequest pageRequest) {
        return pridePeptideSummaryMongoRepository.findAll(pageRequest);
    }

    public Page<PrideMongoPeptideSummary> findAllUniprotPeptideSumamry(PageRequest pageRequest){
        return pridePeptideSummaryMongoRepository.findAllUniprot(pageRequest);
    }

    public Page<PrideMongoPeptideSummary> findUniprotPeptideSummaryByProperties(String peptideSequence,
                                                                                String proteinAccession,
                                                                                String proteinName,
                                                                                String gene,
                                                                                PageRequest pageRequest) {
        return pridePeptideSummaryMongoRepository.
                findUniprotPeptideSummaryByProperties(peptideSequence, proteinAccession,
                        proteinName, gene,
                        pageRequest);
    }

    public long getNumberProteinEvidences() {
        return proteinMongoRepository.count();
    }

    public long getNumberPeptideEvidences() {
        return peptideMongoRepository.count();
    }

    public long getNumberPSMEvidecnes() {
        return psmMongoRepository.count();
    }

    public void addSpectraUsi(String projectAccession) throws InterruptedException, ExecutionException {
        int nThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        int i = 0;
        List<Callable<PsmUpdater>> psmUpdaters = new ArrayList<>(nThreads);
        int size = 1000;
        boolean breakLoop = false;
        while (true) {
            log.info("Page number : " + i);
//            Page<PrideMongoPsmSummaryEvidence> prideMongoPsmSummaryEvidences = listPsmSummaryEvidences(PageRequest.of(i, size));
//            List<PrideMongoPsmSummaryEvidence> psms = prideMongoPsmSummaryEvidences.getContent();
//            String s = "mzspec:PXD000966:CPTAC_CompRef_00_iTRAQ_12_5Feb12_Cougar_11-10-11.mzML:scan:11850:[UNIMOD:214]YYWGGLYSWDMSK[UNIMOD:214]/2";
//            List<String> strings = Collections.singletonList(s);
//            List<PrideMongoPsmSummaryEvidence> psms = psmMongoRepository.findPsmSummaryEvidencesByUsis(strings, PageRequest.of(0, 100)).getContent();
            List<PrideMongoPsmSummaryEvidence> psms = psmMongoRepository.findPsmSummaryEvidencesByProjectAccession(projectAccession, PageRequest.of(i, size));
            i++;
            if (psms.isEmpty()) {
                breakLoop = true;
            }
            Map<String, String> map = new HashMap<>();
            psms.forEach(p -> {
                String usi = p.getUsi();
                String spectraUsi = usi.substring(0, org.apache.commons.lang3.StringUtils.ordinalIndexOf(usi, ":", 5));
                if (p.getSpectraUsi() == null || !p.getSpectraUsi().equals(spectraUsi)) {
                    p.setSpectraUsi(spectraUsi);
                    map.put(p.getUsi(), spectraUsi);
                }
            });
            if (map.size() > 0) {
                psmUpdaters.add(new PsmUpdater(map));
            }
            if (breakLoop || psmUpdaters.size() == nThreads) {
                List<Future<PsmUpdater>> fFutures = executorService.invokeAll(psmUpdaters);
                for (Future<PsmUpdater> future : fFutures) {
                    PsmUpdater psmUpdater = future.get();
                    log.info("Updated records: " + psmUpdater.getUpdatedRecords());
                }
                psmUpdaters.clear();
            }
            if (breakLoop) {
                break;
            }
        }
    }

    public long addSpectraUsi(Map<String, String> usiAndSpectraUsiMap) {
        return psmMongoRepository.bulkupdatePsms(usiAndSpectraUsiMap);
    }

    public Optional<PrideMongoPsmSummaryEvidence> findPsmSummaryEvidencesSpectraUsi(String spectraUSI) {
        return psmMongoRepository.findPsmSummaryBySpectraUsi(spectraUSI);
    }

    class PsmUpdater implements Callable {

        private Map<String, String> map;
        private long updatedRecords;

        public long getUpdatedRecords() {
            return updatedRecords;
        }

        PsmUpdater(Map<String, String> map) {
            this.map = map;
        }

        @Override
        public Object call() throws Exception {
            updatedRecords = psmMongoRepository.bulkupdatePsms(map);
            return this;
        }
    }
}
