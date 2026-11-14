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

## Task 05 – Currency Converter

Four implementations demonstrate different Java event-handling techniques:

1. `Converter.java` – Lambda expression
2. `ConverterFrameListener.java` – Frame implements ActionListener
3. `ConverterNamedInner.java` – Named inner class
4. `ConverterAnonymous.java` – Anonymous inner class

All four versions support conversion using the Convert button and Enter key. They validate numeric input and display the converted amount or an error message.

## Compile and Run Task 05

Open PowerShell in the project root and run:

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

## Project Structure

- `Task01/` – GUI design review
- `Task02/` – Event-driven design documentation
- `Task03/` – Library search screen and screenshots
- `Task04/` – Event-handling forensics
- `Task05/` – Currency converter implementations and screenshots
- `Task06/` – Campus event registration system
- `Assignment_4.docx` – Assignment report

## Notes

- Generated `.class` files are excluded from Git using `.gitignore`.
- Task 05 uses illustrative fixed conversion rates, not live exchange rates.