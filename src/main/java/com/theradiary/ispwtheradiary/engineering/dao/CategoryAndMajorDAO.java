package com.theradiary.ispwtheradiary.engineering.dao;


import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.AccountQuery;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.Connection;

import java.sql.SQLException;


public class CategoryAndMajorDAO {
    private CategoryAndMajorDAO(){}
    public static void addCategory(Patient patient, Category category) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            AccountQuery.addCategory(conn, category.toString(), patient.getCredentials().getMail());
        }catch (SQLException e){
            throw new DatabaseOperationException("Errore nell'aggiunta della categoria",e);
        }
    }
    public static void addMajor(Psychologist psychologist, Major major) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            boolean success = AccountQuery.addMajor(conn,major.toString(), psychologist.getCredentials().getMail());
            if(success)
                psychologist.getMajors().add(major);
        }catch (SQLException e){
            throw new DatabaseOperationException("Errore nell'aggiunta della specializzazione",e);
        }
    }


    public static void removeCategory(Patient patient, Category category) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            boolean success = AccountQuery.removeCategory(conn,patient.getCredentials().getMail(), category.toString());
            if(success)
                patient.getCategories().remove(category);
        }catch (SQLException e){
            throw new DatabaseOperationException("Errore nella rimozione della categoria",e);
        }
    }

    public static void removeMajor(Psychologist psychologist, Major major) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            boolean success = AccountQuery.removeMajor(conn,psychologist.getCredentials().getMail(),major.toString());
            if(success)
                psychologist.getMajors().remove(major);
        }catch (SQLException e){
            throw new DatabaseOperationException("Errore nella rimozione della specializzazione",e);
        }
    }
}
