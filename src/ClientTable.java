import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTable {
    // frame
    JFrame f;
    // Table
    JTable j;
    private static final Logger LOGGER = Logger.getLogger(Methods.class.getName());

    // Constructor
    ClientTable() {

        // Create JFrame
        f = new JFrame();
        // Set the frame title
        f.setTitle("Dados do Cliente");

        // Read data from the file and fill the table
        String[] elements;
        int numberLines = 0;
        int position = 0;

        String[][] content = new String[0][];
        try (BufferedReader reader = new BufferedReader(new FileReader("Client_Flight_Info.txt"))) {
            // Count the number of lines in the file
            while (reader.readLine() != null) {
                numberLines++;
            }
            content = new String[numberLines][];
        } catch (IOException e) {
            // Handle I/O exception and log
            LOGGER.log(Level.SEVERE, "Ocorreu uma IOException", e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("Client_Flight_Info.txt"))) {
            String line;
            // Read each line and fill the content array
            while ((line = reader.readLine()) != null) {
                // Remove brackets from the line
                line = line.substring(1, line.length() - 1);
                // Split the line into elements using a comma as the separator
                elements = line.split(",");
                // Assign the elements to the content array
                content[position] = elements;
                position++;
            }
        } catch (IOException e) {
            // Handle I/O exception and log
            LOGGER.log(Level.SEVERE, "Ocorreu uma IOException", e);
        }

        // Set column names for the JTable
        String[] columnNames = {"Nome", "País", "Seguro", "Bagagem", "Check-in Automático", "Assento do Cliente", "Número do Cartão", "Validade do Cartão", "Código de Segurança", "Método de Pagamento", "Custo Total", "ID do Voo", "ID do Cliente", "Data e Hora da Reserva"};

        // Create JTable with the data and column names
        j = new JTable(content, columnNames);
        j.setBounds(30, 40, 200, 300);

        // Add JScrollPane to the frame and set frame properties
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        f.setSize(500, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    // Main method to create an instance of ClientTable
    public static void main(String[] args)
    {
        new ClientTable();
    }
}
