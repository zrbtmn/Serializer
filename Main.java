import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) {

        try {
            Person person = new Person("Sergey","Man",21);

            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;
            writer = new XMLWriter( System.out, format );
            writer.write( XMLConverter.XMLFile(person) );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
