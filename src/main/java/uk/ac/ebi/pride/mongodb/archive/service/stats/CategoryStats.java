package uk.ac.ebi.pride.mongodb.archive.service.stats;

import lombok.Builder;
import lombok.Data;
import uk.ac.ebi.pride.archive.dataprovider.utils.Tuple;

import java.util.*;

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
 * Created by ypriverol (ypriverol@gmail.com) on 03/08/2018.
 */

@Builder
@Data
public class CategoryStats {

    /** Category under study **/
    Tuple<String, Integer> category;

    /** SubCategory List **/
    Set<CategoryStats> subCategories = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryStats that = (CategoryStats) o;
        return Objects.equals(category.getKey(), that.category.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(category.getKey());
    }
}
