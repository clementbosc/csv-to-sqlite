import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

/**
 * Created by clementbosc on 11/02/2017.
 */
public class SQLiteDB {

    //Chemin acces base de donnee sqlite3
    private String databasePath;


    /**
     * SQLiteDB constructor
     * @param databasePath chemin relatif vers la base de données
     */
    public SQLiteDB(String databasePath) {
        File f = new File(databasePath);

        //Si le fichier de base de donnée n'est pas trouvé, on en crée un
        if(!f.exists() || f.isDirectory()) {
            createNewDatabase(databasePath);
        }

        this.databasePath = databasePath;
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

                System.out.println("\nUne nouvelle base de donnée a été crée");
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
        String url = "jdbc:sqlite:"+this.databasePath;
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
        String url = "jdbc:sqlite:" + this.databasePath;

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
