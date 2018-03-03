package uk.ac.ebi.pride.mongodb.authentication.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.user.UserProvider;
import uk.ac.ebi.pride.archive.dataprovider.utils.TitleConstants;

import java.util.Date;

/**
 * A PRIDE User. That can access to the data to update/download .
 *
 * @author Yasset PErez-Riverol
 * @version $id$
 *
 */

@Document(collection = "user")
public class User implements UserProvider{

    /** the email of the user would be consider as the Id of the User **/
    @Id
    private String email;

    /** Password in the system*/
    private String password;

    /** Creation Date **/
    private Date dateCreated;

    /** Update Date */
    private Date dateUpdated;

    /** Title Constants **/
    private TitleConstants title;

    /** Orcid ID **/
    private String orcid;

    /** First Name **/
    private String firstName;

    /** Last Name **/
    private String lastName;

    /** Affiliation of the User in String representation **/
    private String affiliation;

    /** The country of the user the default values would be in {@link uk.ac.ebi.pride.archive.dataprovider.utils.Country} */
    private String country;

    /** Token used to conenct the user to PRIDE Resources **/
    String accessToken;


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Date getCreateAt() {
        return dateCreated;
    }

    @Override
    public Date getUpdateAt() {
        return dateUpdated;
    }

    @Override
    public TitleConstants getTitle() {
        return title;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getAffiliation() {
        return affiliation;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getCountry() {
        return country;
    }


    @Override
    public String getOrcid() {
        return orcid;
    }

    @Override
    public Comparable getId() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public void setTitle(TitleConstants title) {
        this.title = title;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
