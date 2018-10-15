package techpal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import techpal.Models.*;

import java.util.ArrayList;

public class Session {

    public Session() {
    }

    static ObservableList<Lesson> oblLessons = FXCollections.observableArrayList();
    static Person currentUser = new Person();
    static Student currentStudent = new Student();
    static ArrayList<Program>listPrograms = new ArrayList<>();
    static ArrayList<Device>listDevices = new ArrayList<>();
    static ObservableList<Device>hasDevices = FXCollections.observableArrayList();
    static ArrayList<Period>listPeriods = new ArrayList<>();
    static ArrayList<Level>listLevels = new ArrayList<>();
    static ObservableList<Lesson> oblAvailableLessons = FXCollections.observableArrayList();

}
