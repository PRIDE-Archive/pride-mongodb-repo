package uk.ac.ebi.pride.mongodb.archive.model.user;

import uk.ac.ebi.pride.archive.dataprovider.user.ContactProvider;
import uk.ac.ebi.pride.archive.dataprovider.utils.TitleConstants;

import java.io.Serializable;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 15/06/2018.
 */
public class MongoContact implements ContactProvider, Serializable {

    private TitleConstants title;

    /** First Name **/
    private String firstName;

    /** Last Name **/
    private String lastName;

    /** Contact Identifier **/
    private String identifier;

    /** Affiliation **/
    private String affiliation;

    /** Email **/
    private String email;

    /** Country of the Contact **/
    private String country;

    /** ORCID ID **/
    private String orcid;

    /**
     * Default empty constructor
     */
    public MongoContact() { }

    /**
     * Dafault Constructor for ContactProvider
     * @param title Title
     * @param firstName FirstName
     * @param lastName LastName
     * @param identifier Identifier
     * @param affiliation Affiliation
     * @param email Email
     * @param country Country
     * @param orcid ORCID
     */
    public MongoContact(TitleConstants title, String firstName, String lastName,
                          String identifier, String affiliation, String email, String country, String orcid) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identifier = identifier;
        this.affiliation = affiliation;
        this.email = email;
        this.country = country;
        this.orcid = orcid;

    }

    /**
     * The minimum information for a Contact.
     * @param firstName FirstName
     * @param lastName LastName
     * @param affiliation Affiliation
     * @param country Country
     */
    public MongoContact(String firstName, String lastName, String affiliation, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.affiliation = affiliation;
        this.country = country;
    }

    @Override
    public TitleConstants getTitle() {
        return title;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public Comparable getId() {
        return identifier;
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

    public void setTitle(TitleConstants title) {
        this.title = title;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }
}
