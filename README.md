☄️ Asteroid Alerting Service
Description
The Asteroid Alerting Service is a microservices-based application that monitors near-earth asteroids and alerts users to potentially hazardous objects. It fetches data from NASA's Near Earth Object (NEO) API, filters for asteroids that could be a threat, and sends email notifications to a list of registered users.

Features
Real-time Asteroid Tracking: Ingests up-to-date information from the NASA NEO API.

Threat Assessment: Filters asteroids based on parameters that indicate they may be potentially hazardous.

Scalable Messaging: Uses Apache Kafka as a message queue to decouple services and ensure reliable communication.

Email Notifications: Alerts registered users via email about any potential threats.

Persistent Notifications: Stores a record of all notifications sent.

System Architecture
The service is composed of two primary microservices:

Asteroid Alert Module: This service is responsible for the following:

Periodically polling the NASA NEO API for new data.

Parsing the data and identifying potentially dangerous asteroids based on their size, velocity, and proximity to Earth.

Publishing a message to a Kafka topic for each potentially hazardous asteroid.

Notification Service: This service is responsible for the following:

Consuming messages from the Kafka topic.

Storing the notification details in a database.

Querying a user database to get the list of recipients.

Sending a formatted email alert to each user on the list.

Technologies Used
Backend: [Please fill in the programming language and framework, e.g., Python (Flask/Django), Java (Spring Boot), Node.js (Express), etc.]

Messaging: Apache Kafka

Database: [Please fill in the database you are using, e.g., PostgreSQL, MySQL, MongoDB, etc.]

API: NASA Near Earth Object (NEO) API

Other Libraries/Tools: [Please list any other important libraries, e.g., a Kafka client library, an email library, etc.]

Getting Started
Prerequisites
[List any prerequisites, e.g., Java 11, Python 3.8, Docker, etc.]

An API key for the NASA NEO API. You can get one here.

A running instance of Apache Kafka and Zookeeper.

A configured database.

Installation
Clone the repository:

Bash

git clone https://github.com/DipankarNath00/Asteroid-alerting-service.git
cd Asteroid-alerting-service
Configure the application:

Open the configuration file (e.g., application.properties, config.py, .env).

Add your NASA API key.

Configure the Kafka broker address.

Set up the database connection details.

Configure your email server settings.

Install dependencies:

[Provide the command to install dependencies, e.g., pip install -r requirements.txt, mvn install, etc.]

Usage
Start the services:

[Provide instructions on how to run each of the microservices.]

Registering users:

[Explain how to add users to the database so they can receive alerts.]

Contributing
Contributions are welcome! Please feel free to submit a pull request.
