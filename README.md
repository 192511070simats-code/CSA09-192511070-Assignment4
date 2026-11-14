
# CSA09-192511070-Assignment4

## Student Details

- **Name:** K Suthesika
- **Register Number:** 192511070
- **Course:** Programming in Java
- **Java Version:** JDK 21.0.13 (Temurin)

## Assignment Tasks

| Task | Description |
|---|---|
| Task 01 | GUI Design Review |
| Task 02 | Event-Driven Design |
| Task 03 | Campus Library Search Screen Layout |
| Task 04 | Event-Handling Forensics |
| Task 05 | Currency Converter using Four Event-Handling Styles |
| Task 06 | Campus Event Registration System |

## Task 01 – GUI Design Review

Reviewed a fee-payment interface, identified event-handling and design defects, and redesigned the application using separation of concerns.

## Task 02 – Event-Driven Design

Designed an event-driven hospital outpatient appointment system, including event tables, component hierarchy, pseudocode, and window lifecycle handling.

## Task 03 – Campus Library Search Screen

Implemented a Java Swing library search interface using nested panels and layout managers. Tested the interface at different window sizes.

## Task 04 – Event-Handling Forensics

Identified and corrected mouse event-handling defects. Implemented persistent drawing using an ArrayList of Point objects and repainting.

## Task 05 – Currency Converter

Four implementations demonstrate different Java event-handling techniques:

1. `Converter.java` – Lambda expression
2. `ConverterFrameListener.java` – Frame implements ActionListener
3. `ConverterNamedInner.java` – Named inner class
4. `ConverterAnonymous.java` – Anonymous inner class

All four versions support conversion using the Convert button and Enter key. They validate numeric input and display the converted amount or an error message.

The conversion rates are illustrative fixed values and are not live exchange rates.

### Compile and Run Task 05

Open PowerShell in the project root:

```powershell
cd Task05
javac Converter.java
java Converter
```

To run the other versions:

```powershell
javac ConverterFrameListener.java
java ConverterFrameListener

javac ConverterNamedInner.java
java ConverterNamedInner

javac ConverterAnonymous.java
java ConverterAnonymous
```

## Task 06 – Campus Event Registration System

Developed a Java Swing desktop application connected to MySQL for managing campus event registrations.

### Features

- Student registration with input validation.
- Duplicate registration prevention.
- Committee dashboard to view registrations.
- Filter registrations by event.
- Export registration records to CSV.
- Background database operations using SwingWorker.

### Technologies Used

- Java JDK 21
- Java Swing
- MySQL Server 8.0
- JDBC
- MySQL Connector/J 26.10.0

### Database Details

- Database name: `campus_event_db`
- SQL setup script: `Task06/campus_event_db.sql`
- Tables: `events` and `registrations`
- A unique constraint on student ID and event ID prevents duplicate registrations for the same event.

### Task 06 Project Files

- `DBConnection.java` – Establishes the MySQL connection.
- `DatabaseTest.java` – Tests the database connection.
- `Event.java` – Represents event details.
- `Registration.java` – Represents registration details.
- `RegistrationDAO.java` – Handles database operations.
- `RegistrationService.java` – Validates registration details.
- `RegistrationFrame.java` – Provides the graphical user interface.
- `ExportService.java` – Exports registration details to CSV.

### Compile and Run Task 06

Set the MySQL password in PowerShell before running:

```powershell
$secure = Read-Host "Enter MySQL root password" -AsSecureString
$env:DB_PASSWORD = [System.Net.NetworkCredential]::new("", $secure).Password
```

Compile all Java source files:

```powershell
javac -cp "Task06\lib\mysql-connector-j-26.10.0.jar" -d Task06\out Task06\src\*.java
```

Run the application:

```powershell
java -cp "Task06\out;Task06\lib\mysql-connector-j-26.10.0.jar" RegistrationFrame
```

### Task 06 Test Evidence

The following tests were performed:

- Successful student registration.
- Empty and invalid input validation.
- Duplicate registration prevention.
- Registration for another event.
- Committee dashboard display.
- Event-based filtering.
- CSV export.

Screenshots are available in `Task06/Screenshots/`.

## Project Structure

```text
CSA09-192511070-Assignment4/
│
├── Task01/
├── Task02/
├── Task03/
├── Task04/
├── Task05/
│
├── Task06/
│   ├── src/
│   ├── out/
│   ├── lib/
│   ├── Screenshots/
│   └── campus_event_db.sql
│
├── Assignment_4.docx
├── README.md
└── .gitignore
```

## General Notes

- Generated `.class` files are excluded from Git using `.gitignore`.
- Task 05 uses illustrative fixed currency conversion rates.
- Task 06 requires MySQL Server and the MySQL Connector/J library.
- The MySQL password is supplied through the `DB_PASSWORD` environment variable and is not stored in the source code.
- Task 06 compiled output is stored in `Task06/out/` and excluded from Git.
- Screenshots and documentation are included as assignment evidence.
