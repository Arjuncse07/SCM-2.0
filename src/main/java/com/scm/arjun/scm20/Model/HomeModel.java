package com.scm.arjun.scm20.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HomeModel {
    private String homeName;
    private String homeId;
    private List<HomeCategories> homeCategoriesList;



}
