import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

/**
 * Created by clementbosc on 11/02/2017.
 */
public class SQLiteDB {

    //Chemin acces base de donnee sqlite3
    private String databasePath;

    private Connection c = null;


    /**
     * SQLiteDB constructor
     * @param databasePath chemin relatif vers la base de données
     */
    public SQLiteDB(String databasePath) throws SQLException {
        File f = new File(databasePath);

        //Si le fichier de base de donnée n'est pas trouvé, on en crée un
        if(!f.exists() || f.isDirectory()) {
            System.out.println("\nUne nouvelle base de donnée va être crée");
        }

        this.databasePath = databasePath;
        this.connect();
    }



    /**
     * Fonction de connexion à la base de donnée
     */
    private void connect() throws SQLException {
        // SQLite connection string
        String url = "jdbc:sqlite:"+this.databasePath;

        this.c = DriverManager.getConnection(url);
    }


    /**
     * Crée une table intitulée tableName si elle n'existe pas déjà dans la bd
     * @param tableName nom de la table à créer
     */
    public void createTable(String tableName) throws SQLException {

        // Requete SQL pour creation de la table
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	date date NOT NULL,\n"
                + "	value real NOT NULL\n"
                + ");";


        Statement stmt = this.c.createStatement();
        stmt.execute(createTableStatement);
    }


    /**
     * Exécute une requête
     * @param query requête à exécuter
     */
    public void execQuery(String query) throws SQLException {
        PreparedStatement pstmt = this.c.prepareStatement(query);
        pstmt.executeUpdate();
    }
}
