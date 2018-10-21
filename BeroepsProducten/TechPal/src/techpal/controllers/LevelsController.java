package techpal.controllers;

import techpal.models.Level;

import java.sql.ResultSet;
import java.util.ArrayList;

public class LevelsController {

    public LevelsController() {
    }

    public static ArrayList<Level> alLevels = new ArrayList<>();

    public static void setLevels() {
        DbConnector conn = new DbConnector();
        String sqlLvl = "SELECT nivOm FROM niveaus";
        ResultSet resLvl = conn.getData(sqlLvl);
        try {
            while (resLvl.next()) {
                Level level = new Level();
                level.setLvl(resLvl.getString("nivOm"));
                LevelsController.alLevels.add(level);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
