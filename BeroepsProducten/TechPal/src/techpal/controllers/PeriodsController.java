package techpal.controllers;

import techpal.models.Period;

import java.sql.ResultSet;
import java.util.ArrayList;

public class PeriodsController {
    //the longer I work with the word "period", the more I regret my choice of vocabulary...
    // I mean....periods controller.... what the fuck...

    public PeriodsController() {
    }

    public static ArrayList<Period> alPeriods = new ArrayList<>();

    public static void setPeriods() {
        DbConnector conn = new DbConnector();
        String sqlPer = "SELECT per FROM periodes";
        ResultSet resPer = conn.getData(sqlPer);
        try {
            while (resPer.next()) {
                Period period = new Period();
                period.setPer(resPer.getString("per"));
                PeriodsController.alPeriods.add(period);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
