package com.theradiary.ispwtheradiary.engineering.dao;


import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.RetrieveQuery;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrieveDAO {
    private RetrieveDAO(){}

    public static void searchPsychologists(List<Psychologist> psychologists, String name, String surname, String city, boolean inPerson, boolean online, boolean pag) throws NoResultException{

        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = RetrieveQuery.searchPsychologist(conn, name, surname, city, inPerson, online, pag)) {
            if(!rs.next())
                throw new NoResultException("La ricerca non ha prodotto risultati");
             do{
                //Passare la password come null o creare nuovo costruttore solo con la mail?
                Credentials credentials = new Credentials(rs.getString("mail"), null, Role.PSYCHOLOGIST);
                Psychologist psychologist = new Psychologist(
                        credentials,
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("city"),
                        rs.getString("description"),
                        rs.getBoolean("inPerson"),
                        rs.getBoolean("online"),
                        rs.getBoolean("pag"),
                        null,
                        null
                );
                psychologists.add(psychologist);
            }while(rs.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean retrieveMedicalOffice(MedicalOffice medicalOffice) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection();
            ResultSet rs = RetrieveQuery.retrieveMedicalOffice(conn, medicalOffice.getMail())){
            if(rs.next()){
                medicalOffice.setCity(rs.getString("city"));
                medicalOffice.setPostCode(rs.getString("postCode"));
                medicalOffice.setAddress(rs.getString("address"));
                medicalOffice.setOtherInfo(rs.getString("otherInfo"));
                return true;
            }
            else
                return false;
        }
        catch (SQLException e) {
            throw new RuntimeException(e); //DA VERIFICARE ECCEZIONE
        }

    }

    public static void retrievePatient(Patient patient) {
        try(Connection conn = ConnectionFactory.getConnection();
        ResultSet rs = RetrieveQuery.retrievePatient(conn, patient.getCredentials().getMail())){
            if(rs.next()){
                patient.setName(rs.getString("name"));
                patient.setSurname(rs.getString("surname"));
                patient.setCity(rs.getString("city"));
                patient.setDescription(rs.getString("description"));
                patient.setInPerson(rs.getBoolean("inPerson"));
                patient.setOnline(rs.getBoolean("online"));
                patient.setPag(rs.getBoolean("pag"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void retrievePsychologist(Psychologist psychologist) {
        try(Connection conn = ConnectionFactory.getConnection();
            ResultSet rs = RetrieveQuery.retrievePsychologist(conn, psychologist.getCredentials().getMail())){
            if(rs.next()){
                psychologist.setName(rs.getString("name"));
                psychologist.setSurname(rs.getString("surname"));
                psychologist.setCity(rs.getString("city"));
                psychologist.setDescription(rs.getString("description"));
                psychologist.setInPerson(rs.getBoolean("inPerson"));
                psychologist.setOnline(rs.getBoolean("online"));
                psychologist.setPag(rs.getBoolean("pag"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean retrieveCategories(Patient patient) {
        try(Connection conn = ConnectionFactory.getConnection();
            ResultSet rs = RetrieveQuery.retrieveCategories(conn, patient.getCredentials().getMail())){
            ArrayList<Category> categories=new ArrayList<>();
            while(rs.next()) {
                String categoryName=rs.getString("category");
                try{
                    Category category=Category.valueOf(categoryName);
                    categories.add(category);
                }catch(IllegalArgumentException e){
                    System.err.println("Categoria non valida:"+categoryName);
                    continue;
                }
            }
            patient.setCategories(new ArrayList<>(categories));
            return !categories.isEmpty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean retrieveMajors(Psychologist psychologist) {
        try(Connection conn = ConnectionFactory.getConnection();
            ResultSet rs = RetrieveQuery.retrieveMajors(conn, psychologist.getCredentials().getMail())){
            ArrayList<Major> majors=new ArrayList<>();
            while(rs.next()) {
                String majorName=rs.getString("major");
                try{
                    Major major=Major.valueOf(majorName);
                    majors.add(major);
                }catch(IllegalArgumentException e){
                    System.err.println("Specializzazione non valida:"+majorName);
                    continue;
                }
            }
            psychologist.setMajors(new ArrayList<>(majors));
            return !majors.isEmpty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
