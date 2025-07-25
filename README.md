# Java Swing Registration Form with SQLite Database

## Description
This project is a Java Swing application that provides a registration form allowing users to enter details such as ID, Name, Gender, Address, and Contact. The data is stored in an SQLite database and displayed in a table on the right side of the form.

## Prerequisites
- Java Development Kit (JDK) 8 or higher installed.
- SQLite JDBC driver (sqlite-jdbc-<version>.jar). You can download it from: https://github.com/xerial/sqlite-jdbc/releases

## Setup Instructions

1. **Download SQLite JDBC Driver**
   Download the latest `sqlite-jdbc-<version>.jar` file and place it in the project directory.

2. **Compile the Java Program**
   Open a terminal/command prompt in the project directory and run:
   ```
   javac -cp ".;sqlite-jdbc-<version>.jar" RegistrationForm.java
   ```
   Replace `<version>` with the actual version number of the downloaded JDBC jar.

3. **Run the Program**
   Run the program with the following command:
   ```
   java -cp ".;sqlite-jdbc-<version>.jar" RegistrationForm
   ```

## Usage
- Enter user details in the form fields.
- Click "Register" to save the data to the database.
- The registered users will be displayed in the table on the right.
- Click "Exit" to close the application.

## Database
- The program creates an SQLite database file named `registration.db` in the project directory.
- The `users` table stores the user details.

## Screenshots
Please run the application and take screenshots of:
- The registration form UI.
- The database file (`registration.db`) using any SQLite viewer.
- The source code files.

## GitHub Upload
To upload this project to GitHub:
1. Create a new repository on GitHub.
2. Initialize a git repository in your project folder:
   ```
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin https://github.com/yourusername/your-repo-name.git
   git push -u origin main
   ```
3. Replace the URL with your repository URL.

## Notes
- Ensure the SQLite JDBC jar is in the classpath when compiling and running.
- The program uses SQLite for simplicity; you can modify it to use other databases if needed.

---

If you need any further assistance, please let me know.
