package techpal.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import techpal.models.*;
import java.util.ArrayList;

public class Session {

    public Session() {
    }

    public static ObservableList<Lesson> oblLessons = FXCollections.observableArrayList();
    public static Person currentUser = new Person();
    public static Student currentStudent = new Student();
    public static Tutor currentTutor = new Tutor();
    public static ArrayList<Program>listPrograms = new ArrayList<>();
    public static ArrayList<Device>listDevices = new ArrayList<>();
    public static ObservableList<Device>hasDevices = FXCollections.observableArrayList();
    public static ArrayList<Period>listPeriods = new ArrayList<>();
    public static ArrayList<Level>listLevels = new ArrayList<>();
    public static ObservableList<Lesson> oblAvailableLessons = FXCollections.observableArrayList();
    public static ObservableList<Lesson> oblPrevLessons = FXCollections.observableArrayList();

}
