package uk.ac.ebi.pride.mongodb.archive.model.assay;

import lombok.Builder;
import lombok.Data;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;

import java.util.List;

@Data
@Builder
public class MongoAssayFile {

    String fileName;
    String fileAccession;
    MongoCvParam fileCategory;
    String fileSourceType;
    List<MongoAssayFile> relatedFiles;

}
