package uk.ac.ebi.pride.mongodb.archive.model.sdrf;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.List;
import java.util.Map;


/**
 * Pride project information collections contains the information for the corresponding project in PRIDE including information about the title, accession, samples, etc. Please
 * read the specification of a PRIDE project here:
 *
 * @author Yasset Perez-Riverol
 */
@Document(collection = PrideArchiveField.PRIDE_SDRF_COLLECTION_NAME)
@Data
@Builder
@TypeAlias("MongoPrideSdrf")
public class MongoPrideSdrf {

    @Id
    private ObjectId id;

    private String projectAccession;

    @Field(value = PrideArchiveField.SAMPLE_FILE_CHECKSUM)
    private String filechecksum;

    @Field(value = PrideArchiveField.SAMPLE)
    Map<String,String> sample;
}
