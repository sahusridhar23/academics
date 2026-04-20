# Complaint Portal

A web-based complaint management system built with Java Servlets and Tomcat. This application allows users to submit complaints and view existing complaints through an intuitive web interface.

---

## Prerequisites

Ensure you have the following installed on your system:

| Requirement    | Version            |
| -------------- | ------------------ |
| Java JDK       | 8 or above         |
| Apache Tomcat  | 9 or 10            |
| Web Browser    | Any modern browser |
| IDE (Optional) | VS Code / IntelliJ |

---

## Setup Instructions

### Step 1: Download and Extract the Project

Download the project ZIP file and extract it to your desired location.

### Step 2: Place Project in Tomcat

Move the project folder to your Tomcat webapps directory:

```
<tomcat-folder>/webapps/
```

**Example for Windows:**

```
C:\tomcat\webapps\complaintportalm1
```

### Step 3: Verify Required Dependencies

Ensure the Gson library is present:

```
WEB-INF/lib/gson-2.10.1.jar
```

If missing, download [Gson 2.10.1](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar) and place it in the `WEB-INF/lib/` directory.

### Step 4: Compile Java Servlets

Open a terminal/command prompt and navigate to the project's `WEB-INF/src` directory:

```bash
cd WEB-INF/src
```

Compile all Java files:

**Windows:**

```bash
javac -cp "C:\tomcat\lib\servlet-api.jar;C:\tomcat\webapps\complaintportalm1\WEB-INF\lib\gson-2.10.1.jar" -d "C:\tomcat\webapps\complaintportalm1\WEB-INF\classes" *.java
```

**Linux/Mac:**

```bash
javac -cp "~/tomcat/lib/servlet-api.jar:~/tomcat/webapps/complaintportalm1/WEB-INF/lib/gson-2.10.1.jar" -d "~/tomcat/webapps/complaintportalm1/WEB-INF/classes" *.java
```

### Step 5: Start Tomcat Server

**Windows:**

```bash
<tomcat-folder>\bin\startup.bat
```

**Linux/Mac:**

```bash
<tomcat-folder>/bin/startup.sh
```

### Step 6: Access the Application

Open your web browser and navigate to:

```
http://localhost:8080/complaintportalm1
```
