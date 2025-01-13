
import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;



class TestPatient {

    private static final String TEST_MAIL = "test@gmail.com";
    private String password;

    @Test
    void testCredential() {
        int res ;
        password = generatePassword();
        try {
            // Prima registriamo il paziente
            registraTester();

            // Ora tentiamo di effettuare il login con le stesse credenziali
            LoginAndRegistrationDAO loginAndRegistrationDAO = FactoryDAO.getDAO();
            Credentials credenziali = new Credentials(TEST_MAIL, password, Role.PATIENT);

            // Proviamo a fare il login
            loginAndRegistrationDAO.login(credenziali);

            // Se arriviamo qui, il login è stato eseguito correttamente
            res = 1;

        } catch (Exception e) {
            // Se c'è stata un'eccezione, segnaliamo che il test è fallito
            res = 0;
        }

        // Verifica che res sia uguale a 1 (test passato)
        Assertions.assertEquals(1, res);
    }

    // Metodo per generare una password unica
    private String generatePassword() {
        return "test" + System.currentTimeMillis();
    }

    // Metodo che si occupa di registrare il paziente
    public void registraTester() throws LoginAndRegistrationException{
        LoginAndRegistrationDAO loginAndRegistrationDAO = FactoryDAO.getDAO();
        try {
            // Tentiamo il login con le credenziali fornite
            Credentials credenziali = new Credentials(TEST_MAIL, password, Role.PATIENT);
            loginAndRegistrationDAO.login(credenziali);

        } catch (WrongEmailOrPasswordException e) {
            // Se la registrazione fallisce, eseguiamo l'inserimento dell'utente nel sistema
            // Creiamo un nuovo paziente e lo registriamo
            Patient paziente = new Patient(new Credentials(TEST_MAIL, password, Role.PATIENT));
            loginAndRegistrationDAO.insertUser(paziente.getCredentials());

        } catch (LoginAndRegistrationException e) {
            // Gestiamo l'eccezione generica di registrazione
            throw new LoginAndRegistrationException("Errore nella registrazione: " + e.getMessage(), e);
        }
    }
}








