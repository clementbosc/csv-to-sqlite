import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

/**
 * Created by clementbosc on 11/02/2017.
 */
public class SQLiteDB {

    private String datebasePath;


    /**
     * SQLiteDB constructor
     * @param datebasePath chemin relatif vers la base de données
     */
    public SQLiteDB(String datebasePath) {
        File f = new File(datebasePath);

        //Si le fichier de base de donnée n'est pas trouvé, on en crée un
        if(!f.exists() || f.isDirectory()) {
            createNewDatabase(datebasePath);
        }

        this.datebasePath = datebasePath;
    }


    /**
     * Crée une nouvelle base de donnée SQLite à l'emplacement spécifié
     * @param fileName l'emplacement de la BD
     */
    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Fonction de connexion à la base de donnée
     * @return la Conenxion
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+this.datebasePath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     * Crée une table intitulée tableName si elle n'existe pas déjà dans la bd
     * @param tableName nom de la table à créer
     */
    public void createTable(String tableName){
        String url = "jdbc:sqlite:" + this.datebasePath;

        // Requete SQL pour creation de la table
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	date date NOT NULL,\n"
                + "	value real NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Insere dans la table tableName le ligne (date, value) passée en param
     * @param tableName nom de la table
     * @param date param date de la donnée
     * @param value param value de la donnée
     */
    public void insert(String tableName, String date, float value) {
        String sql = "INSERT INTO "+tableName+"(date,value) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setFloat(2, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Exécute une requête
     * @param query requête à exécuter
     */
    public void execQuery(String query){
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
