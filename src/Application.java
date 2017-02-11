import java.io.FileNotFoundException;

/**
 * Created by clementbosc on 11/02/2017.
 */
public class Application {

    //Chemin vers la base de donnée sqlite3
    public static final String DATABASE_PATH = "db/database.sqlite3";

    //Chemin vers le fichier csv à importer
    public static final String CSV_FILE_PATH = "docs/file.csv";

    //Table à créer et dans laquelle insérer les données
    public static final String TABLE_NAME = "data";


    /**
     * Application constructor
     */
    public Application() throws FileNotFoundException {
        //Instanciation de SQLiteDB
        SQLiteDB database = new SQLiteDB(DATABASE_PATH);

        //création de la table si elle n'existe pas
        database.createTable(TABLE_NAME);

        //Instanciation du CSVReader
        CSVReader reader = new CSVReader(CSV_FILE_PATH, ",");

        //On génère la requête d'insertion
        String query = reader.getSQLInsertStatement(TABLE_NAME);

        //On exécute la requête
        database.execQuery(query);
    }

    public static void main(String[] args) {
        try {
            new Application();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
