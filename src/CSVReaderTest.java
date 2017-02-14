import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;


/**
 * Created by clementbosc on 14/02/2017.
 */
public class CSVReaderTest {

    @Test
    public void getSQLInsertStatement() throws FileNotFoundException {
        CSVReader reader = new CSVReader("docs/test-file.csv", ",");
        assertEquals(reader.getSQLInsertStatement("data"), "INSERT INTO data (date, value) VALUES (\"2017-01-30T01:07:23+01:00\",\"45506\"), (\"2017-02-11T13:21:34+01:00\",\"47525\")");
    }

}