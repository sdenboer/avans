package techpal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import techpal.Models.*;

import java.sql.ResultSet;
import java.util.Arrays;

import static java.lang.Math.abs;

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
        String sqlDev = "SELECT toestel_tstl FROM heeftToestellen WHERE Persoon_user = UPPER('"+Session.currentUser.getUserNm()+"')";
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

    public static void setAvailableLessons() {
        DbConnector conn = new DbConnector();
        String sqlAv = "SELECT p.nm, p.pc, p.hnr, p.niveau_nivom, l.stu, l.dtm, l.periode_per, l.programma_prognm, " +
                "l.isFin, l.tstl " +
                "FROM lessen l " +
                "JOIN personen p ON(p.userNm = l.stu) " +
                "JOIN " +
                "WHERE ttr IS NULL AND l.isFin = 0";
        ResultSet resAv = conn.getData(sqlAv);
        try {
            while (resAv.next()) {
                Lesson lesson = new Lesson();
                lesson.setStu(resAv.getString("stu"));
                lesson.setStuNm(resAv.getString("nm"));
                lesson.setStuPc(resAv.getString("pc"));;
                lesson.setStuHnr(resAv.getString("hnr"));
                lesson.setStuNiv(resAv.getString("niveau_nivom"));
                lesson.setDtm(resAv.getDate("dtm").toLocalDate());
                lesson.setPer(resAv.getString("periode_per"));
                lesson.setProg(resAv.getString("programma_progNm"));
                lesson.setIsFin(resAv.getInt("isFin"));
                lesson.setTstl(resAv.getString("tstl"));
                Session.oblAvailableLessons.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //I'm extremely proud of this code! Reordering the zip codes from nearest to furthest from the home address of the currentUser.
        int zipCode = Integer.parseInt(Session.currentUser.getPc().replaceAll("[\\D]", ""));
        for (int i = 0; i<Session.oblAvailableLessons.size(); i++) {
            for (int j = 1; j<Session.oblAvailableLessons.size()-i; j++) {
                int intFirst = abs(Integer.parseInt(Session.oblAvailableLessons.get(j-1).getStuPc().replaceAll("[\\D]", ""))-zipCode);
                int intSecond = abs(Integer.parseInt(Session.oblAvailableLessons.get(j).getStuPc().replaceAll("[\\D]", ""))-zipCode);
                if (intFirst > intSecond) {
                    ObservableList<Lesson>temp = FXCollections.observableArrayList();
                    temp.add(0,  Session.oblAvailableLessons.get(j-1) );
                    Session.oblAvailableLessons.set(j-1, Session.oblAvailableLessons.get(j));
                    Session.oblAvailableLessons.set(j, temp.get(0));
                }
            }
        }
//        for (int i = 0; i < Session.oblAvailableLessons.size(); i++) {
//            System.out.println(Session.oblAvailableLessons.get(i).getStuPc());
//        }

        //The code below was my test code. It's basically explaining what I'm doing above. I'm using a bubble sort algorithm to sort the array from nearest to
        //farthest

//        int nr = 3471;
//        int[] arr = new int[] {1111, 2222, 3333, 4444, 5555, 6666, 7777, 8888};
//        for (int i = 0; i<arr.length; i++) {
//            for (int j = 1; j<arr.length -i; j++) {
//                int firstNumber = abs(arr[j-1] - nr);
//                int secondNumber = abs(arr[j] - nr);
//                if (firstNumber > secondNumber ) {
//                    int temp = arr[j-1];
//                    arr[j-1] = arr[j];
//                    arr[j] = temp;
//                }
//            }
//        }
//        System.out.println(arr.length);
//        System.out.println(Arrays.toString(arr));
//        System.out.println(Session.oblAvailableLessons.size());
    }
}
