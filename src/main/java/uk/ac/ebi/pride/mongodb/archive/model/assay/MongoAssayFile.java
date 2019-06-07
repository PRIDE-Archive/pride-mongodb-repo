package uk.ac.ebi.pride.mongodb.archive.model.assay;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
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
