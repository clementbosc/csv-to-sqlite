import java.io.*;
import java.util.ArrayList;

/**
 * Created by clementbosc on 11/02/2017.
 */
public class CSVReader {

    private String csvFilePath;
    private String separationChar;


    /**
     * CSVReader constructor
     * @param csvFilePath chemin d'accès vers le fichier CSV à parser
     * @param separationChar caractère de séparation des donnée (une virgule en général)
     */
    public CSVReader(String csvFilePath, String separationChar) throws FileNotFoundException {
        File f = new File(csvFilePath);

        if(!f.exists() || f.isDirectory()) {
            throw new FileNotFoundException("Le fichier "+csvFilePath+" n'a pas été trouvé");
        }

        this.csvFilePath = csvFilePath;
        this.separationChar = separationChar;
    }


    /**
     * Retourne une requête d'insersiond des données contenus dans le fichier CSV
     * @param tableName nom de la table dans laquelle insérer les données
     * @return la requête générée
     */
    public String getSQLInsertStatement(String tableName){
        String line = "";

        //La liste contriendra pour chaque ligne du fichier un couple data, value sous forme de String séparé par une virgule
        ArrayList<String> array = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(this.csvFilePath))) {

            //Initialisation de la requête
            String statement = "INSERT INTO "+tableName+" (date, value) VALUES ";

            //Pour chaque ligne du fichier on boucle
            while ((line = br.readLine()) != null) {

                String[] results = line.split(this.separationChar);
                //On ajoute le couple data/value à l'arrayList
                array.add("(\""+ results[0] +"\",\""+results[1]+"\")");
            }

            //On supprimer le premier élément (représente l'en-tête du des colonnes du fichier csv)
            array.remove(0);

            //On ajoute toutes les valeurs à insérer à la requête précedement initialisée
            statement += String.join(", ", array);

            return statement;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
