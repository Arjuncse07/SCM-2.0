package com.scm.arjun.scm20.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class HomeCategories {

    private String homeCategoryId;
    private String homeCategoryName;
    private String homeCategoryStatus;
    private Double homeCategoryPrice;
    private String homeLocation;
    private String homeNearestLocation;
    private String homeId;

}
