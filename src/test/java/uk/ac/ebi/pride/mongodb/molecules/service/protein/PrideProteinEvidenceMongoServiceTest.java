package uk.ac.ebi.pride.mongodb.molecules.service.protein;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.mongodb.molecules.config.PrideProjectFongoTestConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectFongoTestConfig.class})
public class PrideProteinEvidenceMongoServiceTest {

//    @Autowired
//    PrideMoleculesMongoService mongoService;
//    private MZTabFile mzTabFileP1A1;
//    private MZTabFile mzTabFileP1A2;
//    private MZTabFile mzTabFileP1A3;
//    private MZTabFile mzTabFileP2A1;
//
//    @Before
//    public void setUp() throws URISyntaxException, IOException {
//        mongoService.deleteAll();
//        mzTabFileP1A1 = new MZTabFileParser(new File(Objects.requireNonNull(MongoPsmMzTabBuilderTest.class.getClassLoader().getResource("submissions/2014/01/PXD000581/generated/PRIDE_Exp_Complete_Ac_32411.mztab")).toURI()),System.out).getMZTabFile();
//        mzTabFileP1A2 = new MZTabFileParser(new File(Objects.requireNonNull(MongoPsmMzTabBuilderTest.class.getClassLoader().getResource("submissions/2014/01/PXD000581/generated/PRIDE_Exp_Complete_Ac_32416.mztab")).toURI()), System.out).getMZTabFile();
//        mzTabFileP1A3 = new MZTabFileParser(new File(Objects.requireNonNull(MongoPsmMzTabBuilderTest.class.getClassLoader().getResource("submissions/2014/01/PXD000581/generated/PRIDE_Exp_Complete_Ac_32417_null.mztab")).toURI()), System.out).getMZTabFile();
//        mzTabFileP2A1 = new MZTabFileParser(new File(Objects.requireNonNull(MongoPsmMzTabBuilderTest.class.getClassLoader().getResource("submissions/TST000121/generated/PRIDE_Exp_Complete_Ac_00001.mztab")).toURI()), System.out).getMZTabFile();
//    }
//
//    @Test
//    public void saveTest() {
//
//        PrideMongoProteinEvidence protein = PrideMongoProteinEvidence.builder()
//                .reportedAccession("sp|Q672I1|POLG_SVSAP")
//                .projectAccession("PXD000433")
//                .proteinSequence("MVSKPFKPIVLNATFEWQVFKRCYLRVAPREAFCENLSELHHYFARRVNAWLKHATRTLP\n" +
//                        "DGYTFVEEGLLDMFGTKAPDSVQEGTLFRELFGVDQTEQFPLSLADLAKLQGELVDATRT\n" +
//                        "PGHALRQKYTMTTIQDLINKITKVVPVQATLTEMHARRQFERERADLFHELPLVDEDAVS\n" +
//                        "QPKTYFYTMWRQVVKKGKAYFCPLVKTSAWRTKISAITEPIKDFLIAFCQAVQQEMGVNP\n" +
//                        "QYLQLAWLQKLKPTTLTIILQQHKYTVSGWLATMTALVEVYSNLFDDLRKSSVAIVSSIG\n" +
//                        "AFFDICKDFVSQVVELVKTTFTAQGPTDLGWAAVLAGAAMILLKMSGCPGVIGMWTKVLK\n" +
//                        "ICGGITTITAAARGVRWLKDLYEEAEGRRLAKMYMARGAALIELAASREVTGIDELKGLL\n" +
//                        "DCFTILIEEGTELIHKFGTSPLAGLVRTYVSELETQANNIRSTIKLDTPRRVPVVIILTG\n" +
//                        "APGIGKTRLAQYVGQRFGKTSNFSVAVDHHDGYTGNTVCIWDEFDVDSKGAFVETMIGIA\n" +
//                        "NTAPFPLNCDRVENKGRVFTSDYVICTSNYPTSVIPDNPRAAAFYRRVLTVDVSAPDLEE\n" +
//                        "WKKRNPGKRPTPDLYQDDFSHLKLMLRPYLGYNPDGDTLEGPRVAPTQISIAGLITLMER\n" +
//                        "RFKEQAGPLQNLWLQVPKTLVEQSTNMVKAFMYANRAVCDVIPNPATRDIAETALTKIFV\n" +
//                        "CGTAPPPEFVGKHIVITGIEVGDASIANSLLSMFTTTTRLSAAAQREYMYRVWSPLIHIQ\n" +
//                        "DRSINTQNLPYINRVIPVTSHWDFLRGLRHHLGFTSIPGMWKAFQGWRTSQGIVDFVAHH\n" +
//                        "MADVTFPSNPECTIFRTPDADVVFYTFGSYVCFATPARVPYVGTPPTTIHSNTPRCMTWG\n" +
//                        "ETIALLCEVVAEFVLHFGPVILSAANIAYLMTRGSRTEEAKGKTKHGRGMRHGHRAGVSL\n" +
//                        "SDDEYDEWRDLMRDWRRDMSVNDFLMLRERSALGVDDEDEARYRAWLEIRAMRMAGGAYT\n" +
//                        "HATIIGRGGVRDEIIRTAPRRAPTRPQQHYEEEAPTAIVEFTQGGDHIGYGVHIGNGNVI\n" +
//                        "TVTHVASTSDEVNGSAFKITRTVGETTWVQGPFSQLPHMQIGSGSPVYFTTRLHPVFTIS\n" +
//                        "EGTFETPNITVNGFHVRIMNGYPTKKGDCGLPYFNSNRQLVALHAGTDTQGETKVAQRVV\n" +
//                        "KEVTTQDEFQWKGLPVVKSGLDVGGMPTGTRYHRSPAWPEEQPGETHAPAPFGAGDKRYT\n" +
//                        "FSQTEMLVNGLKPYTEPTAGVPPQLLSRAVTHVRSYIETIIGTHRSPVLTYHQACELLER\n" +
//                        "TTSCGPFVQGLKGDYWDEEQQQYTGVLANHLEQAWDKANKGIAPRNAYKLALKDELRPIE\n" +
//                        "KNKAGKRRLLWGCDAATTLIATAAFKAVATRLQVVTPMTPVAVGINMDSVQMQVMNDSLK\n" +
//                        "GGVLYCLDYSKWDSTQNPAVTAASLAILERFAEPHPIVSCAIEALSSPAEGYVNDIKFVT\n" +
//                        "RGGLPSGMPFTSVVNSINHMIYVAAAILQAYESHNVPYTGNVFQVETVHTYGDDCMYSVC\n" +
//                        "PATASIFHAVLANLTSYGLKPTAADKSDAIKPTNTPVFLKRTFTQTPHGVRALLDITSIT\n" +
//                        "RQFYWLKANRTSDPSSPPAFDRQARSAQLENALAYASQHGPVVFDTVRQIAIKTAQGEGL\n" +
//                        "VLVNTNYDQALATYNAWFIGGTVPDPVGHTEGTHKIVFEMEGNGSNPEPKQSNNPMVVDP\n" +
//                        "PGTTGPTTSHVVVANPEQPNGAAQRLELAVATGAIQSNVPEAIRNCFAVFRTFAWNDRMP\n" +
//                        "TGTFLGSISLHPNINPYTAHLSGMWAGWGGSFEVRLSISGSGVFAGRIIASVIPPGVDPS\n" +
//                        "SIRDPGVLPHAFVDARITEPVSFMIPDVRAVDYHRMDGAEPTCSLGFWVYQPLLNPFSTT\n" +
//                        "AVSTCWVSVETKPGGDFDFCLLRPPGQQMENGVSPEGLLPRRLGYSRGNRVGGLVVGMVL\n" +
//                        "VAEHKQVNRHFNSNSVTFGWSTAPVNPMAAEIVTNQAHSTSRHAWLSIGAQNKGPLFPGI\n" +
//                        "PNHFPDSCASTIVGAMDTSLGGRPSTGVCGPAISFQNNGDVYENDTPSVMFATYDPLTSG\n" +
//                        "TGVALTNSINPASLALVRISNNDFDTSGFANDKNVVVQMSWEMYTGTNQIRGQVTPMSGT\n" +
//                        "NYTFTSTGANTLVLWQERMLSYDGHQAILYSSQLERTAEYFQNDIVNIPENSMAVFNVET\n" +
//                        "NSASFQIGIRPDGYMVTGGSIGINVPLEPETRFQYVGILPLSAALSGPSGNMGRARRVFQ")
//                .proteinDescription("Genome polyprotein OS=Sapporo virus (isolate GI/Human/Germany/pJG-Sap01) OX=291175 PE=3 SV=1")
//                .proteinGroupMembers(Collections.singleton("sp|Q672I1|POLG_SVSAP"))
//                .isDecoy(true)
//                .build();
//
//        protein.addAttribute(new DefaultCvParam("MS", "MS:1001013", "database name", "phytophtora_infestans_rnd"));
//        protein.addAttribute(new DefaultCvParam("MS", "MS:1001016", "database version", "20100422.fasta"));
//
//        DefaultIdentifiedModification mod = new DefaultIdentifiedModification(new DefaultCvParam("UNIMOD", "UNIMOD:35", "Oxidation", "15.994915"), null, null, null);
//        protein.addIdentifiedModification(mod);
//
//        System.out.println(protein.toString());
//
//        mongoService.saveProteinEvidences(protein);
//        mongoService.findAll().stream().forEach(PrideMongoProteinEvidence::toString);
//
//        Assert.assertTrue(mongoService.findAll().size() == 1);
//
//    }
//
//
//    @Test
//    public void saveMzTabTest() {
//
//        PrideMongoProteinEvidence protein = PrideMongoProteinEvidence.builder()
//                .reportedAccession("sp|Q672I1|POLG_SVSAP")
//                .projectAccession("PXD000433")
//                .proteinSequence("MVSKPFKPIVLNATFEWQVFKRCYLRVAPREAFCENLSELHHYFARRVNAWLKHATRTLP\n" +
//                        "DGYTFVEEGLLDMFGTKAPDSVQEGTLFRELFGVDQTEQFPLSLADLAKLQGELVDATRT\n" +
//                        "PGHALRQKYTMTTIQDLINKITKVVPVQATLTEMHARRQFERERADLFHELPLVDEDAVS\n" +
//                        "QPKTYFYTMWRQVVKKGKAYFCPLVKTSAWRTKISAITEPIKDFLIAFCQAVQQEMGVNP\n" +
//                        "QYLQLAWLQKLKPTTLTIILQQHKYTVSGWLATMTALVEVYSNLFDDLRKSSVAIVSSIG\n" +
//                        "AFFDICKDFVSQVVELVKTTFTAQGPTDLGWAAVLAGAAMILLKMSGCPGVIGMWTKVLK\n" +
//                        "ICGGITTITAAARGVRWLKDLYEEAEGRRLAKMYMARGAALIELAASREVTGIDELKGLL\n" +
//                        "DCFTILIEEGTELIHKFGTSPLAGLVRTYVSELETQANNIRSTIKLDTPRRVPVVIILTG\n" +
//                        "APGIGKTRLAQYVGQRFGKTSNFSVAVDHHDGYTGNTVCIWDEFDVDSKGAFVETMIGIA\n" +
//                        "NTAPFPLNCDRVENKGRVFTSDYVICTSNYPTSVIPDNPRAAAFYRRVLTVDVSAPDLEE\n" +
//                        "WKKRNPGKRPTPDLYQDDFSHLKLMLRPYLGYNPDGDTLEGPRVAPTQISIAGLITLMER\n" +
//                        "RFKEQAGPLQNLWLQVPKTLVEQSTNMVKAFMYANRAVCDVIPNPATRDIAETALTKIFV\n" +
//                        "CGTAPPPEFVGKHIVITGIEVGDASIANSLLSMFTTTTRLSAAAQREYMYRVWSPLIHIQ\n" +
//                        "DRSINTQNLPYINRVIPVTSHWDFLRGLRHHLGFTSIPGMWKAFQGWRTSQGIVDFVAHH\n" +
//                        "MADVTFPSNPECTIFRTPDADVVFYTFGSYVCFATPARVPYVGTPPTTIHSNTPRCMTWG\n" +
//                        "ETIALLCEVVAEFVLHFGPVILSAANIAYLMTRGSRTEEAKGKTKHGRGMRHGHRAGVSL\n" +
//                        "SDDEYDEWRDLMRDWRRDMSVNDFLMLRERSALGVDDEDEARYRAWLEIRAMRMAGGAYT\n" +
//                        "HATIIGRGGVRDEIIRTAPRRAPTRPQQHYEEEAPTAIVEFTQGGDHIGYGVHIGNGNVI\n" +
//                        "TVTHVASTSDEVNGSAFKITRTVGETTWVQGPFSQLPHMQIGSGSPVYFTTRLHPVFTIS\n" +
//                        "EGTFETPNITVNGFHVRIMNGYPTKKGDCGLPYFNSNRQLVALHAGTDTQGETKVAQRVV\n" +
//                        "KEVTTQDEFQWKGLPVVKSGLDVGGMPTGTRYHRSPAWPEEQPGETHAPAPFGAGDKRYT\n" +
//                        "FSQTEMLVNGLKPYTEPTAGVPPQLLSRAVTHVRSYIETIIGTHRSPVLTYHQACELLER\n" +
//                        "TTSCGPFVQGLKGDYWDEEQQQYTGVLANHLEQAWDKANKGIAPRNAYKLALKDELRPIE\n" +
//                        "KNKAGKRRLLWGCDAATTLIATAAFKAVATRLQVVTPMTPVAVGINMDSVQMQVMNDSLK\n" +
//                        "GGVLYCLDYSKWDSTQNPAVTAASLAILERFAEPHPIVSCAIEALSSPAEGYVNDIKFVT\n" +
//                        "RGGLPSGMPFTSVVNSINHMIYVAAAILQAYESHNVPYTGNVFQVETVHTYGDDCMYSVC\n" +
//                        "PATASIFHAVLANLTSYGLKPTAADKSDAIKPTNTPVFLKRTFTQTPHGVRALLDITSIT\n" +
//                        "RQFYWLKANRTSDPSSPPAFDRQARSAQLENALAYASQHGPVVFDTVRQIAIKTAQGEGL\n" +
//                        "VLVNTNYDQALATYNAWFIGGTVPDPVGHTEGTHKIVFEMEGNGSNPEPKQSNNPMVVDP\n" +
//                        "PGTTGPTTSHVVVANPEQPNGAAQRLELAVATGAIQSNVPEAIRNCFAVFRTFAWNDRMP\n" +
//                        "TGTFLGSISLHPNINPYTAHLSGMWAGWGGSFEVRLSISGSGVFAGRIIASVIPPGVDPS\n" +
//                        "SIRDPGVLPHAFVDARITEPVSFMIPDVRAVDYHRMDGAEPTCSLGFWVYQPLLNPFSTT\n" +
//                        "AVSTCWVSVETKPGGDFDFCLLRPPGQQMENGVSPEGLLPRRLGYSRGNRVGGLVVGMVL\n" +
//                        "VAEHKQVNRHFNSNSVTFGWSTAPVNPMAAEIVTNQAHSTSRHAWLSIGAQNKGPLFPGI\n" +
//                        "PNHFPDSCASTIVGAMDTSLGGRPSTGVCGPAISFQNNGDVYENDTPSVMFATYDPLTSG\n" +
//                        "TGVALTNSINPASLALVRISNNDFDTSGFANDKNVVVQMSWEMYTGTNQIRGQVTPMSGT\n" +
//                        "NYTFTSTGANTLVLWQERMLSYDGHQAILYSSQLERTAEYFQNDIVNIPENSMAVFNVET\n" +
//                        "NSASFQIGIRPDGYMVTGGSIGINVPLEPETRFQYVGILPLSAALSGPSGNMGRARRVFQ")
//                .proteinDescription("Genome polyprotein OS=Sapporo virus (isolate GI/Human/Germany/pJG-Sap01) OX=291175 PE=3 SV=1")
//                .proteinGroupMembers(Collections.singleton("sp|Q672I1|POLG_SVSAP"))
//                .isDecoy(true)
//                .build();
//
//        protein.addAttribute(new DefaultCvParam("MS", "MS:1001013", "database name", "phytophtora_infestans_rnd"));
//        protein.addAttribute(new DefaultCvParam("MS", "MS:1001016", "database version", "20100422.fasta"));
//
//        DefaultIdentifiedModification mod = new DefaultIdentifiedModification(new DefaultCvParam("UNIMOD", "UNIMOD:35", "Oxidation", "15.994915"),null, null, null);
//        protein.addIdentifiedModification(mod);
//
//        System.out.println(protein.toString());
//
//        mongoService.saveProteinEvidences(protein);
//        mongoService.findAll().stream().forEach(PrideMongoProteinEvidence::toString);
//
//        Assert.assertTrue(mongoService.findAll().size() == 1);
//
//    }
}