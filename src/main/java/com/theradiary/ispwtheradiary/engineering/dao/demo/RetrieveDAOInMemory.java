package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.model.*;

import java.time.LocalDate;
import java.util.*;

public class RetrieveDAOInMemory implements RetrieveDAO {
    private final Map<String, Psychologist> psychologists = new HashMap<>();
    private final Map<String, Patient> patients = new HashMap<>();
    private final Map<String, List<Appointment>> appointments = new HashMap<>();

    private static final String ERROR_NO_RESULT = "Nessun risultato trovato";
    private static final String ERROR_INVALID_CATEGORY = "Categoria non valida: ";
    private static final String ERROR_INVALID_MAJOR = "Specializzazione non valida: ";

    @Override
    public void searchPsychologists(List<Psychologist> resultList, String name, String surname, String city, boolean inPerson, boolean online, boolean pag) throws NoResultException {
        resultList.clear();  // Pulizia della lista dei risultati

        for (Psychologist psychologist : psychologists.values()) {
            if (matchesSearchCriteria(psychologist, name, surname, city, inPerson, online, pag)) {
                resultList.add(psychologist);
            }
        }

        if (resultList.isEmpty()) {
            throw new NoResultException(ERROR_NO_RESULT);
        }
    }

    private boolean matchesSearchCriteria(Psychologist psychologist, String name, String surname, String city, boolean inPerson, boolean online, boolean pag) {
        return psychologist.getName().equals(name)
                && psychologist.getSurname().equals(surname)
                && psychologist.getCity().equals(city)
                && psychologist.isInPerson() == inPerson
                && psychologist.isOnline() == online
                && psychologist.isPag() == pag;
    }

    @Override
    public boolean retrieveMedicalOffice(MedicalOffice medicalOffice) {
        Psychologist psychologist = psychologists.get(medicalOffice.getPsychologist());
        if (psychologist == null) {
            return false;
        } else {
            medicalOffice.setPsychologist(psychologist.getName());
            return true;
        }
    }

    @Override
    public boolean retrieveCategories(Patient patient) {
        if (patient == null || !patients.containsKey(patient.getCredentials().getMail())) {
            return false;
        }

        List<Category> categories = new ArrayList<>();
        patient.setCategories(categories);
        return true;
    }

    @Override
    public void addCategory(ArrayList<Category> categories, String categoryName) {
        try {
            Category category = Category.valueOf(categoryName);
            categories.add(category);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ERROR_INVALID_CATEGORY + categoryName);
        }
    }

    @Override
    public boolean retrieveMajors(Psychologist psychologist) {
        if (psychologist == null || !psychologists.containsKey(psychologist.getCredentials().getMail())) {
            return false;
        }

        List<Major> majors = new ArrayList<>();
        psychologist.setMajors(majors);
        return true;
    }

    @Override
    public void addMajor(ArrayList<Major> majors, String majorName) {
        try {
            Major major = Major.valueOf(majorName);
            majors.add(major);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ERROR_INVALID_MAJOR + majorName);
        }
    }

    @Override
    public List<Patient> retrievePatientList(Psychologist psychologist) {
        List<Patient> list = new ArrayList<>();
        for (Patient patient : patients.values()) {
            if (patient.getPsychologist() != null &&
                    patient.getPsychologist().getCredentials() != null &&
                    patient.getPsychologist().getCredentials().getMail().equals(psychologist.getCredentials().getMail())) {
                list.add(patient);
            }
        }
        return list;
    }

    @Override
    public void checkPag(LoggedUser loggedUser) {
        String mail = loggedUser.getCredentials().getMail();
        loggedUser.setPag(psychologists.containsKey(mail) || patients.containsKey(mail));
    }

    @Override
    public void retrievePatientsRequest(Psychologist psychologist, List<Request> requests) {
        patients.values().stream()
                .filter(patient -> patient.getPsychologist().getCredentials().getMail().equals(psychologist.getCredentials().getMail()))
                .forEach(patient -> {
                    Request request = new Request(patient, psychologist, LocalDate.now());
                    requests.add(request);
                });
    }

    @Override
    public Psychologist yourPsychologist(Patient patient) {
        if (patient != null && patient.getPsychologist() != null) {
            return psychologists.get(patient.getPsychologist().getCredentials().getMail());
        }
        return null;
    }

    @Override
    public void retrieveAllAppointments(Psychologist psychologist, List<Appointment> appointmentsList) {
        if (psychologist != null) {
            appointmentsList.addAll(appointments.getOrDefault(psychologist.getCredentials().getMail(), new ArrayList<>()));
        }
    }

    @Override
    public Appointment retrievePatientAppointment(Patient patient, Psychologist psychologist) {
        if (psychologist == null || patient == null) {
            return null;
        }

        return appointments.getOrDefault(psychologist.getCredentials().getMail(), new ArrayList<>())
                .stream()
                .filter(appointment -> appointment.getPatient() != null &&
                        appointment.getPatient().getCredentials().getMail().equals(patient.getCredentials().getMail()))
                .findFirst()
                .orElse(null);
    }
}
