**Movies Project**

The Movies Project is a Java Spring Boot application that provides CRUD (Create, Read, Update, Delete) functionality for managing movie data. It includes RESTful API endpoints for creating, retrieving, updating, and deleting movie records. This README provides an overview of the project along with instructions for installation, usage, contributing, and licensing.

---

**Table of Contents**

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
---

**Introduction**

The Movies Project is aimed at providing a simple yet effective solution for managing movie data. It allows users to perform CRUD operations on movie records via RESTful API endpoints.

---

**Features**

- Create new movie records
- Retrieve movie records by ID or all movies
- Update existing movie records
- Delete movie records
- Error handling for invalid requests

---

**Installation**

To install and set up the Movies Project, follow these steps:

```
# 1. Clone the repository:
git clone https://github.com/your-username/movies-project.git

# 2. Navigate to the project directory:
cd movies-project

# 3. Build the project:
mvn clean install

# 4. Run the project:
mvn spring-boot:run
```

**Usage**

To use the Movies Project, you can interact with its RESTful API endpoints. Here are some examples:

```
- Create a new movie:
  POST /movies
  { "title": "Pengabdi Setan 2 Comunion", "description": "A horror movie...", "rating": 7.0, "image": "", "created_at": "2022-08-01 10:56:31", "updated_at": "2022-08-13 09:30:23" }

- Retrieve all movies:
  GET /movies

- Retrieve a movie by ID:
  GET /movies/{id}

- Update a movie:
  PUT /movies/{id}
  { "title": "New Title", ... }

- Delete a movie:
  DELETE /movies/{id}
```

