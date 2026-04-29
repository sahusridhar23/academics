# Silicon University — Complaint Management Portal

A lightweight web application that allows students and staff of Silicon University to submit, view, update, and delete complaints across various campus categories. Built with Java Servlets, JDBC, and a plain HTML/CSS/JS frontend.

---

## What's New in Milestone 2

Milestone 2 expands the portal from a read/write-only system into a fully CRUD-capable application:

- **Delete complaints** — any complaint card on the dashboard can now be permanently removed via a dedicated Delete button.
- **Update complaint status** — status can be changed inline on the dashboard (Pending → In Progress → Resolved) without reloading the page.
- **Expanded dashboard UI** — `index.html` now includes per-card action controls and a significantly richer stylesheet (`style.css` grew from ~4 KB to ~6.5 KB).
- **Filter bar on the dashboard** — complaints can be filtered by Status and/or Category in real time, with a Reset Filters button and a live count ("Showing X of Y complaints").
- **Two new servlets** — `DeleteComplaintServlet` and `UpdateComplaintServlet` handle the new backend operations.
- **`compile.bat` helper** — a Windows batch script (`WEB-INF/src/compile.bat`) is included for one-command compilation on Windows.

> **Upgrading from Milestone 1?** The database schema is unchanged — no migration is needed. Just recompile, redeploy the updated folder, and restart Tomcat.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Database Setup](#database-setup)
- [DB Credentials Configuration](#db-credentials-configuration)
- [Running the Project](#running-the-project)
- [API Endpoints](#api-endpoints)
- [Dependencies (JARs)](#dependencies-jars)
- [Developers](#developers)

---

## Features

- Submit a complaint with your name, category, title, and description
- View all submitted complaints on a live dashboard
- **Filter complaints** by Status and/or Category directly on the dashboard, with a Reset Filters button and a live "Showing X of Y complaints" count *(new in m2)*
- **Update** the status of any complaint directly from the dashboard *(new in m2)*
- **Delete** any complaint from the dashboard *(new in m2)*
- Complaint categories: Classroom, Hostel, Library, Cafeteria, IT/Network, Other
- Status badges (Pending, In Progress, Resolved) on complaint cards
- Clean responsive UI with Silicon University branding
- REST-style JSON API backed by Java Servlets

---

## Tech Stack

| Layer       | Technology                      |
|-------------|---------------------------------|
| Backend     | Java Servlets (Jakarta EE)      |
| Database    | PostgreSQL                      |
| JDBC Driver | postgresql-42.7.10.jar          |
| JSON        | Google Gson 2.10.1              |
| Frontend    | HTML5, CSS3, Vanilla JavaScript |
| Server      | Apache Tomcat 10+ (Jakarta EE)  |

---

## Prerequisites

Ensure the following are installed before running the project:

| Tool          | Version / Notes                                               |
|---------------|---------------------------------------------------------------|
| Java (JDK)    | Java 17 or higher (uses the `record` keyword)                 |
| Apache Tomcat | Version 10 or higher (required for the `jakarta.*` namespace) |
| PostgreSQL    | Version 13 or higher                                          |
| Browser       | Any modern browser (Chrome, Firefox, Edge)                    |

> **Important:** Do not use Tomcat 9 or lower. This project uses `jakarta.servlet.*` imports, which are only available from Tomcat 10 onwards.

---

## Project Structure

```
Complaint_portal-m2/
│
├── index.html                        # Dashboard — lists all complaints with update/delete controls
├── add.html                          # Form to submit a new complaint
│
├── css/
│   └── style.css                     # Shared stylesheet (expanded in m2)
│
├── images/
│   └── sit.png                       # Silicon University logo
│
├── sql/
│   └── schema.sql                    # Database creation script
│
└── WEB-INF/
    ├── src/                          # Java source files
    │   ├── DBconnection.java         # Database credentials are configured here
    │   ├── AddComplaintServlet.java
    │   ├── GetComplaintsServlet.java
    │   ├── UpdateComplaintServlet.java   # NEW in m2
    │   ├── DeleteComplaintServlet.java   # NEW in m2
    │   └── compile.bat               # Windows one-command compile helper (NEW in m2)
    │
    ├── classes/                      # Compiled .class files (generated during build)
    │   ├── DBconnection.class
    │   ├── Complaint.class
    │   ├── AddComplaintServlet.class
    │   ├── GetComplaintsServlet.class
    │   ├── UpdateComplaintServlet.class  # NEW in m2
    │   └── DeleteComplaintServlet.class  # NEW in m2
    │
    └── lib/                          # Required JAR dependencies (pre-included)
        ├── gson-2.10.1.jar
        └── postgresql-42.7.10.jar
```

---

## Database Setup

### Step 1 — Start PostgreSQL

Ensure your PostgreSQL server is running before proceeding.

### Step 2 — Create the Database and Table

Run the provided `schema.sql` file:

```bash
psql -U postgres -f sql/schema.sql
```

Or manually execute the following in `psql`:

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

> **Upgrading from m1:** The schema is identical — no changes are needed to an existing database.

---

## DB Credentials Configuration

Open:

```
WEB-INF/src/DBconnection.java
```

Update the three lines to match your PostgreSQL setup:

```java
private static String url      = "jdbc:postgresql://localhost:5432/complaints";
private static String username = "postgres";
private static String password = "your_password_here";
```

> **Security Warning:** Credentials are currently hardcoded in `DBconnection.java`. Update these before deployment and avoid committing sensitive credentials to version control.

---

## Running the Project

### Step 1 — Clone the Repository

```bash
git clone https://github.com/your-username/Complaint_portal.git
cd Complaint_portal-m2
```

### Step 2 — Compile the Java Source Files

**Linux / macOS:**

```bash
javac -cp "WEB-INF/lib/*:/path/to/tomcat/lib/servlet-api.jar" \
      -d WEB-INF/classes \
      WEB-INF/src/*.java
```

**Windows (manual):**

```bat
javac -cp "WEB-INF/lib/*;C:\tomcat\lib\servlet-api.jar" ^
      -d WEB-INF/classes ^
      WEB-INF/src/*.java
```

**Windows (using the included helper):**

The file `WEB-INF/src/compile.bat` pre-fills the Tomcat path for a standard Windows installation. Open it, verify the paths, then run:

```bat
cd WEB-INF\src
compile.bat
```

> Replace `/path/to/tomcat/` (or `C:\tomcat`) with your actual Tomcat installation directory.

### Step 3 — Deploy to Tomcat

Copy the entire project folder into Tomcat's `webapps/` directory:

```bash
# Linux / macOS
cp -r Complaint_portal-m2 /path/to/tomcat/webapps/complaintportalm1

# Windows
xcopy /E /I Complaint_portal-m2 C:\tomcat\webapps\complaintportalm1
```

The folder **must** be named `complaintportalm1` — the frontend JavaScript hardcodes this context path for all API calls:

```
http://localhost:8080/complaintportalm1/complaints
http://localhost:8080/complaintportalm1/addComplaint
http://localhost:8080/complaintportalm1/UpdateComplaintServlet
http://localhost:8080/complaintportalm1/DeleteComplaintServlet
```

### Step 4 — Start Tomcat

```bash
# Linux / macOS
/path/to/tomcat/bin/startup.sh

# Windows
C:\tomcat\bin\startup.bat
```

### Step 5 — Open in Browser

```
http://localhost:8080/complaintportalm1/index.html
```

---

## API Endpoints

| Method | Endpoint                  | Description                              |
|--------|---------------------------|------------------------------------------|
| GET    | `/complaints`             | Returns all complaints as JSON           |
| POST   | `/addComplaint`           | Submits a new complaint                  |
| POST   | `/UpdateComplaintServlet` | Updates the status of an existing complaint *(new in m2)* |
| POST   | `/DeleteComplaintServlet` | Permanently deletes a complaint by ID *(new in m2)*       |

---

### POST `/addComplaint` — Parameters

| Parameter     | Type   | Max Length | Required |
|---------------|--------|------------|----------|
| `name`        | String | 100        | Yes      |
| `category`    | String | 50         | Yes      |
| `title`       | String | 120        | Yes      |
| `description` | String | 1000       | Yes      |

---

### POST `/UpdateComplaintServlet` — Parameters *(new in m2)*

| Parameter | Type   | Allowed Values                    | Required |
|-----------|--------|-----------------------------------|----------|
| `id`      | int    | ID of an existing complaint       | Yes      |
| `status`  | String | `Pending`, `In Progress`, `Resolved` | Yes   |

Returns `success` or `failed` as plain text.

---

### POST `/DeleteComplaintServlet` — Parameters *(new in m2)*

| Parameter | Type | Description                  | Required |
|-----------|------|------------------------------|----------|
| `id`      | int  | ID of the complaint to delete | Yes     |

Returns `success` or `failed` as plain text.

---

## Dependencies (JARs)

Both JARs are included in `WEB-INF/lib/` — no manual download is required.

| JAR File                 | Purpose                       |
|--------------------------|-------------------------------|
| `gson-2.10.1.jar`        | Java object / JSON conversion |
| `postgresql-42.7.10.jar` | PostgreSQL JDBC driver        |

> `servlet-api.jar` (or `jakarta.servlet-api`) is required at **compile time only**. It is provided by Tomcat at runtime and must **not** be placed in `WEB-INF/lib/`.

---

## Developers

- [sahusridhar23](https://github.com/sahusridhar23)
- [AmlanSP](https://github.com/AmlanSP)
- [Ayush-2604](https://github.com/Ayush-2604)
