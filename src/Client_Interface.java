import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Class that represents the main interface of the client
public class Client_Interface extends JFrame{
    // User interface components
    protected JTextField passengerName;protected JPanel MainPanel;protected JPanel ParentPanel;protected JPanel passengerDetails;protected JButton paymentButton;
    protected JPanel extraServices;protected JComboBox<String> countryInput;protected JTextField expirationDateInput;protected JTextField numCardInput;protected JRadioButton yesRadioInsurance;
    protected JComboBox<String> baggageInput;protected JRadioButton noRadioInsurance;protected JComboBox<String> seatsInput;protected JLabel imageSeats;protected JButton seatsButton;
    protected JPanel Seats;protected JPanel paymentDetails;protected JComboBox<String> Month;protected JTextField Year;protected JButton servicesButton;
    protected JRadioButton noCheckIn;protected JRadioButton yesCheckIn;protected JComboBox<String> paymentMethod;protected JButton passengerButton;

    // Constructor to set up the user interface
    public void setUpUI(){
        setContentPane(MainPanel);
        setTitle("Client Interface");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        // Reference array to visualize the position of each data
        // String[] tempArray={Name, Country, Insurance, Baggage, AutomaticCheckIn, ClientSeat, CardNum, Validity, SecCod, TotalCost, flight_id, client_id};

    }
    // Method to remove whitespace from a string
    protected static String removeWhitespace(String input) {
        return input.replaceAll("\\s", "");
    }
    // Method to create user interface components
    private void createUIComponents() {
        imageSeats =new JLabel(new ImageIcon("Airbus-A320-configuration-with-30-seat-rows.png"));
    }
}
// Class containing various utility methods
class Methods extends Client_Interface{
    private static final Logger LOGGER = Logger.getLogger(Methods.class.getName());
    // Method where the returned string depends on the state of the radio buttons
    protected static String radioCheck(boolean yes, boolean no){

        if (yes && no || !yes && !no){
            return "invalid";
        } else if (yes) {
            return "Yes";
        }else{
            return "No";
        }
    }
    // Method to detect if the string contains only non-numeric characters
    protected static String notNumericCheck(String str){
        if (str.matches("^[ A-Za-z]+$")){
            return str;
        }else {
            return "invalid";
        }
    }
    // Method to detect if the string contains only numeric characters
    protected static String numericCheck(String str){
        if (str.matches("\\d+")){
            return str;
        }else {return "invalid";}
    }
    // Method to remove occupied seats from the list of available seats
    protected static void removeSeats(ArrayList<String> seats, JComboBox<String> seatsInput){
        String[] elements;
        try (BufferedReader reader = new BufferedReader(new FileReader("Client_Flight_Info.txt"))) {
            ArrayList <String>OcuppiedSeats=new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                elements = line.split(",");
                OcuppiedSeats.add(elements[5]);
            }
            OcuppiedSeats.replaceAll(Client_Interface::removeWhitespace);
            seats.removeAll(OcuppiedSeats);
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) seatsInput.getModel();
            model.removeAllElements();
            model.addAll(seats);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An IOException occurred", e);
        }
    }
    // Method to validate and process payment information
    protected static String[] payingMethodCheck(String numCard, String year, String code, String paymentNetwork, JComboBox<String> Month){
        String[] tempArray={numCard, year, code};
        for (int i=0; i<3;i++){
            if (Objects.equals(numericCheck(tempArray[i]), "invalid")){
                tempArray[i]="invalid";
                return tempArray;
            }
        }

        if ((Objects.equals(paymentNetwork, "Visa") || Objects.equals(paymentNetwork, "Mastercard")) && numCard.length()!=16){
            tempArray[0]="invalid";
            return tempArray;
        } else if (Objects.equals(paymentNetwork, "American Express") && numCard.length()!=15) {
            tempArray[0]="invalid";
            return tempArray;
        } else if (code.length()!=3){
            tempArray[2]="invalid";
        }else if (Integer.parseInt(tempArray[1])<2024){
            tempArray[1]="invalid";
        }else {
            tempArray[1]=(String) Month.getSelectedItem()+'/'+year;
            return tempArray;
        }
        return tempArray;
    }
    // Method to add the extra cost of optional services to the total cost
    protected static String custo(String insurance, String baggage, String seat){
        float totalCost=155.99f;
        float baggageExtraCost=40.00f;
        char row=seat.charAt(0);
        if (Objects.equals(insurance, "Yes")){
            totalCost=totalCost+82.51f;
        }
        for (int i=1; i<5;i++){
            if (Objects.equals(baggage, String.valueOf(i))){
                totalCost=totalCost+baggageExtraCost;
                break;}
            baggageExtraCost+=40.00f;}
        if ((int) row <6){
            totalCost+=95;
        }
        return String.valueOf(totalCost);
    }
    // Method to generate and compare IDs to avoid duplicate IDs
    protected static String compareID(int position, int lowerLimit, int upperLimit){
        String[] FileArray;
        ArrayList<String> FileID=new ArrayList<>();
        Random rand=new Random();
        String ID=String.valueOf(rand.nextInt(lowerLimit, upperLimit));
        try (BufferedReader reader = new BufferedReader(new FileReader("Client_Flight_Info.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                FileArray = line.split(",");
                FileID.add(FileArray[position]);
            }
            for (int i=0; i<FileID.size();i++){
                if (ID.equals(FileID.get(i))){
                    ID=String.valueOf(rand.nextInt(lowerLimit, upperLimit));
                    i=0;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An IOException occurred", e);
        }
        return ID;
    }
    // Method to obtain the array of total seats
    protected static String[] getStrings() {
        String NumSeats="1A,1B,1C,1D,1E,1F,2A,2B,2C,2D,2E,2F,3A,3B,3C,3D,3E,3F,4A,4B,4C,4D,4E,4F,5A,5B,5C,5D,5E,5F,6A,6B,6C,6D,6E,6F,7A,7B,7C,7D,7E,7F,8A,8B,8C,8D,8E,8F,9A,9B,9C,9D,9E,9F,10A,10B,10C,10D,10E,10F,11A,11B,11C,11D,11E,11F,12A,12B,12C,12D,12E,12F,13A,13B,13C,13D,13E,13F,14A,14B,14C,14D,14E,14F,15A,15B,15C,15D,15E,15F,16A,16B,16C,16D,16E,16F,17A,17B,17C,17D,17E,17F,18A,18B,18C,18D,18E,18F,19A,19B,19C,19D,19E,19F,20A,20B,20C,20D,20E,20F,21A,21B,21C,21D,21E,21F,22A,22B,22C,22D,22E,22F,23A,23B,23C,23D,23E,23F,24A,24B,24C,24D,24E,24F,25A,25B,25C,25D,25E,25F,26A,26B,26C,26D,26E,26F,27A,27B,27C,27D,27E,27F,28A,28B,28C,28D,28E,28F,29A,29B,29C,29D,29E,29F,30A,30B,30C,30D,30E";
        return NumSeats.split(",");
    }

}
// Class representing reservations and extending the client interface
class Bookings extends Client_Interface{
    private static final Logger LOGGER = Logger.getLogger(Bookings.class.getName());

    // Date and time variables
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);

    // Method to handle button actions
    public void buttons(){
        ArrayList <String> currentArray=new ArrayList<>();
        String[] seatArray = Methods.getStrings();
        ArrayList<String> totalSeats = new ArrayList<>(Arrays.asList(seatArray));

        // Action responsible for the validation and insertion of the client's name and nationality
        passengerButton.addActionListener(e -> {
            if (Objects.equals(Methods.notNumericCheck(passengerName.getText()), "invalid")){
                passengerName.setText("");
                countryInput.setSelectedIndex(0);
                JOptionPane.showMessageDialog(passengerDetails, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                currentArray.add(Methods.notNumericCheck(passengerName.getText()));//Name
                currentArray.add((String) countryInput.getSelectedItem());//Country
                ParentPanel.removeAll();
                ParentPanel.add(extraServices);
                ParentPanel.repaint();
                ParentPanel.revalidate();}
        });
        // Action responsible for the validation and insertion of extra services (travel insurance, extra baggage, automatic check-in)
        servicesButton.addActionListener(e -> {
            if (Objects.equals(Methods.radioCheck(yesRadioInsurance.isSelected(), noRadioInsurance.isSelected()), "invalid") || Objects.equals(Methods.radioCheck(yesCheckIn.isSelected(), noCheckIn.isSelected()), "invalid")){
                passengerName.setText("");
                yesRadioInsurance.setSelected(false);
                noRadioInsurance.setSelected(false);
                baggageInput.setSelectedIndex(0);
                yesCheckIn.setSelected(false);
                noCheckIn.setSelected(false);
                JOptionPane.showMessageDialog(extraServices, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                currentArray.add(Methods.radioCheck(yesRadioInsurance.isSelected(), noRadioInsurance.isSelected()));//Insurance
                currentArray.add((String) baggageInput.getSelectedItem());//Baggage
                currentArray.add(Methods.radioCheck(yesCheckIn.isSelected(), noCheckIn.isSelected()));//AutomaticCheckIn
                Methods.removeSeats(totalSeats, seatsInput);
                ParentPanel.removeAll();
                ParentPanel.add(Seats);
                ParentPanel.repaint();
                ParentPanel.revalidate();
            }
        });
        // Action responsible for the validation and insertion of the selection of available seats
        seatsButton.addActionListener(e -> {
            currentArray.add((String) seatsInput.getSelectedItem());//ClientSeat
            ParentPanel.removeAll();
            ParentPanel.add(paymentDetails);
            ParentPanel.repaint();
            ParentPanel.revalidate();
        });
        // Action responsible for the validation and insertion of payment details
        paymentButton.addActionListener(e -> {
            String[] PaymentArray=Methods.payingMethodCheck(numCardInput.getText(), Year.getText(), expirationDateInput.getText(), (String) paymentMethod.getSelectedItem(), Month);
            if (Objects.equals(PaymentArray[0], "invalid") || Objects.equals(PaymentArray[1], "invalid") || Objects.equals(PaymentArray[2], "invalid")){
                numCardInput.setText("");
                Month.setSelectedIndex(0);
                Year.setText("");
                expirationDateInput.setText("");
                paymentMethod.setSelectedIndex(0);
                JOptionPane.showMessageDialog(paymentDetails, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                currentArray.add(Methods.numericCheck(numCardInput.getText()));//CardNum
                currentArray.add(PaymentArray[1]);//Validity
                currentArray.add(Methods.numericCheck(expirationDateInput.getText()));//SecCod
                currentArray.add((String) paymentMethod.getSelectedItem());
                currentArray.add(Methods.custo(Methods.radioCheck(yesRadioInsurance.isSelected(), noRadioInsurance.isSelected()), (String) baggageInput.getSelectedItem(), (String) Objects.requireNonNull(seatsInput.getSelectedItem())));//TotalCost
                currentArray.add(Methods.compareID(10, 1000, 10000));//flight_ID
                currentArray.add(Methods.compareID(11, 10000000, 100000000)); //client_id
                currentArray.add(formattedDateTime);
                try (FileWriter fileWriter = new FileWriter("Client_Flight_Info.txt", true)) {
                    fileWriter.write(currentArray + "\n");
                    JOptionPane.showMessageDialog(paymentDetails, "Booking Complete", "Booking Complete", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException exception) {
                    LOGGER.log(Level.SEVERE, "An IOException occurred", exception);
                }
            }
            System.exit(0);
        });
    }
}
// Main class to run the client reservation program
class ClientReservations{
    public static void main(String[] args) {
        Bookings program=new Bookings();
        program.setUpUI();
        program.buttons();
    }
}
