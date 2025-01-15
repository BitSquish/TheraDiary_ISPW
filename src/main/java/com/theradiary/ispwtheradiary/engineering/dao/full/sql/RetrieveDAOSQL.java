package com.theradiary.ispwtheradiary.engineering.dao.full.sql;


import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.enums.*;
import com.theradiary.ispwtheradiary.engineering.exceptions.PersistenceOperationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.RetrieveQuery;
import com.theradiary.ispwtheradiary.model.*;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrieveDAOSQL implements RetrieveDAO {
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String CITY = "city";
    private static final String DESCRIPTION = "description";
    private static final String IN_PERSON = "inPerson";
    private static final String ONLINE = "online";
    private static final String PAG = "pag";
    private static final String MAIL = "mail";
    private static final String POSTCODE = "postCode";
    private static final String ADDRESS = "address";
    private static final String OTHERINFO = "otherInfo";
    private static final String PATIENT = "patient";
    private static final String DAY = "day";
    private static final String TIMESLOT = "timeSlot";
    private static final String AVAILABLE = "available";
    private static final String APPOINTMENT_ERROR = "Errore nel recupero degli appuntamenti";

    @Override
    public void searchPsychologists(List<Psychologist> psychologists, String name, String surname, String city, boolean inPerson, boolean online, boolean pag) {
        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = RetrieveQuery.searchPsychologist(conn, name, surname, city, inPerson, online, pag)) {
            if (!rs.next())
                throw new NoResultException("La ricerca non ha prodotto risultati");
            do {
                //Passare la password come null o creare nuovo costruttore solo con la mail?
                Credentials credentials = new Credentials(rs.getString(MAIL), Role.PSYCHOLOGIST);
                Psychologist psychologist = new Psychologist(
                        credentials,
                        rs.getString(NAME),
                        rs.getString(SURNAME),
                        rs.getString(CITY),
                        rs.getString(DESCRIPTION),
                        rs.getBoolean(IN_PERSON),
                        rs.getBoolean(ONLINE)
                );
                psychologist.setPag(rs.getBoolean(PAG));
                psychologists.add(psychologist);
            } while (rs.next());
        } catch (SQLException | NoResultException e) {
            throw new PersistenceOperationException("Errore nella ricerca", e);
        }
    }
    @Override
    public boolean retrieveMedicalOffice(MedicalOffice medicalOffice) {
        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = RetrieveQuery.retrieveMedicalOffice(conn, medicalOffice.getPsychologist())) {
            if (rs.next()) {
                medicalOffice.setCity(rs.getString(CITY));
                medicalOffice.setPostCode(rs.getString(POSTCODE));
                medicalOffice.setAddress(rs.getString(ADDRESS));
                medicalOffice.setOtherInfo(rs.getString(OTHERINFO));
                return true;
            } else
                return false;
        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore nel recupero dell'ufficio medico", e);
        }

    }

    @Override
    public boolean retrieveCategories(Patient patient) {
        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = RetrieveQuery.retrieveCategories(conn, patient.getCredentials().getMail())) {

            ArrayList<Category> categories = extractCategories(rs);
            patient.setCategories(new ArrayList<>(categories));
            return !categories.isEmpty();

        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore nel recupero delle categorie", e);
        }
    }

    private ArrayList<Category> extractCategories(ResultSet rs) {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            while (rs.next()) {
                String categoryName = rs.getString("category");
                addCategory(categories, categoryName);
            }
        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore durante l'elaborazione del ResultSet", e);
        }
        return categories;
    }
    public void addCategory(List<Category> categories, String categoryName) {
        try {
            Category category = Category.valueOf(categoryName);
            categories.add(category);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoria non valida: " + categoryName);
        }
    }
    @Override
    public boolean retrieveMajors(Psychologist psychologist) {
        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = RetrieveQuery.retrieveMajors(conn, psychologist.getCredentials().getMail())) {

            ArrayList<Major> majors = extractMajors(rs);
            psychologist.setMajors(new ArrayList<>(majors));
            return !majors.isEmpty();

        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore nel recupero delle specializzazioni", e);
        }
    }

    private ArrayList<Major> extractMajors(ResultSet rs) {
        ArrayList<Major> majors = new ArrayList<>();
        try {
            while (rs.next()) {
                String majorName = rs.getString("major");
                addMajor(majors, majorName);
            }
        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore durante l'elaborazione del ResultSet", e);
        }
        return majors;
    }

    public void addMajor(List<Major> majors, String majorName) {
        try {
            Major major = Major.valueOf(majorName);
            majors.add(major);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Specializzazione non valida: " + majorName);
        }
    }
    @Override
    public List<Patient> retrievePatientList(Psychologist psychologist) {
        List<Patient> patients = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = RetrieveQuery.retrievePatientList(conn, psychologist.getCredentials().getMail())) {
            // Itera sui risultati della query
            while (rs.next()) {
                Patient patient = new Patient(
                        new Credentials(rs.getString(MAIL), Role.PATIENT),
                        rs.getString(NAME),
                        rs.getString(SURNAME),
                        rs.getString(CITY),
                        rs.getString(DESCRIPTION),
                        rs.getBoolean(IN_PERSON),
                        rs.getBoolean(ONLINE)

                );
                patient.setPag(rs.getBoolean(PAG));
                patients.add(patient);
                // Aggiungi il paziente alla lista
            }

        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore nel recupero dei pazienti", e);
        }
        return patients;
    }
    @Override
    public void checkPag(LoggedUser loggedUser) {
        try (Connection conn = ConnectionFactory.getConnection()){
            ResultSet rs;
             if(loggedUser.getCredentials().getRole().equals(Role.PATIENT))
                rs = RetrieveQuery.checkPatientPag(conn, loggedUser.getCredentials().getMail());
            else
                rs = RetrieveQuery.checkPsychologistPag(conn, loggedUser.getCredentials().getMail());
            if (rs.next()) {
                loggedUser.setPag(rs.getBoolean(PAG));
            }
        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore nel recupero del pag", e);
        }
    }

    @Override
    public void retrievePatientsRequest(Psychologist psychologist, List<Request> requests) {
        try(Connection conn = ConnectionFactory.getConnection()){
            ResultSet rs = RetrieveQuery.retrieveRequests(conn, psychologist.getCredentials().getMail());
            while(rs.next()){
                Patient patient = new Patient(
                        new Credentials(rs.getString(MAIL), Role.PATIENT),
                        rs.getString(NAME),
                        rs.getString(SURNAME),
                        rs.getString(CITY),
                        rs.getString(DESCRIPTION),
                        rs.getBoolean(IN_PERSON),
                        rs.getBoolean(ONLINE)
                );
                patient.setPag(rs.getBoolean(PAG));
                Request request = new Request(patient, psychologist, rs.getDate("date").toLocalDate());
                requests.add(request);
            }

        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nel recupero delle richieste", e);
        }
    }

    public List<Appointment> retrieveSlotTime(Psychologist psychologist, DayOfTheWeek dayOfTheWeek) {
        List<Appointment> appointments = new ArrayList<>();
        try(Connection conn = ConnectionFactory.getConnection()){
            ResultSet rs = RetrieveQuery.retrieveSlotTime(conn, psychologist.getCredentials().getMail(), dayOfTheWeek.toString());
            while(rs.next()){
                Appointment appointment = new Appointment(
                        psychologist,
                        dayOfTheWeek,
                        TimeSlot.valueOf(rs.getString(TIMESLOT)),
                        rs.getBoolean(IN_PERSON),
                        rs.getBoolean(ONLINE)
                );
                appointment.setPatient(new Patient(new Credentials(rs.getString(PATIENT), Role.PATIENT)));
                appointments.add(appointment);
            }
            return appointments;
        }catch (SQLException e){
            throw new PersistenceOperationException(APPOINTMENT_ERROR, e);
        }
    }
    @Override
    public Psychologist yourPsychologist(Patient patient) {
        try(Connection conn = ConnectionFactory.getConnection();
            ResultSet rs = RetrieveQuery.yourPsychologist(conn, patient.getCredentials().getMail())){
            if(rs.next()){
                Psychologist psychologist = new Psychologist(
                        new Credentials(rs.getString(MAIL), Role.PSYCHOLOGIST),
                        rs.getString(NAME),
                        rs.getString(SURNAME),
                        rs.getString(CITY),
                        rs.getString(DESCRIPTION),
                        rs.getBoolean(IN_PERSON),
                        rs.getBoolean(ONLINE)
                );
                psychologist.setPag(rs.getBoolean(PAG));
                return psychologist;
            }else
                return null;
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nel recupero dello psicologo", e);
        }
    }
    @Override
    public void retrieveAllAppointments(Psychologist psychologist, List<Appointment> appointments) {
        try(Connection conn = ConnectionFactory.getConnection();
            ResultSet rs = RetrieveQuery.retrieveAllAppointments(conn, psychologist.getCredentials().getMail())){
            while(rs.next()) {
                DayOfTheWeek day = DayOfTheWeek.fromStringToDay(rs.getString(DAY));
                Appointment appointment = new Appointment(
                        psychologist,
                        day,
                        TimeSlot.valueOf(rs.getString(TIMESLOT)),
                        rs.getBoolean(IN_PERSON),
                        rs.getBoolean(ONLINE)
                );
                appointment.setPatient(new Patient(new Credentials(rs.getString(PATIENT), Role.PATIENT)));
                appointment.setAvailable(rs.getBoolean(AVAILABLE));
                appointments.add(appointment);
            }
        }catch (SQLException e){
            throw new PersistenceOperationException(APPOINTMENT_ERROR, e);
        }
    }

    @Override
    public Appointment retrievePatientAppointment(Patient patient, Psychologist psychologist) {
        boolean availability = false;
        try(Connection conn = ConnectionFactory.getConnection();
            ResultSet rs = RetrieveQuery.retrievePatientAppointment(conn, psychologist.getCredentials().getMail(), patient.getCredentials().getMail(), availability)){
            Appointment appointment = new Appointment(psychologist, patient);
            while(rs.next()){
                appointment = new Appointment(
                        psychologist,
                        DayOfTheWeek.valueOf(rs.getString(DAY)),
                        TimeSlot.valueOf(rs.getString(TIMESLOT)),
                        rs.getBoolean(IN_PERSON),
                        rs.getBoolean(ONLINE)
                );
                appointment.setPatient(patient);
                appointment.setAvailable(availability);
            }
            return appointment;
        }catch (SQLException e){
            throw new PersistenceOperationException(APPOINTMENT_ERROR, e);
        }
    }
}

