package aasim.ris;

import datastorage.User;
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JavaFX App
 */
public class App extends Application {

    public static User user;
    public static String fileName = "risDirectory";

    @Override
    public void start(Stage stage) {

        //Creating, Editing, Adding stuff to the scene
        //Add stuff to the Stage
        stage = new Login();
        stage.setMaximized(true);
        stage.show();
        //

    }

    public static void main(String[] args) {
        //Create
        createDatabase(fileName);
        createUserTable(fileName);
        createAppointmentTable(fileName);
        createPatientTable(fileName);
        createStatusCodesTable(fileName);
        createOrderCodesTable(fileName);
        createImageTable(fileName);
        //Populate
        populateTablesReceptionist(fileName);
        populateTablesStatus(fileName);
        populateTablesTech(fileName);
        populateTablesDoc(fileName);
    	populateTablesRad(fileName);
////        Duplication bug if you run these multiple times, leave commented out
//        populateAppointments(fileName);
        launch();
    }

    //Create a database
    public static void createDatabase(String fileName) {
        String url = "jdbc:sqlite:C:/sqlite/" + fileName;
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Functionality only does Users as of now.
    //In future, all tables to be created will be put in here
    public static void createUserTable(String fileName) {
        String url = "jdbc:sqlite:C://sqlite/" + fileName;
        String sql = "CREATE TABLE users (\n"
                + "	user_id INTEGER PRIMARY KEY ,\n"
                + "	email VARCHAR(45) UNIQUE NOT NULL,\n"
                + "	full_name VARCHAR(45) NOT NULL,\n"
                + "	username VARCHAR(25) UNIQUE NOT NULL,\n"
                + "	password VARCHAR(64) NOT NULL,\n"
                + "     role TINYINT NOT NULL DEFAULT 0,\n"
                + "	enabled BIT NOT NULL DEFAULT 1\n"
                + ");";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createAppointmentTable(String fileName) {
        String url = "jdbc:sqlite:C://sqlite/" + fileName;
        //apptId, patientID, fullname, time, address, insurance, referral, status, order
        String sql = "CREATE TABLE appointments (\n"
                + "	appt_id INTEGER PRIMARY KEY UNIQUE,\n"
                + "	patient_id INTEGER NOT NULL,\n"
                + "	full_name VARCHAR(45) NOT NULL,\n"
                + "	time VARCHAR(25) NOT NULL,\n"
                + "	address VARCHAR(64) NOT NULL,\n"
                + "     insurance VARCHAR(64) NOT NULL,\n"
                + "     referral_doc_id VARCHAR(64) NOT NULL,\n"
                + "     statusCode INTEGER NOT NULL,\n" //0 inprogress, 1 complete
                + "	patient_order VARCHAR(45)\n"
                + ");";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createPatientTable(String fileName) {
        String url = "jdbc:sqlite:C://sqlite/" + fileName;
        String sql = "CREATE TABLE patients (\n"
                + "	patientID INTEGER PRIMARY KEY ,\n"
                + "	email VARCHAR(45) UNIQUE NOT NULL,\n"
                + "	full_name VARCHAR(45) NOT NULL,\n"
                + "	dob VARCHAR(45) NOT NULL,\n"
                + "	address VARCHAR(64) NOT NULL,\n"
                + "     insurance VARCHAR(64) NOT NULL,\n"
                + "     referralDocId VARCHAR(64) NOT NULL,\n"
                + "	patientOrder VARCHAR(100),\n"
                + "	appointmentIDs VARCHAR(45)\n"
                + ");";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createStatusCodesTable(String fileName) {
        String url = "jdbc:sqlite:C://sqlite/" + fileName;
        String sql = "CREATE TABLE statusCode (\n"
                + "	statusID INTEGER PRIMARY KEY,\n"
                + "	status VARCHAR(45)\n"
                + ");";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createOrderCodesTable(String fileName) {
        String url = "jdbc:sqlite:C://sqlite/" + fileName;
        String sql = "CREATE TABLE orderCodes (\n"
                + "	orderID INTEGER PRIMARY KEY,\n"
                + "	orders VARCHAR(45)\n"
                + ");";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createImageTable(String fileName) {
        String url = "jdbc:sqlite:C://sqlite/" + fileName;
        String sql = "CREATE TABLE images (\n"
                + "	imageID INTEGER PRIMARY KEY,\n"
                + "	patientID INTEGER,\n"
                + "	apptID INTEGER,\n"
                + "	image BLOB\n"
                + ");";
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public static void createRadReportTable(String fileName) {
    	String url = "jdbc:sqlite:C://sqlite/" + fileName;
    	String sql = "CREATE TABLE report(\n"
    			+"apptID INTEGER, \n"
    			+"orderID INTEGER, \n"
    			+"referralDocId VARCHAR(64) NOT NULL,\n"
    			+"writtenreport VARCHAR(max)"
    			+");";
    }

    //In future, all population statements will be put in here
    public static void executeSQLStatement(String fileName, String sql) {
        String url = "jdbc:sqlite:C://sqlite/" + fileName;
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void populateTablesReceptionist(String fileName) {
        String sql = "INSERT INTO users(email, full_name, username, password, role) VALUES ('exampleemail@gmail.com', 'Sadie Greenhorn', 'receptionist', 'receptionist', '2');\n";
        executeSQLStatement(fileName, sql);
    }

    public static void populateTablesTech(String fileName) {
        String sql = "INSERT INTO users(email, full_name, username, password, role) VALUES ('techdude@gmail.com', 'dude dude', 'tech', 'tech', '3');\n";
        executeSQLStatement(fileName, sql);
    }
    
    public static void populateTablesRad(String fileName) {
    	String sql = "INSERT INTO users (email, full_name, username, password, role) VALUES('raddude@gmail.com', 'rad dude', 'rad', 'rad', '4');\n";
    	executeSQLStatement(fileName, sql);
    }

    public static void populateTablesStatus(String fileName) {
        String sql = "INSERT INTO statusCode VALUES ('0', 'Patient did not show');\n";
        String sql1 = "INSERT INTO statusCode VALUES ('1', 'appointment scheduled');\n";
        String sql2 = "INSERT INTO statusCode VALUES ('2', 'checked in');\n";
        String sql3 = "INSERT INTO statusCode VALUES ('3', 'Patient pulled back for xray');\n";
        String sql4 = "INSERT INTO statusCode VALUES ('4', 'Patient done with xray');\n";
        String sql5 = "INSERT INTO statusCode VALUES ('5', 'Patient insurance billed');\n";
        String sql6 = "INSERT INTO statusCode VALUES ('6', 'Patient sent remaining bill after insurance');\n";
        String sql7 = "INSERT INTO statusCode VALUES ('7', 'Patient Missed Appointment');\n";

        executeSQLStatement(fileName, sql);
        executeSQLStatement(fileName, sql1);
        executeSQLStatement(fileName, sql2);
        executeSQLStatement(fileName, sql3);
        executeSQLStatement(fileName, sql4);
        executeSQLStatement(fileName, sql5);
        executeSQLStatement(fileName, sql6);
        executeSQLStatement(fileName, sql7);

    }

    public static void populateTablesDoc(String fileName) {
        String sql = "INSERT INTO users(email, full_name, username, password, role) VALUES ('notanorangutan@gmail.com', 'Orangutan Dave', 'doc', 'doc', '5');\n";
        executeSQLStatement(fileName, sql);
    }

    private static void populateAppointments(String fileName) {
        String sql = "INSERT INTO appointments(patient_id, full_name, time, address, insurance, referral_doc_id, patient_order, statusCode) VALUES('0','Loki Barnes','2022-02-24 18:00', '3012 Popcorn Avenue Texas Illinois', 'PeachTree Healthcare', 'Vad', 'xray', '0');";
        executeSQLStatement(fileName, sql);
        sql = "INSERT INTO appointments(patient_id, full_name, time, address, insurance, referral_doc_id, patient_order, statusCode) VALUES('" + 1 + "','Candive Sharp','2022-02-24 18:30', '3013 Popcorn Avenue Texas Illinois', 'PeachTree Healthcare', 'Vad', 'xray', '0');";
        executeSQLStatement(fileName, sql);
    }

}
