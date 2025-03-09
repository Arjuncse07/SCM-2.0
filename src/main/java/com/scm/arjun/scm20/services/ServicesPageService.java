package com.scm.arjun.scm20.services;

import com.scm.arjun.scm20.Model.HomeCategories;
import com.scm.arjun.scm20.Model.HomeModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesPageService {


    public List<HomeModel> listOfHomes() {

        List<HomeModel> homeModelList = new ArrayList<>();

        List<HomeCategories> categories1 = List.of(
                new HomeCategories("COO1", "LuxryVilla", "Available", 50000.0,
                        "Mumbai", "Bandra","H001"),
                new HomeCategories("COO2", "Beach House", "Available", 70000.0, "Goa",
                        "Baga Beach","H001"));

        List<HomeCategories> categories2 = List.of(
                new HomeCategories("L001", "Modern Apartment", "SoldOut", 30000.0, "Delhi",
                        "Connaught Place","H002"),
                new HomeCategories("L002", "PentHouse", "Available", 100000.0,
                        "Banglore", "MG-Road","H002"));

        List<HomeModel> homeModelList1 = List.of(
                new HomeModel("Sweet Home", "H001", categories1),
                new HomeModel("Dream Villa", "H002", categories2)
        );

        homeModelList.addAll(homeModelList1);

        return homeModelList;
    }


}
