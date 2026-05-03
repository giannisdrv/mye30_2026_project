# Analytics Platform for MYE030 at CSE UOI

A full-stack web application designed to process, store, and analyze academic publication data (Conferences, Journals, Authors, and Articles). This project provides an interactive dashboard with various metrics, line charts, and scatter plots to visualize the evolution and correlation of academic venues over time.

## Features

* **Data Preprocessing Pipeline:** Automated Python scripts to clean, filter, and format raw CSV/TSV academic data into structured formats ready for database ingestion.
* **Interactive Dashboards:** Built with **Chart.js** to visualize data dynamically.
* **Year Profile Analysis (Linecharts):** Track the evolution of published articles or active venues across specific years.
* **Scatter Plots Analysis:** Discover correlations between metrics (e.g., Total Articles vs. Average Authors per Article).
* **Detailed Tabular Data:** View specific publications, filtered by year and venue.

## Tech Specifications

**Backend:**
* Java 17+
* Spring Boot (Web, Thymeleaf)
* Spring JDBC Template

**Frontend:**
* HTML5, CSS3
* Bootstrap 5.3.0
* Thymeleaf (Templating Engine)
* Chart.js (Data Visualization)

**Database & Data Processing:**
* MySQL (InnoDB)
* Python 3.x (Data preprocessing: `csv`, `re`, `difflib`, `os`)

## Project Structure

```text
├── processing/             # Python scripts for data cleaning
│   └── preprocess.py       # Main data preparation script
├── initial_data/           # Raw input datasets (CSV)
├── processed_data/         # Cleaned datasets ready for DB loading
├── src/                    # Spring Boot Application source code (Java/HTML)
│   ├── main/java/...       # Controllers, Services, DAOs
│   └── main/resources/...  # Thymeleaf templates (linecharts.html, etc.)
├── tables.sql              # Database schema creation and LOAD DATA script
└── README.md
```

## How to Run

### Prerequisites
Make sure you have the following installed on your system:
* Java Development Kit (JDK) 17 or higher
* Maven
* Python 3.x
* MySQL Server (with `local_infile` enabled)

### Step 1: Data Preprocessing
Before starting the application, the raw data needs to be processed. 
1. Open a terminal and navigate to the `processing` folder.
2. Run the Python script:
   ```bash
   python preprocess.py
   ```

### Step 2: Database Setup
1. Ensure your MySQL server has `local_infile` enabled to allow bulk data loading.
2. Open your MySQL client (e.g., terminal or MySQL Workbench).
3. Execute the `tables.sql` script to create the database schema and load the processed data:
   ```sql
   SOURCE path/to/your/project/tables.sql;
   ```

### Step 3: Running the Application
1. Update the database credentials in `src/main/resources/application.properties` (set your MySQL username and password).
2. Run the Spring Boot application using Maven:
   ```bash
   mvn spring-boot:run
   ```
3. Open your web browser and navigate to: `http://localhost:8080`

## Author
**Ioannis Drivas**
