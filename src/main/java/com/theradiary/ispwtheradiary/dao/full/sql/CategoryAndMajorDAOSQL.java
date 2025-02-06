package com.theradiary.ispwtheradiary.dao.full.sql;


import com.theradiary.ispwtheradiary.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.patterns.factory.ConnectionFactory;
import com.theradiary.ispwtheradiary.query.AccountQuery;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.Connection;

import java.sql.SQLException;


public class CategoryAndMajorDAOSQL implements CategoryAndMajorDAO {
   @Override
    public void addCategory(Patient patient, Category category) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            AccountQuery.addCategory(conn, category.toString(), patient.getCredentials().getMail());
        }catch (SQLException | DatabaseOperationException e){
            handleException(e);
        }
   }
    @Override
    public void addMajor(Psychologist psychologist, Major major) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            boolean success = AccountQuery.addMajor(conn,major.toString(), psychologist.getCredentials().getMail());
            if(success)
                psychologist.getMajors().add(major);
        }catch (SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }

    @Override
    public void removeCategory(Patient patient, Category category) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            boolean success = AccountQuery.removeCategory(conn,patient.getCredentials().getMail(), category.toString());
            if(success)
                patient.getCategories().remove(category);
        }catch (SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }
    @Override
    public void removeMajor(Psychologist psychologist, Major major) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            boolean success = AccountQuery.removeMajor(conn,psychologist.getCredentials().getMail(),major.toString());
            if(success)
                psychologist.getMajors().remove(major);
        }catch (SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }
    private void handleException(Exception e) {
        Printer.errorPrint(String.format("%s", e.getMessage()));
    }
}
