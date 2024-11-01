package com.theradiary.ispwtheradiary.engineering.dao;



/*public class RetrieveDAO {
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
}*/
