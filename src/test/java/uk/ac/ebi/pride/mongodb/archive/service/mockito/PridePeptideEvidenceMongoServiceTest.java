package uk.ac.ebi.pride.mongodb.archive.service.mockito;

import org.springframework.boot.test.context.SpringBootTest;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectFongoTestConfig;

/**
 * @author ypriverol
 */
@SpringBootTest(classes = {PrideProjectFongoTestConfig.class})
public class PridePeptideEvidenceMongoServiceTest {

//    public static final long ZERO_DOCS = 0L;
//    public static final long SINGLE_DOC = 1L;
//    // PSM 1 test data
//    private static final String PSM_1_ID = "TEST-PSM-ID1";
//    private static final String PSM_1_REPORTED_ID = "TEST-PSM-REPORTED-ID1";
//    private static final String PSM_1_SEQUENCE = "MLVEYTQNQKEVLSEKEKKLEEYK";
//    private static final String PSM_1_SPECTRUM = "SPECTRUM-ID1";
//    // PSM 2 test data
//    private static final String PSM_2_ID = "TEST-PSM-ID2";
//    private static final String PSM_2_REPORTED_ID = "TEST-PSM-REPORTED-ID2";
//    private static final String PSM_2_SEQUENCE = "YSQPEDSLIPFFEITVPESQLTVSQFTLPK";
//    private static final String PSM_2_SPECTRUM = "SPECTRUM-ID1";
//    // PSM 3 test data
//    private static final String PSM_3_ID = "TEST-PSM-ID3";
//    private static final String PSM_3_REPORTED_ID = "TEST-PSM-REPORTED-ID3";
//    private static final String PSM_3_SEQUENCE = "YSQPEDSLIPFFEITVPE";
//    private static final String PSM_3_SPECTRUM = "SPECTRUM-ID3";
//
//    // Proteins
//    private static final String PROTEIN_1_ACCESSION = "PROTEIN-1-ACCESSION";
//    private static final String PROTEIN_2_ACCESSION = "PROTEIN-2-ACCESSION";
//    private static final String PARTIAL_ACCESSION_WILDCARD = "PROTEIN-*";
//
//    // Projects and assays
//    private static final String PROJECT_1_ACCESSION = "PROJECT-1-ACCESSION";
//    private static final String PROJECT_2_ACCESSION = "PROJECT-2-ACCESSION";
//    private static final String ANALYSIS_1_ACCESSION = "ASSAY-1-ACCESSION";
//    private static final String ANALYSIS_2_ACCESSION = "ASSAY-2-ACCESSION";
//    // Modifications
//    private static final Integer MOD_1_POS = 3;
//    private static final Integer MOD_2_POS = 5;
//    private static final String MOD_1_ACCESSION = "MOD:00696";
//    private static final String MOD_2_ACCESSION = "MOD:00674";
//    private static final String MOD_1_NAME = "phosphorylated residue";
//    private static final String MOD_2_NAME = "amidated residue";
//    // Neutral Loss without mod
//    private static final String NEUTRAL_LOSS_ACC = "MS:1001524";
//    private static final String NEUTRAL_LOSS_NAME = "fragment neutral loss";
//    private static final String NEUTRAL_LOSS_VAL = "63.998283";
//
//    private List<PrideMongoPeptideEvidence> mongoPsms = new ArrayList<>();
//
////    @Autowired
////    private PridePeptideEvidenceMongoService mongoService;
//
//    /** Ensures all existing data are deleted, and inserts test data. */
//    @Before
//    public void setUp(){
//        mongoService.deleteAll();
//        insertTestData();
//    }
//
//    /** Inserts test data. */
//    private void insertTestData() {
//        addPsm(
//                PSM_1_ID,
//                PSM_1_REPORTED_ID,
//                PSM_1_SEQUENCE,
//                PSM_1_SPECTRUM,
//                PROTEIN_1_ACCESSION,
//                PROJECT_1_ACCESSION,
//                ANALYSIS_1_ACCESSION);
//        addPsm(
//                PSM_2_ID,
//                PSM_2_REPORTED_ID,
//                PSM_2_SEQUENCE,
//                PSM_2_SPECTRUM,
//                PROTEIN_2_ACCESSION,
//                PROJECT_2_ACCESSION,
//                ANALYSIS_2_ACCESSION);
//        addPsm(
//                PSM_3_ID,
//                PSM_3_REPORTED_ID,
//                PSM_3_SEQUENCE,
//                PSM_3_SPECTRUM,
//                PROTEIN_2_ACCESSION,
//                PROJECT_2_ACCESSION,
//                ANALYSIS_2_ACCESSION);
//    }
//
////    /** Tests searching by ID. */
////    @Test
////    public void testSearchById() {
////        PrideMongoPeptideEvidence psm1 = mongoService.findByAccession(PSM_1_ID);
////        Assert.assertNotNull(psm1);
////        Assert.assertEquals(PSM_1_ID, psm1.getAccession());
////
////        PrideMongoPeptideEvidence psm2 = mongoService.findByAccession(PSM_2_ID);
////        Assert.assertNotNull(psm2);
////        Assert.assertEquals(PSM_2_ID, psm2.getAccession());
////
////        PrideMongoPeptideEvidence psm3 = mongoService.findByAccession(PSM_3_ID);
////        Assert.assertNotNull(psm3);
////        Assert.assertEquals(PSM_3_ID, psm3.getAccession());
////
////        Assert.assertEquals(
////                3,
////                mongoService
////                        .findByIdIn(mongoPsms.stream().map(PrideMongoPeptideEvidence::getAccession).collect(Collectors.toList()))
////                        .size());
////        Assert.assertEquals(
////                3,
////                mongoService
////                        .findByIdIn(mongoPsms.stream().map(PrideMongoPeptideEvidence::getAccession).collect(Collectors.toList()), new Sort(Sort.Direction.DESC, PrideArchiveField.ACCESSION))
////                        .size());
////    }
//
//    /** Tests counting by project accession. */
//    @Test
//    public void testCountByProjectAccession() {
//        long totalFound = mongoService.countByProjectAccession(PROJECT_1_ACCESSION);
//        Assert.assertEquals(1, totalFound);
//        totalFound = mongoService.countByProjectAccession(PROJECT_2_ACCESSION);
//        Assert.assertEquals(2, totalFound);
//    }
//
//    /**
//     * Adds a PSM
//     *
//     * @param psmId the PSM ID
//     * @param psmReportedId the PSM reported ID
//     * @param psmSequence the PSM sequence
//     * @param psmSpectrum the PSM spectrum
//     * @param proteinAcccession the protein accession
//     * @param projectAccession the project accession
//     * @param assayAccession the assay accession
//     */
//    private void addPsm(
//            String psmId,
//            String psmReportedId,
//            String psmSequence,
//            String psmSpectrum,
//            String proteinAcccession,
//            String projectAccession,
//            String assayAccession) {
//
//        IdentifiedModificationProvider mod1 = new IdentifiedModification(new CvParam(MOD_1_ACCESSION, MOD_1_NAME), new CvParam("MS",NEUTRAL_LOSS_ACC, NEUTRAL_LOSS_NAME,NEUTRAL_LOSS_VAL), Collections.singletonList(MOD_1_POS));
//        IdentifiedModificationProvider mod2 = new IdentifiedModification(new CvParam(MOD_2_ACCESSION, MOD_2_NAME), Collections.singletonList(MOD_2_POS));
//
//        PrideMongoPeptideEvidence psm = PrideMongoPeptideEvidence.builder().accession(psmId).reportedFileID(psmReportedId)
//                .peptideSequence(psmSequence)
//                .reportedProteinAccession(proteinAcccession)
//                .projectAccession(projectAccession)
//                .ptmList(Arrays.asList(mod1, mod2))
//        .build();
//
//        mongoService.saveProteinEvidences(psm);
//        mongoPsms.add(psm);
//    }
//
//
//    @Test
//    public void searchWildcard(){
//        Page<PrideMongoPeptideEvidence> psms = mongoService.searchPSMs("reportedProteinAccession=regex=" + PARTIAL_ACCESSION_WILDCARD, PageRequest.of(0,10));
//        Assert.assertEquals(3, psms.getTotalElements());
//    }
}