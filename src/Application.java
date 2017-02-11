import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by clementbosc on 11/02/2017.
 */
public class Application {

    //Chemin vers la base de donnée sqlite3
    public String databasePath;

    //Chemin vers le fichier csv à importer
    public String csvPath;

    //Table à créer et dans laquelle insérer les données
    public String tableName;


    /**
     * Application constructor
     */
    public Application() throws FileNotFoundException {

        //Initialisation du programme
        initialisation();

        //Instanciation de SQLiteDB
        SQLiteDB database = new SQLiteDB(databasePath);

        System.out.println("\nTraitement en cours");

        //création de la table si elle n'existe pas
        database.createTable(tableName);

        //Instanciation du CSVReader
        CSVReader reader = new CSVReader(csvPath, ",");

        //On génère la requête d'insertion
        String query = reader.getSQLInsertStatement(tableName);

        //On exécute la requête
        database.execQuery(query);

        System.out.println("\n\nTraitement terminé : les données ont été inséré dans la table "+tableName+" du fichier SQLite "+databasePath);
    }


    /**
     * Fonction d'initialisation du programme
     */
    public void initialisation(){
        System.out.println("*************************************");
        System.out.println("****   CSV -> SQLite Converter   ****");
        System.out.println("*************************************");


        Scanner sc = new Scanner(System.in);
        System.out.println("\nVeuillez entrer un fichier .sqlite3 dans lequel les données seront insérées " +
                "(si le fichier n'existe pas il sera créé) :");

        this.databasePath = sc.nextLine();

        System.out.println("\nVeuillez entrer une table dans laquelle les données seront insérées " +
                "(si la table n'existe pas elle sera créé, sinon les données seront ajoutées à la suite de celles existantes) :");

        this.tableName = sc.nextLine();

        System.out.println("\nVeuillez entrer un fichier CSV dans lequel les données sont actuellement stockées : ");

        this.csvPath = sc.nextLine();
    }

    public static void main(String[] args) {
        try {
            new Application();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
