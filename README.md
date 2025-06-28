# ☄️ Asteroid Alerting Service

## 📖 Description

The **Asteroid Alerting Service** is a microservices-based application that monitors near-earth asteroids and alerts users to potentially hazardous objects.  
It fetches data from NASA's **Near Earth Object (NEO)** API, filters for asteroids that could pose a threat, and sends email notifications to registered users.

---

## ✨ Features

- **Real-time Asteroid Tracking** — Ingests up-to-date information from NASA's NEO API.
- **Threat Assessment** — Filters asteroids based on size, velocity, and proximity to Earth.
- **Scalable Messaging** — Uses **Apache Kafka** as a message queue to decouple services and ensure reliable communication.
- **Email Notifications** — Sends alerts to registered users via email.
- **Persistent Notifications** — Stores a record of all notifications sent.

---

## 🏗️ System Architecture

The service is composed of **two primary microservices**:

### 1️⃣ Asteroid Alert Module
- Periodically polls the NASA NEO API for new asteroid data.
- Parses the data and identifies potentially hazardous asteroids.
- Publishes a message to a **Kafka topic** for each hazardous asteroid.

### 2️⃣ Notification Service
- Consumes messages from the Kafka topic.
- Stores notification details in a **MySQL** database.
- Looks up subscribed users.
- Sends a formatted **email alert** to each recipient.

---

## ⚙️ Technologies Used

| Layer         | Technology                                    |
|---------------|-----------------------------------------------|
| **Backend**   | Java, Spring Boot                             |
| **Messaging** | Apache Kafka                                  |
| **Database**  | MySQL                                         |
| **API**       | NASA Near Earth Object (NEO) API              |
| **Email**     | Spring Boot Starter Mail (SMTP)               |
| **Other**     | Spring Data JPA, Lombok, Spring Kafka         |

---

## 🚀 Getting Started

### ✅ Prerequisites

- **Java 17** or higher (tested with OpenJDK 23)
- **Apache Kafka & Zookeeper** running locally or remotely
- **MySQL** running and configured
- **NASA API Key** — [Get one here](https://api.nasa.gov/)
- **SMTP server credentials** — e.g., Gmail or Mailtrap for testing

---

### 📥 Installation

1️⃣ **Clone the repository:**

```bash
git clone https://github.com/DipankarNath00/Asteroid-alerting-service.git
cd Asteroid-alerting-service
