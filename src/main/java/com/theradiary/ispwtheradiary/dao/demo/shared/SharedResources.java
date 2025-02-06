package com.theradiary.ispwtheradiary.dao.demo.shared;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class SharedResources {

    // Mappe condivise per i vari entità
    private final Map<String, MedicalOffice> medicalOffices = new ConcurrentHashMap<>();
    private final Map<String, Psychologist> psychologists = new ConcurrentHashMap<>();
    private final Map<String, Patient> patients = new ConcurrentHashMap<>();
    private final Map<String, Appointment> appointments = new ConcurrentHashMap<>();
    private final Map<String, Set<Category>> patientCategories = new ConcurrentHashMap<>();
    private final Map<String, Set<Major>> psychologistMajors = new ConcurrentHashMap<>();
    private final Map<String, Credentials> userTable = new ConcurrentHashMap<>();
    private final Map<String, List<Request>> requestsSent = new HashMap<>();
    // Mappa per tenere traccia delle relazioni pazienti-psicologi
    private final  Map<String, Psychologist> patientsWithPsychologists = new HashMap<>();
    private final Map<String, Map<LocalDate,String>> diaryTable= new ConcurrentHashMap<>();//<mail,<data,contenuto>>
    private final Map<String, List<ToDoItem>> toDoTable= new ConcurrentHashMap<>();//<mail,listaToDo>
    private final Map<String, List<Task>> taskTable= new ConcurrentHashMap<>();//<mail,listaTask>

    // Singleton per garantire che ci sia una sola istanza della classe
    private static SharedResources instance=null;

    private SharedResources() {
        // Costruttore privato per evitare la creazione di istanze esterne
    }

    // Metodo per ottenere l'istanza Singleton
    public static synchronized SharedResources getInstance() {
        if (instance == null) {
            instance = new SharedResources();
        }
        return instance;
    }

    // Getter per le mappe condivise
    public Map<String, MedicalOffice> getMedicalOffices() {
        return medicalOffices;
    }

    public Map<String, Psychologist> getPsychologists() {
        return psychologists;
    }

    public Map<String, Patient> getPatients() {
        return patients;
    }

    public Map<String, Appointment> getAppointments() {
        return appointments;
    }
    public Map<String,Set<Category>> getPatientCategories(){
        return patientCategories;
    }
    public Map<String,Set<Major>> getPsychologistMajors(){
        return psychologistMajors;
    }
    public Map<String,Credentials> getUserTable(){
        return userTable;
    }
    public Map<String,Psychologist> getPatientsWithPsychologists(){
        return patientsWithPsychologists;
    }
    public Map<String,List<Request>> getRequestsSent(){
        return requestsSent;
    }
    public Map<String, Map<LocalDate,String>> getDiaryTable(){
        return diaryTable;
    }
    public Map<String, List<ToDoItem>> getToDoTable(){
        return toDoTable;
    }
    public Map<String, List<Task>> getTaskTable(){
        return taskTable;
    }


}

