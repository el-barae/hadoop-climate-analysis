# Big Data Climate Analysis with Hadoop & MapReduce

[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white)](https://www.java.com/)
[![Hadoop](https://img.shields.io/badge/Apache%20Hadoop-66CC00?style=flat-square&logo=apachehadoop&logoColor=white)](https://hadoop.apache.org/)
[![MapReduce](https://img.shields.io/badge/MapReduce-FF9900?style=flat-square)](https://hadoop.apache.org/docs/r1.2.1/mapred_tutorial.html)

A distributed Big Data pipeline engineered to analyze 20 years of climate and precipitation trends (1994–2015) using the Hadoop ecosystem. This project implements multiple MapReduce jobs for data aggregation, anomaly detection, and geographic analysis on large meteorological datasets.

## 📊 Overview

This system processes large-scale climate observation data to extract meaningful meteorological insights. By leveraging the MapReduce programming model, the application efficiently distributes computations across an HDFS (Hadoop Distributed File System) cluster.

### Key Objectives
*   Process massive datasets of historical weather records (precipitation, temperature).
*   Implement custom MapReduce jobs for specific analytical queries.
*   Detect climate anomalies and extreme weather patterns over a 20-year span.
*   Analyze geographic variations by aggregating data across different latitude/longitude zones.

## 🚀 Features & Analytical Jobs

The project includes several specific MapReduce implementations, each answering a distinct analytical question:

1.  **Record Count (`RecordCountDriver`)**: Fundamental data parsing to count total valid observations in the dataset.
2.  **Yearly Observations (`YearCountDriver`)**: Aggregates the frequency of weather recordings per year.
3.  **Annual Total Precipitation (`YearPrecipDriver`)**: Calculates the total sum of precipitation for each year, highlighting historical trends.
4.  **Monthly Average Precipitation (`MonthPrecipDriver`)**: Computes the average rainfall per month across the 20-year span to identify seasonal patterns.
5.  **Extreme Temperatures (`TempExtremeDriver`)**: Scans the dataset to identify the absolute maximum and minimum temperatures recorded globally.
6.  **Geographic Zone Analysis (`LatitudeZoneDriver`)**: Splits the globe into distinct latitude zones and calculates average precipitation for each, comparing regional climates.
7.  **Anomaly Detection (`AnomalyDetectionDriver`)**: Identifies years with statistically significant precipitation anomalies (droughts or extreme rainfall) compared to the historical baseline.

## 🏗️ Architecture & Technologies

*   **Language:** Java 8+
*   **Big Data Framework:** Apache Hadoop
*   **Storage:** HDFS (Hadoop Distributed File System)
*   **Processing Model:** MapReduce
*   **Build Tool:** Maven (`pom.xml`)

## ⚙️ Setup & Execution

### Prerequisites
*   Java Development Kit (JDK 8 or higher)
*   Apache Hadoop cluster (pseudo-distributed or fully distributed mode) running locally or on a server.
*   Maven installed.

### 1. Build the Project
Compile the Java source code and build the executable JAR file using Maven:
```bash
mvn clean package
```
This will generate the JAR file in the `target/` directory (e.g., `climate-mapreduce-1.0-SNAPSHOT.jar`).

### 2. Prepare Data on HDFS
Ensure your Hadoop daemons (`NameNode`, `DataNode`, `ResourceManager`, `NodeManager`) are running.
Upload the raw climate dataset (e.g., `.txt` or `.csv` files from the `data/` folder) to HDFS:
```bash
hdfs dfs -mkdir -p /input/climate_data
hdfs dfs -put data/*.txt /input/climate_data/
```

### 3. Run MapReduce Jobs
Submit a job to the Hadoop cluster. Replace `<JobDriverClass>` with the specific analysis you want to run (e.g., `YearPrecipDriver`).

```bash
hadoop jar target/climate-mapreduce-1.0-SNAPSHOT.jar <package_name>.<JobDriverClass> /input/climate_data /output/results
```
*Note: Make sure the output directory (`/output/results`) does not exist before running the command, as Hadoop requires a fresh directory for output.*

### 4. View Results
Retrieve and view the aggregated results from HDFS:
```bash
hdfs dfs -cat /output/results/part-r-00000
```

## 📝 Project Report
For a detailed explanation of the methodology, HDFS architecture setup, and the interpretation of the output data, please refer to the comprehensive report included in this repository:
[`MapReduce lab - Report - El Barae AKACHAR.pdf`](MapReduce%20lab%20-%20Report%20-%20El%20Barae%20AKACHAR.pdf)

## 👨‍💻 Author
**El Barae AKACHAR**
*   Master in Computer Engineering (M2I) - Software Engineering and Information Systems.
*   Faculty of Sciences, Tetouan
