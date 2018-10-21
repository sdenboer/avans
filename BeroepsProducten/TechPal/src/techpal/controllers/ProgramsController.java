package techpal.controllers;

import techpal.models.Program;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ProgramsController {

    public ProgramsController(){
    }

    public static ArrayList<Program> alPrograms = new ArrayList<>();

    public static void setPrograms() {
        DbConnector conn = new DbConnector();
        String sqlProg = "SELECT progNm FROM programmas";
        ResultSet resProg = conn.getData(sqlProg);
        try {
            while (resProg.next()) {
                Program program = new Program();
                program.setProgNm(resProg.getString("progNm"));
                ProgramsController.alPrograms.add(program);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
