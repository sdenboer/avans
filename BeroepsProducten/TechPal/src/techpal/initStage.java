package techpal;

import techpal.Models.Device;
import techpal.Models.Level;
import techpal.Models.Period;
import techpal.Models.Program;

import java.sql.ResultSet;

public class initStage {
    public initStage() {
    }

    public static void setPeriods() {
        DbConnector conn = new DbConnector();
        String sqlPer = "SELECT per FROM periodes";
        ResultSet resPer = conn.getData(sqlPer);
        try {
            while (resPer.next()) {
                Period period = new Period();
                period.setPer(resPer.getString("per"));
                Session.listPeriods.add(period);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setLevels() {
        DbConnector conn = new DbConnector();
        String sqlLvl = "SELECT nivOm FROM niveaus";
        ResultSet resLvl = conn.getData(sqlLvl);
        try {
            while (resLvl.next()) {
                Level level = new Level();
                level.setLvl(resLvl.getString("nivOm"));
                Session.listLevels.add(level);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPrograms() {
        DbConnector conn = new DbConnector();
        String sqlProg = "SELECT progNm FROM programmas";
        ResultSet resProg = conn.getData(sqlProg);
        try {
            while (resProg.next()) {
                Program program = new Program();
                program.setProgNm(resProg.getString("progNm"));
                Session.listPrograms.add(program);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setDevices() {
        DbConnector conn = new DbConnector();
        String sqlDev = "SELECT tstl FROM toestellen";
        ResultSet resDev = conn.getData(sqlDev);
        try {
            while (resDev.next()) {
                Device device = new Device();
                device.setTstl(resDev.getString("tstl"));
                Session.listDevices.add(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setHasDevices() {
        DbConnector conn = new DbConnector();
        String sqlDev = "SELECT toestel_tstl FROM heeftToestellen WHERE Persoon_user = '"+Session.currentUser.getUserNm()+"'";
        ResultSet resDev = conn.getData(sqlDev);
        try {
            while (resDev.next()) {
                Device device = new Device();
                device.setTstl(resDev.getString("toestel_tstl"));
                Session.hasDevices.add(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
