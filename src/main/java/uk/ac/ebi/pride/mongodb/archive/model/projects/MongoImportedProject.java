package uk.ac.ebi.pride.mongodb.archive.model.projects;

import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;
import uk.ac.ebi.pride.archive.dataprovider.reference.Reference;
import uk.ac.ebi.pride.archive.dataprovider.user.Contact;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(collection = PrideArchiveField.IMPORTED_PROJECTS_COLLECTION_NAME)
@TypeAlias("MongoImportedProject")
@SuperBuilder
public class MongoImportedProject extends MongoPrideProject {
    public MongoImportedProject() {
        super();
    }

    public MongoImportedProject(ObjectId id, String accession, String title, String description, List<Triple<String, String, CvParam>> submittedFileRelations, String sampleProcessing, String dataProcessing, Collection<Contact> submitters, Collection<Contact> headLab, Collection<String> keywords, Collection<String> projectTags, Collection<CvParam> quantificationMethods, String submissionType, Date publicationDate, Date submissionDate, Date updatedDate, Collection<CvParam> ptmList, List<Tuple<CvParam, Set<CvParam>>> samplesDescription, List<Tuple<CvParam, Set<CvParam>>> experimentalFactors, Collection<CvParam> instruments, Collection<CvParam> softwareList, Collection<CvParam> experimentTypes, Collection<Reference> references, Collection<CvParam> attributes, String doi, List<String> omicsLinks, List<String> countries, boolean publicProject) {
        super(id, accession, title, description, submittedFileRelations, sampleProcessing, dataProcessing, submitters, headLab, keywords, projectTags, quantificationMethods, submissionType, publicationDate, submissionDate, updatedDate, ptmList, samplesDescription, experimentalFactors, instruments, softwareList, experimentTypes, references, attributes, doi, omicsLinks, countries, publicProject);
    }
}
