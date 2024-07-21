
# Flight Reservation System Simulation

The Flight Reservation System is a Java-based simulation developed using IntelliJ IDEA and Swing, designed to demonstrate the complete process of booking a specific flight and managing passenger information through a graphical user interface. The system is divided into two main parts, each handled by separate Java files:

-Client_Interface.java: Manages the entire booking process, including entering passenger details, selecting seats, adding services, and processing payments.

-ClientTable.java: Displays a read-only table of all passengers' information, providing a management overview of all bookings for the specific flight.

Booking data is stored and can be accessed on the text file Client_Flight_Info.txt.
## Installation

1. Install Java JDK

-Go to the [Oracle JDK download page](https://www.oracle.com/java/technologies/downloads/#jdk22-linux).

-Select the appropriate JDK for your operating system (make sure it’s JDK 11 or higher).

-Download and install the JDK following the instructions provided on the website.

2. Install IntelliJ IDEA

-Visit the [IntelliJ IDEA download page](https://www.jetbrains.com/idea/download/?section=windows).

-Choose the edition you prefer (Community Edition is free and sufficient for this project).

-Download and install IntelliJ IDEA following the instructions on the site.

3. Git:

-Git is required for version control management and cloning the repository. Download and install it from [Git’s official site](https://git-scm.com/downloads).

4. Clone the repository by inputting the following to a Terminal:
```bash
    git clone https://github.com/sebasgim975/FlightReservationSystemSimulation.git
```
5. Open the project in IntelliJ IDEA:

-Start IntelliJ IDEA.

-Click "Open" and navigate to the project directory.

-Select the project to open it.

6. Configure the JDK:

-Go to File > Project Structure > Project.

-Set the Project SDK to the JDK version you installed (e.g., Java 11).

## Usage

To run the project:

-Open the Client_Interface.java or ClientTable.java from the project explorer.

-Right-click on the file in the editor and select Run 'Client_Interface.main()' or Run 'ClientTable.main()'.

For Booking:

-Start Client_Interface.java and follow the on-screen prompts to enter details, select a seat, add services, and provide payment information.

For Viewing Bookings:

-Run ClientTable.java to open a window displaying a table with all passenger booking details.
## Troubleshooting Common Issues

False Error Positives in IntelliJ IDEA

If IntelliJ IDEA shows error highlights that do not affect the program's execution, try the following steps to resolve them:

-Invalidate Caches and Restart

-Go to File > Invalidate Caches / Restart.
Click on Invalidate and Restart.

-Once IntelliJ IDEA restarts, it will rebuild the project index which can resolve false error positives.
## 🔗 Links
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/sebas-gim/)


