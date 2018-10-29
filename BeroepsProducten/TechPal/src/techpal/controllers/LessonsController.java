package techpal.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import techpal.models.*;
import techpal.views.TabView;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.StringJoiner;
import static java.lang.Math.abs;

public class LessonsController {
    private static DbConnector conn;

    public LessonsController() {
    }

    public static ObservableList<Lesson> olLessons = FXCollections.observableArrayList();
    public static ObservableList<Lesson> olAvailableLessons = FXCollections.observableArrayList();
    public static ObservableList<Lesson> olPrevLessons = FXCollections.observableArrayList();

    public static void setLessons(Person user) {
        //This method sets the user specific lessons in the Lesson and Controller classes.
        // olLessons is an arrayList of Lesson instances.
        conn = new DbConnector();
        String role = user instanceof Student ? "stu" : "ttr";
        //checks whether the user is a student or a tutor
        //The following sql query joins the person table twice,
        // as I want to show the name of the tutor in the lesson tab view,
        // and not just the tutor's username. PS stands for PersoonStudent and PT means PersoonTutor
        String sql = "SELECT ps.usernm, ps.nm AS stuNm, ps.pc, ps.hnr, ps.niveau_nivom, l.stu, l.dtm, l.periode_per, " +
                "l.programma_prognm, l.isFin, l.tstl, l.ttr, pt.nm AS ttrNm " +
                "FROM personen ps " +
                "INNER JOIN lessen l ON(l.stu = ps.userNm) " +
                "LEFT OUTER JOIN personen pt ON(l.ttr = pt.usernm) " +
                "WHERE "+role+" = UPPER('"+user.getUserNm()+"')";
        ResultSet res = conn.getData(sql);
        try {
            while (res.next()) {
                Lesson lesson = new Lesson();
                lesson.setStu(res.getString("stu"));
                lesson.setStuNm(res.getString("stuNm"));
                lesson.setDtm(res.getDate("dtm").toLocalDate());
                lesson.setPer(res.getString("periode_per"));
                lesson.setProg(res.getString("programma_progNm"));
                lesson.setIsFin(res.getInt("isFin"));
                lesson.setTstl(res.getString("tstl"));
                lesson.setTtr(res.getString("ttr"));
                lesson.setStuNiv(res.getString("niveau_nivOm"));
                lesson.setTtrNm(res.getString("ttrNm"));
                lesson.setStuPc((res.getString("pc")));
                lesson.setStuHnr(res.getString("hnr"));
                if (lesson.getIsFin() == 0) {
                    LessonsController.olLessons.add(lesson);
                } else {
                    LessonsController.olPrevLessons.add(lesson);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNewLesson(LocalDate date, String period, String program, String dev, TabView tabPane) {
        conn = new DbConnector();
        Lesson lesson = new Lesson();
        lesson.setStu(StudentsController.currentStudent.getUserNm());
        lesson.setDtm(date);
        lesson.setPer(period);
        lesson.setProg(program);
        lesson.setIsFin(0);
        lesson.setTstl(dev);
        String sql = "INSERT INTO lessen (stu, dtm, periode_per, programma_prognm, isFin, tstl) " +
                "VALUES (UPPER('"+lesson.getStu()+"'), to_date('"+lesson.getDtm()+"', 'yyyy/mm/dd')" +
                ", '"+lesson.getPer()+"', '"+lesson.getProg()+"', "+lesson.getIsFin()+", '"+lesson.getTstl()+"')";
        LessonsController.olLessons.add(lesson);
        int result = conn.executeDML(sql);
        if (result == 0) {
            BaseController.alert("Oeps!", "Er is iets fout gegaan met de invoer", Alert.AlertType.INFORMATION);
            LessonsController.olLessons.remove(lesson); //removes the lesson if it fails to insert into the db
        } else {
            tabPane.getSelectionModel().select(0); //Return to first tab
        }
    }

    public static void deleteLesson(LocalDate lcdDtm, String strPer, int index) {
        conn = new DbConnector();
        LessonsController.olLessons.remove(index); //deletes the lesson from the observable list
        String sql = "DELETE FROM lessen " +
                "WHERE dtm = to_date('" + lcdDtm + "', 'yyyy/mm/dd') " +
                "AND stu = UPPER('" + StudentsController.currentStudent.getUserNm() + "') " +
                "AND periode_per = '" + strPer + "' ";
        int result = conn.executeDML(sql);
    }

    public static void addTutorLesson(String stuUserName,
                                      LocalDate dtm,
                                      String per,
                                      String prog,
                                      String dev,
                                      String nr,
                                      String stuNm,
                                      String stuNiv,
                                      String stuPc,
                                      int index) {
        conn = new DbConnector();
        Lesson lesson = new Lesson();
        lesson.setStu(stuUserName);
        lesson.setDtm(dtm);
        lesson.setPer(per);
        lesson.setProg(prog);
        lesson.setIsFin(0);
        lesson.setTstl(dev);
        lesson.setTtr(TutorsController.currentTutor.getUserNm());
        lesson.setStuHnr(nr);
        lesson.setStuNm(stuNm);
        lesson.setStuNiv(stuNiv);
        lesson.setStuPc(stuPc);
        LessonsController.olAvailableLessons.remove(index);
        LessonsController.olLessons.add(lesson);
        String sql = "UPDATE lessen " +
                "SET ttr = UPPER('" + lesson.getTtr() + "') " +
                "WHERE dtm = to_date('" + lesson.getDtm() + "', 'yyyy/mm/dd') " +
                "AND stu = '" + lesson.getStu() + "' " +
                "AND periode_per = '" + lesson.getPer() + "' ";
        int result = conn.executeDML(sql);
    }

    public static void cancelLesson(LocalDate dtm, String stu, String per, Lesson lesson, int index) {
        conn = new DbConnector();
        LessonsController.olAvailableLessons.add(lesson);
        String sql = "UPDATE lessen " +
                "SET ttr = NULL " +
                "WHERE dtm = to_date('" + dtm + "', 'yyyy/mm/dd') " +
                "AND stu = '" + stu + "' " +
                "AND ttr = UPPER('" + TutorsController.currentTutor.getUserNm() + "') " +
                "AND periode_per = '" + per + "' ";
        System.out.println(sql);
        LessonsController.olLessons.remove(index);
        int result = conn.executeDML(sql);
    }

    public static void finishLesson(LocalDate dtm, String stu, String per, Lesson lesson, int index) {
        conn = new DbConnector();
        LessonsController.olPrevLessons.add(lesson);
        String sql = "UPDATE lessen " +
                "SET isFin = 1 " +
                "WHERE dtm = to_date('"+dtm+"', 'yyyy/mm/dd') " +
                "AND stu = '"+stu+"' "+
                "AND ttr = UPPER('" +TutorsController.currentTutor.getUserNm()+"') " +
                "AND periode_per = '" +per+ "' ";
        System.out.println(sql);
        LessonsController.olLessons.remove(index);
        int result = conn.executeDML(sql);
    }

    public static void setAvailableLessons() {
        LessonsController.olAvailableLessons.clear(); //clearing the old available lesson array
        conn = new DbConnector();
        StringJoiner strjDev = new StringJoiner("', '", "'", "'");
        //prints 'string', 'string'. Needed for entering data in the sql statement below.
        DevicesController.olHasDevices.forEach(device -> strjDev.add(device.getTstl()));
        //strjDev is now 'device1', 'device2', 'device3'
        String sql = "SELECT p.nm, p.pc, p.hnr, p.niveau_nivom, l.stu, l.dtm, l.periode_per, l.programma_prognm, " +
                "l.isFin, l.tstl " +
                "FROM lessen l " +
                "JOIN personen p ON(p.userNm = l.stu) " +
                "WHERE tstl IN ("+strjDev+")" +
                "AND ttr IS NULL " +
                "AND l.isFin = 0";
        ResultSet res = conn.getData(sql);
        try {
            while (res.next()) {
                Lesson lesson = new Lesson();
                lesson.setStu(res.getString("stu"));
                lesson.setStuNm(res.getString("nm"));
                lesson.setStuPc(res.getString("pc"));
                lesson.setStuHnr(res.getString("hnr"));
                lesson.setStuNiv(res.getString("niveau_nivom"));
                lesson.setDtm(res.getDate("dtm").toLocalDate());
                lesson.setPer(res.getString("periode_per"));
                lesson.setProg(res.getString("programma_progNm"));
                lesson.setIsFin(res.getInt("isFin"));
                lesson.setTstl(res.getString("tstl"));
                LessonsController.olAvailableLessons.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Reordering the zip codes from nearest to furthest from the home address of the currentUser.
        int zipCode = Integer.parseInt(TutorsController.currentTutor.getPc().replaceAll("[\\D]", ""));
        for (int i = 0; i<LessonsController.olAvailableLessons.size(); i++) {
            for (int j = 1; j <LessonsController.olAvailableLessons.size()-i; j++) {
                int intFirst = abs(Integer.parseInt(LessonsController.olAvailableLessons.get(j-1).getStuPc().replaceAll("[\\D]", ""))-zipCode);
                int intSecond = abs(Integer.parseInt(LessonsController.olAvailableLessons.get(j).getStuPc().replaceAll("[\\D]", ""))-zipCode);
                if (intFirst > intSecond) {
                    Lesson temp = LessonsController.olAvailableLessons.get(j-1);
                    LessonsController.olAvailableLessons.set(j-1, LessonsController.olAvailableLessons.get(j));
                    LessonsController.olAvailableLessons.set(j, temp);
                }
            }
        }

        //The code below was my test code. It's basically explaining what I'm doing above. I'm using a bubble sort algorithm to sort the array from nearest to
        //farthest
//
//        int nr = 3471;
//        int[] arr = new int[] {1111, 2222, 2222, 2222, 2222, 3333, 4444, 7771, 7771, 7771, 7771, 7812, 7712, 6666, 7777, 8888};
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
//        System.out.println(LessonsController.oblAvailableLessons.size());
    }
}
