package test;

import com.theradiary.ispwtheradiary.engineering.dao.*;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
/*       Sofia Tosti 0308023                 */
class TestPatient {
    //Variabili di test
    private static final String TEST_MAIL = "test@gmail.com";
    private String password;
    private static final String TEST_DIARY_CONTENT = "Test content";
    private static final String TEST_TASK_NAME = "Test task";
    private static final String TEST_SURNAME = "TestSurname";
    private static final String TEST_CITY = "TestCity";
    private static final String TEST_DESCRIPTION = "TestDescription";
    private static final boolean TEST_INPERSON = true;
    private static final boolean TEST_ONLINE = false;
    //DAO
    private LoginAndRegistrationDAO loginAndRegistrationDAO;
    private TaskAndToDoDAO taskAndToDoDAO;
    private CategoryAndMajorDAO categoryAndMajorDAO;
    private RetrieveDAO retrieveDAO;
    //Paziente di test
    private Patient testpatient;
    // Azione che viene svolta prima di ogni test
    @BeforeEach
    void setUp() {
        // genero una password
        password = generatePassword();

        // Inizializziamo i DAO una sola volta per evitare chiamate ripetute alla Factory
        loginAndRegistrationDAO = FactoryDAO.getDAO();
        taskAndToDoDAO = FactoryDAO.getTaskAndToDoDAO();
        categoryAndMajorDAO = FactoryDAO.getCategoryAndMajorDAO();
        retrieveDAO = FactoryDAO.getRetrieveDAO();
        // Creo un paziente di test
        testpatient = createPatient();


    }
    // Azione che viene svolta dopo ogni test
    @AfterEach
    void tearDown() {
        // Rimuovo il paziente creato durante il test
        loginAndRegistrationDAO.removePatient(testpatient);
    }
    // test per assicurarmi che un paziente dopo essersi registrato può svolgere il login
    @Test
    void testCredential() {
        try{
            // Registriamo il paziente
            registerTester();
            Credentials credentials = new Credentials(TEST_MAIL, password, Role.PATIENT);
            // Effettuiamo il login
            loginAndRegistrationDAO.login(credentials);
        } catch (WrongEmailOrPasswordException | LoginAndRegistrationException e) {
            // Se il login fallisce, il test fallisce
            Assertions.fail("Errore: " + e.getMessage());
        }

    }

    //test paziente scrive il diario e lo recupera
    @Test
    void testDiary() {
        LocalDate today = LocalDate.now();

        try {
            // Salva il diario per oggi
            taskAndToDoDAO.diary(testpatient, TEST_DIARY_CONTENT, today);

            // Recupera il diario per oggi
            Optional<String> retrievedDiaryOpt = taskAndToDoDAO.getDiaryForToday(testpatient);
            String retrievedDiary = retrievedDiaryOpt.orElse("");

            Assertions.assertEquals(TEST_DIARY_CONTENT, retrievedDiary,
                    "Il contenuto del diario recuperato non corrisponde a quello previsto.");
            // Rimuove il diario
            taskAndToDoDAO.removeDiaryEntry(today, testpatient);

            // Prova a recuperare di nuovo il diario
            Optional<String> retrievedDiaryAfterRemoval = taskAndToDoDAO.getDiaryForToday(testpatient);

            // Verifica che il diario sia stato effettivamente rimosso
            Assertions.assertTrue(retrievedDiaryAfterRemoval.isEmpty(),
                    "Il diario non è stato rimosso correttamente.");

        } catch (Exception e) {
            Assertions.fail("Test del diario fallito a causa di un'eccezione: " + e.getMessage());
        }
    }
    //test per il paziente che aggiunge una categoria e la recpera correttamente
    @Test
    void testRetrieveCategories() {
        Category category = Category.OTHER;

        try {
            categoryAndMajorDAO.addCategory(testpatient, category);
            retrieveDAO.retrieveCategories(testpatient);

            Assertions.assertTrue(testpatient.getCategories().contains(category),
                    "La categoria aggiunta non è presente nella lista delle categorie del paziente.");
            // Rimuove la categoria dal paziente
            categoryAndMajorDAO.removeCategory(testpatient, category);
            retrieveDAO.retrieveCategories(testpatient); // Aggiorna le categorie dopo la rimozione

            Assertions.assertFalse(testpatient.getCategories().contains(category),
                    "La categoria rimossa è ancora presente nella lista delle categorie del paziente.");
        } catch (Exception e) {
            Assertions.fail("Test del recupero categorie fallito: " + e.getMessage());
        }
    }
    //creo un paziente
    private Patient createPatient() {
        return new Patient(
                new Credentials(TEST_MAIL, password, Role.PATIENT),
                TEST_TASK_NAME,
                TEST_SURNAME,
                TEST_CITY,
                TEST_DESCRIPTION,
                TEST_INPERSON,
                TEST_ONLINE
        );
    }
    //genero una password
    private String generatePassword() {
        return "test" + System.currentTimeMillis();
    }
    //registrazione paziente
    private void registerTester() {
        try {
            loginAndRegistrationDAO.registerPatient(testpatient);
        } catch (MailAlreadyExistsException e) {
            // Se l'email è già registrata, non falliamo il test, ma facciamo direttamente il login
            Assertions.assertDoesNotThrow(() -> loginAndRegistrationDAO.login(new Credentials(TEST_MAIL, password, Role.PATIENT)),
                    "L'email era già registrata, ma il login ha fallito.");
        } catch (LoginAndRegistrationException e) {
            Assertions.fail("Test fallito a causa di un'eccezione inattesa durante la registrazione: " + e.getMessage());
        }
    }
}










