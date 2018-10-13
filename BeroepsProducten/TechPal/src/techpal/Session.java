package techpal;

import techpal.Models.*;

import java.util.ArrayList;

public class Session {

    public Session() {
    }

    static ArrayList<Lesson>listLessons = new ArrayList<>();
    static Person currentUser = new Person();
    static Student currentStudent = new Student();
    static ArrayList<Program>listPrograms = new ArrayList<>();
    static ArrayList<Device>hasDevices = new ArrayList<>();
    static  ArrayList<Period>listPeriods = new ArrayList<>();

}
