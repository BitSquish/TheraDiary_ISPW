package test;

import com.theradiary.ispwtheradiary.dao.CategoryAndMajorDAO;
import com.theradiary.ispwtheradiary.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.dao.TaskAndToDoDAO;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
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

    private String password;
    private String testEmail;
    private final LocalDate today = LocalDate.now();

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
    void setUp() throws  LoginAndRegistrationException {
        // genero una password
        password = generatePassword();
        //test email
        testEmail=generateTestEmail();

        // Inizializziamo i DAO una sola volta per evitare chiamate ripetute alla Factory
        loginAndRegistrationDAO = FactoryDAO.getDAO();
        taskAndToDoDAO = FactoryDAO.getTaskAndToDoDAO();
        categoryAndMajorDAO = FactoryDAO.getCategoryAndMajorDAO();
        retrieveDAO = FactoryDAO.getRetrieveDAO();
        // Creo un paziente di test
        testpatient = createPatient();
        // Registriamo il paziente
        registerTester();


    }



    // Azione che viene svolta dopo ogni test
    @AfterEach
    void tearDown() {
        if(testpatient!=null){
            loginAndRegistrationDAO.removePatient(testpatient);
            taskAndToDoDAO.removeDiaryEntry(today, testpatient);
            categoryAndMajorDAO.removeCategory(testpatient, Category.OTHER);

        }

    }
    // test per assicurarmi che un paziente dopo essersi registrato può svolgere il login
    @Test
    void testCredential() {
        try{
            Credentials credentials = new Credentials(testEmail, password, Role.PATIENT);
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
        // Salva il diario per oggi
        taskAndToDoDAO.diary(testpatient, TEST_DIARY_CONTENT, today);

        // Recupera il diario per oggi
        Optional<String> retrievedDiaryOpt = taskAndToDoDAO.getDiaryForToday(testpatient);
        String retrievedDiary = retrievedDiaryOpt.orElse("");

        Assertions.assertEquals(TEST_DIARY_CONTENT, retrievedDiary,
                "Il contenuto del diario recuperato non corrisponde a quello previsto.");

    }
    //test per il paziente che aggiunge una categoria e la recpera correttamente
    @Test
    void testRetrieveCategories() {
        Category category = Category.OTHER;
        categoryAndMajorDAO.addCategory(testpatient, category);
        retrieveDAO.retrieveCategories(testpatient);

        Assertions.assertTrue(testpatient.getCategories().contains(category),
                "La categoria aggiunta non è presente nella lista delle categorie del paziente.");


    }
    //creo un paziente
    private Patient createPatient() {
        return new Patient(
                new Credentials(testEmail, password, Role.PATIENT),
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
    //genero un email di test
    private String generateTestEmail() {
        return "test" + System.currentTimeMillis() + "@gmail.com";
    }
    //registro il paziente
    private void registerTester() throws  LoginAndRegistrationException {
        try {
            loginAndRegistrationDAO.registerPatient(testpatient);
        } catch (MailAlreadyExistsException e) {
            throw new LoginAndRegistrationException("Errore nella registrazione del paziente", e);
        }
    }

}










