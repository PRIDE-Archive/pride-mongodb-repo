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





    @Override
    public TitleConstants getTitle() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getAffiliation() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public String getOrcid() {
        return null;
    }

    @Override
    public Comparable getId() {
        return null;
    }
}
