package uk.ac.ebi.pride.mongodb.archive.model.assay;

import lombok.Builder;
import lombok.Data;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;

import java.util.List;

@Data
@Builder
public class MongoAssayFile {

    String fileName;
    String fileAccession;
    CvParam fileCategory;
    String fileSourceType;
    List<MongoAssayFile> relatedFiles;

}
