# Medical Appointment Management System

This project implements a CRUD (Create, Read, Update, Delete) system to manage patients, doctors, and appointments in a clinical environment.

## Overview

The goal of this system is to facilitate the management of information related to patients, doctors, and their respective appointments. It allows:

- **Creation** of new records for patients, doctors, and appointments.
- **Reading** of existing information.
- **Updating** data when necessary.
- **Deleting** records that are no longer needed.

## Features

- **Patient Management**: Add, view, update, and remove patient information.
- **Doctor Management**: Control over doctor data.
- **Appointment Scheduling**: System to schedule, modify, or cancel appointments.

## Some interfaces:
**Login Interface**:
  ![Login Screen](login.png)
  
**Dashboard Interface**:
  ![Dashboard Screen](dashboard.png)
  
**Appointments Interface**:
  ![Appointments Screen](consultas.png)

## ER Diagram
![ER Diagram](diagrama-er.png)

## Data Dictionary

### Table: `person`
| Attribute Name    | Data Type    | Size    | Description                       | Constraints       |
|-------------------|--------------|---------|-----------------------------------|-------------------|
| id                | SERIAL       |         | Unique identifier of the person   | Primary Key       |
| name              | VARCHAR      | 100     | Full name of the person           | NOT NULL          |
| phone             | VARCHAR      | 15      | Phone number of the person        |                   |
| birth_date        | DATE         |         | Birth date of the person          |                   |

### Table: `user`
| Attribute Name    | Data Type    | Size    | Description                       | Constraints       |
|-------------------|--------------|---------|-----------------------------------|-------------------|
| id                | SERIAL       |         | Unique identifier of the user     | Primary Key       |
| person_id         | INT          |         | Foreign key to the person table   | NOT NULL, FK to `person(id)` |
| email             | VARCHAR      | 100     | User's email address              | UNIQUE, NOT NULL  |
| password          | VARCHAR      | 50      | User's password (hash recommended)| NOT NULL          |

### Table: `specialty`
| Attribute Name    | Data Type    | Size    | Description                       | Constraints       |
|-------------------|--------------|---------|-----------------------------------|-------------------|
| id                | SERIAL       |         | Unique identifier of the specialty| Primary Key       |
| name              | VARCHAR      | 100     | Name of the medical specialty     | UNIQUE, NOT NULL  |

### Table: `doctor`
| Attribute Name    | Data Type    | Size    | Description                       | Constraints       |
|-------------------|--------------|---------|-----------------------------------|-------------------|
| id                | SERIAL       |         | Unique identifier of the doctor   | Primary Key       |
| person_id         | INT          |         | Foreign key to the person table   | NOT NULL, FK to `person(id)` |
| user_id           | INT          |         | Foreign key to the user table     | NOT NULL, FK to `user(id)` |
| crm               | VARCHAR      | 20      | Doctor's registration (CRM)       | UNIQUE, NOT NULL  |

### Table: `doctor_specialty`
| Attribute Name    | Data Type    | Size    | Description                       | Constraints       |
|-------------------|--------------|---------|-----------------------------------|-------------------|
| doctor_id         | INT          |         | Foreign key to the doctor table   | NOT NULL, FK to `doctor(id)`, Part of PK |
| specialty_id      | INT          |         | Foreign key to the specialty table| NOT NULL, FK to `specialty(id)`, Part of PK |

### Table: `client`
| Attribute Name    | Data Type    | Size    | Description                       | Constraints       |
|-------------------|--------------|---------|-----------------------------------|-------------------|
| id                | SERIAL       |         | Unique identifier of the client   | Primary Key       |
| person_id         | INT          |         | Foreign key to the person table   | NOT NULL, FK to `person(id)` |
| cpf               | VARCHAR      | 14      | Client's CPF (Individual Taxpayer Registry) | UNIQUE, NOT NULL  |

### Table: `appointment`
| Attribute Name    | Data Type    | Size    | Description                       | Constraints       |
|-------------------|--------------|---------|-----------------------------------|-------------------|
| id                | SERIAL       |         | Unique identifier of the appointment | Primary Key   |
| doctor_id         | INT          |         | Foreign key to the doctor table   | NOT NULL, FK to `doctor(id)` |
| client_id         | INT          |         | Foreign key to the client table   | NOT NULL, FK to `client(id)` |
| appointment_date  | TIMESTAMP    |         | Date and time of the appointment  | NOT NULL          |
| notes             | TEXT         |         | Notes or observations of the appointment |               |

## Technologies:
- Java 21
- JavaFX
- PostgreSQL Database
- JDBC
- Eclipse IDE

