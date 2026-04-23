# 🎓 Silicon University — Complaint Management Portal

A lightweight web application that allows students and staff of Silicon University to submit and view complaints across various campus categories. Built with Java Servlets, JDBC, and a plain HTML/CSS/JS frontend.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Project Structure](#-project-structure)
- [Database Setup](#-database-setup)
- [DB Credentials Configuration](#-db-credentials-configuration)
- [Running the Project](#-running-the-project)
- [API Endpoints](#-api-endpoints)
- [Dependencies (JARs)](#-dependencies-jars)

---

## ✨ Features

- Submit a complaint with your name, category, title, and description
- View all submitted complaints on a live dashboard
- Complaint categories: Classroom, Hostel, Library, Cafeteria, IT/Network, Other
- Status badges (Pending, In Progress, Resolved) on complaint cards
- Clean responsive UI with Silicon University branding
- REST-style JSON API backed by Java Servlets

---

## 🛠 Tech Stack

| Layer      | Technology                          |
|------------|-------------------------------------|
| Backend    | Java Servlets (Jakarta EE)          |
| Database   | PostgreSQL                          |
| JDBC Driver| postgresql-42.7.10.jar              |
| JSON       | Google Gson 2.10.1                  |
| Frontend   | HTML5, CSS3, Vanilla JavaScript     |
| Server     | Apache Tomcat 10+ (Jakarta EE)      |

---

## ✅ Prerequisites

Make sure you have the following installed before running the project:

| Tool              | Version / Notes                                      |
|-------------------|------------------------------------------------------|
| Java (JDK)        | Java 17 or higher (uses `record` keyword)            |
| Apache Tomcat     | Version **10 or higher** (required for `jakarta.*` namespace) |
| PostgreSQL        | Version 13 or higher                                 |
| A browser         | Any modern browser (Chrome, Firefox, Edge)           |

> ⚠️ **Important:** Do NOT use Tomcat 9 or lower — this project uses `jakarta.servlet.*` imports which are only available from Tomcat 10+.

---

## 📁 Project Structure

```
Complaint_portal-main/
│
├── index.html                   # Dashboard — lists all complaints
├── add.html                     # Form to submit a new complaint
│
├── css/
│   └── style.css                # Shared stylesheet for all pages
│
├── images/
│   └── sit.png                  # Silicon University logo
│
└── WEB-INF/
    ├── src/                     # Java source files
    │   ├── DBconnection.java    # ⚠️ DB credentials live here
    │   ├── AddComplaintServlet.java
    │   └── GetComplaintsServlet.java
    │
    ├── classes/                 # Compiled .class files
    │   ├── DBconnection.class
    │   ├── Complaint.class
    │   ├── AddComplaintServlet.class
    │   └── GetComplaintsServlet.class
    │
    └── lib/                     # Required JAR dependencies
        ├── gson-2.10.1.jar
        └── postgresql-42.7.10.jar
```

---

## 🗄 Database Setup

### Step 1 — Start PostgreSQL

Make sure your PostgreSQL server is running.

### Step 2 — Create Database and Table

Run the provided `schema.sql` file:

```bash
psql -U postgres -f schema.sql
```

Or manually run these commands in `psql`:

```sql
CREATE DATABASE complaints;

\c complaints

CREATE TABLE complaints (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    category    VARCHAR(50)   NOT NULL,
    title       VARCHAR(120)  NOT NULL,
    description VARCHAR(1000) NOT NULL,
    status      VARCHAR(20)   DEFAULT 'Pending',
    created_at  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);
```

### Step 3 — (Optional) Load Sample Data

```bash
psql -U postgres -d complaints -f sample_data.sql
```

---

## 🔑 DB Credentials Configuration

Open the file:

```
WEB-INF/src/DBconnection.java
```

Update the following three lines to match your PostgreSQL setup:

```java
private static String url      = "jdbc:postgresql://localhost:5432/complaints";
private static String username = "postgres";
private static String password = "your_password_here";
```

> ⚠️ **Security Warning:** Credentials are hardcoded directly in `DBconnection.java`. Make sure you update the url and credentials

---

## 🚀 Running the Project

### Step 1 — Clone the Repository

```bash
git clone https://github.com/your-username/Complaint_portal.git
cd Complaint_portal-main
```

### Step 2 — Compile the Java Source Files

From the project root, compile all `.java` files in `WEB-INF/src/` and output to `WEB-INF/classes/`:

```bash
javac -cp "WEB-INF/lib/*:/path/to/tomcat/lib/servlet-api.jar" \
      -d WEB-INF/classes \
      WEB-INF/src/*.java
```

> Replace `/path/to/tomcat/` with your actual Tomcat installation directory (e.g., `/opt/tomcat` or `C:\tomcat`).

### Step 3 — Deploy to Tomcat

Copy (or move) the entire project folder into Tomcat's `webapps/` directory and rename it to match the context path used in the frontend JS:

```bash
cp -r Complaint_portal-main /path/to/tomcat/webapps/complaintportalm1
```

The folder must be named **`complaintportalm1`** because the frontend JavaScript calls:
```
http://localhost:8080/complaintportalm1/complaints
http://localhost:8080/complaintportalm1/addComplaint
```

### Step 4 — Start Tomcat

```bash
# Linux / macOS
/path/to/tomcat/bin/startup.sh

# Windows
\path\to\tomcat\bin\startup.bat
```

### Step 5 — Open in Browser

```
http://localhost:8080/complaintportalm1/index.html
```

---

## 🔌 API Endpoints

| Method | Endpoint           | Description                      |
|--------|--------------------|----------------------------------|
| GET    | `/complaints`      | Returns all complaints as JSON   |
| POST   | `/addComplaint`    | Submits a new complaint          |

**POST `/addComplaint` — Form Parameters:**

| Parameter     | Type   | Max Length | Required |
|---------------|--------|------------|----------|
| `name`        | String | 100        | ✅       |
| `category`    | String | 50         | ✅       |
| `title`       | String | 120        | ✅       |
| `description` | String | 1000       | ✅       |

---

## 📦 Dependencies (JARs)

Both JARs are already included in `WEB-INF/lib/` — no manual download needed.

| JAR File                    | Purpose                          |
|-----------------------------|----------------------------------|
| `gson-2.10.1.jar`           | Java object ↔ JSON conversion    |
| `postgresql-42.7.10.jar`    | PostgreSQL JDBC driver           |

You will also need `servlet-api.jar` (or `jakarta.servlet-api`) at **compile time** only — this is provided by Tomcat and should NOT be placed in `WEB-INF/lib/`.

---

## 👥 Developers

- [sahusridhar23](https://github.com/sahusridhar23)  
- [AmlanSP](https://github.com/AmlanSP)
- [Ayush-2604](https://github.com/Ayush-2604)  
