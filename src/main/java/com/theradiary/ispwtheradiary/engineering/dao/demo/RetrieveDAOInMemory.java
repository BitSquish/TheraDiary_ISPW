package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.demo.shared.SharedResources;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.model.*;

import java.time.LocalDate;
import java.util.*;

public class RetrieveDAOInMemory implements RetrieveDAO {


    private static final String ERROR_INVALID_CATEGORY = "Categoria non valida: ";
    private static final String ERROR_INVALID_MAJOR = "Specializzazione non valida: ";
    @Override
    public void searchPsychologists(List<Psychologist> psychologists, String name, String surname, String city, boolean inPerson, boolean online, boolean pag) throws NoResultException {
        List<Psychologist> results = new ArrayList<>();
        for (Psychologist psychologist : SharedResources.getInstance().getPsychologists().values()) {
            if (matchesCriteria(psychologist, name, surname, city, inPerson, online, pag)) {
                results.add(psychologist);
            }
        }

        if (results.isEmpty()) {
            throw new NoResultException("La ricerca non ha prodotto risultati");
        }
        psychologists.addAll(results);
    }

    private boolean matchesCriteria(Psychologist psychologist, String name, String surname, String city, boolean inPerson, boolean online, boolean pag) {
        return matchesName(psychologist, name) &&
                matchesSurname(psychologist, surname) &&
                matchesCity(psychologist, city) &&
                matchesInPerson(psychologist, inPerson) &&
                matchesOnline(psychologist, online) &&
                matchesPag(psychologist, pag);
    }

    private boolean matchesName(Psychologist psychologist, String name) {
        return name == null || psychologist.getCredentials().getMail().contains(name);
    }

    private boolean matchesSurname(Psychologist psychologist, String surname) {
        return surname == null || psychologist.getSurname().contains(surname);
    }

    private boolean matchesCity(Psychologist psychologist, String city) {
        return city == null || psychologist.getCity().contains(city);
    }

    private boolean matchesInPerson(Psychologist psychologist, boolean inPerson) {
        return !inPerson || psychologist.isInPerson();
    }

    private boolean matchesOnline(Psychologist psychologist, boolean online) {
        return !online || psychologist.isOnline();
    }

    private boolean matchesPag(Psychologist psychologist, boolean pag) {
        return !pag || psychologist.isPag();
    }


    @Override
    public boolean retrieveMedicalOffice(MedicalOffice medicalOffice) {
        MedicalOffice retrieved = SharedResources.getInstance().getMedicalOffices().get(medicalOffice.getPsychologist());
        if(retrieved == null) {
            return false;
        } else {
            medicalOffice.setCity(retrieved.getCity());
            medicalOffice.setAddress(retrieved.getAddress());
            medicalOffice.setOtherInfo(retrieved.getOtherInfo());
            medicalOffice.setPostCode(retrieved.getPostCode());
            return true;
        }
    }

    @Override
    public boolean retrieveCategories(Patient patient) {
        if (patient == null || !SharedResources.getInstance().getPatientCategories().containsKey(patient.getCredentials().getMail())) {
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
        if (psychologist == null || !SharedResources.getInstance().getPsychologistMajors().containsKey(psychologist.getCredentials().getMail())) {
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
        for (Patient patient : SharedResources.getInstance().getPatients().values()) {
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
        loggedUser.setPag(SharedResources.getInstance().getPsychologists().containsKey(mail) || SharedResources.getInstance().getPatients().containsKey(mail));
    }

    @Override
    public void retrievePatientsRequest(Psychologist psychologist, List<Request> requests) {
        SharedResources.getInstance().getPatients().values().stream()
                .filter(patient -> patient.getPsychologist().getCredentials().getMail().equals(psychologist.getCredentials().getMail()))
                .forEach(patient -> {
                    Request request = new Request(patient, psychologist, LocalDate.now());
                    requests.add(request);
                });
    }

    @Override
    public Psychologist yourPsychologist(Patient patient) {
        if (patient != null && patient.getPsychologist() != null) {
            return SharedResources.getInstance().getPsychologists().get(patient.getPsychologist().getCredentials().getMail());
        }
        return null;
    }

    @Override
    public void retrieveAllAppointments(Psychologist psychologist, List<Appointment> appointmentsList) {
        if (psychologist != null) {
            List<Appointment> appointments = new ArrayList<>(SharedResources.getInstance().getAppointments().values());
            appointmentsList.addAll(appointments);
        }
    }

    @Override
    public Appointment retrievePatientAppointment(Patient patient, Psychologist psychologist) {
        if (psychologist == null || patient == null) {
            return null;
        }

        return SharedResources.getInstance().getAppointments().get(patient.getCredentials().getMail() + "_" + psychologist.getCredentials().getMail());
    }


}
