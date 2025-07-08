# 🌤️ Weather Dashboard App

A full-stack web application that displays hourly temperature forecasts for selected cities using the 
[Open-Meteo API](https://open-meteo.com/).

## 📑 Table of Contents

- [🔧 Tech Stack](#-tech-stack)  
- [🚀 Features](#-features)  
- [🏁 Getting Started](#-getting-started)  
- [📡 API Endpoints](#-api-endpoints)  
- [🧾 City Data](#-city-data)  
- [📊 UI Flow](#-ui-flow)  
- [📂 Project Structure](#-project-structure)  
- [🐳 Docker](#-docker)  
- [📄 License](#-license)  
- [🙋‍♂️ Author](#️-author)


## 🔧 Tech Stack

- **Frontend:** React, Chart.js
- **Backend:** Java Spring Boot
- **API:** Open-Meteo (free weather API)
- **Deployment:** Docker & Docker Compose
- **Database:** MySQL (via Docker)


## 🚀 Features

- 🔽 Select a city from a dropdown menu
- 📊 View hourly temperature data for the next 24 hours
- 🌡️ Display temperatures in a responsive bar chart
- 🛰️ Fetch real-time weather using coordinates
- 🧹 Clear separation between backend logic and frontend display


## 🏁 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Spicysimonroll/OpenMeteo.git
cd weather-dashboard
```

### 2. Run the Application with Docker

```bash
docker-compose down -v        # Clear previous volumes (optional)
docker-compose up --build -d  # Build and start in detached mode
```

- Backend available at: http://localhost:8081
- Frontend available at: http://localhost:3001


## 📡 API Endpoints

| Method | Endpoint                          | Description                         |
|--------|-----------------------------------|-------------------------------------|
| GET    | /api/cities                       | Returns a list of available cities  |
| GET    | /api/weather?city={cityName}      | Returns hourly weather for the city |


## 🧾 City Data

Cities are stored in a MySQL database with the following schema:

| Field       | Type     | Description                      |
|-------------|----------|----------------------------------|
| `id`        | Integer  | Primary key                      |
| `name`      | String   | City name (e.g. "Paris")         |
| `latitude`  | Double   | Latitude coordinate              |
| `longitude` | Double   | Longitude coordinate             |

### Adding Initial City Data

Use Flyway to seed city data.

Create a new migration file in: 

```bash
backend/src/resources/db/migration
```

**Important**:

Flyway migration files must follow a strict naming convention and sequence. The format is:

```bash
V<version_number>__<description>.sql
```

- `<version_number>` must be a sequential integer (e.g., `V1`, `V2`, `V3`, …).
- The sequence **must not skip numbers** — if your last migration file is `V2__some_change.sql`, the next one should be 
  `V3__insert_cities.sql`.
- The double underscore `__` is mandatory to separate the version and description.

For example, if this is your first migration, name it:

```bash
V1__insert_cities.sql
```

If you already have `V1` and `V2` files, the new file should be: 

```bash
V3__insert_cities.sql
```

Then add your SQL insert statement:

```sql
INSERT INTO cities (name, latitude, longitude) VALUES
('London', 51.5072, -0.1276),
('New York', 40.7128, -74.0060),
('Tokyo', 35.6895, 139.6917);
```

Rebuild and restart containers to apply the new migration.


## 📊 UI Flow

1. 🏙️ User selects a city from the dropdown.
2. 🔁 Frontend sends a GET request to `/api/weather?city=CityName`.
3. 🗺️ Backend retrieves city coordinates from the database.
4. 🌐 Backend fetches forecast data from Open-Meteo.
5. 🧽 Filters the necessary fields (time and temperature).
6. 📈 Frontend renders the data using a bar chart (next 24 hours).

## 📂 Project Structure

```bash
weather-dashboard/
│
├── backend/                    # Spring Boot backend
│   ├── src/main/java/          # Java code (controllers, services, etc.)
│   ├── src/main/resources/     # application.yml, Flyway SQL migrations
│   └── pom.xml                 # Maven project file
│
├── frontend/                   # React frontend
│   ├── src/                    # App.js, components, CSS
│   ├── public/                 # Static assets
│   └── package.json            # Frontend dependencies
│
├── docker-compose.yml          # Multi-container Docker setup
└── README.md                   # Project documentation
```

## 🐳 Docker

### Docker Environments

This project provides two Docker configurations:

- `Production`: Uses optimized builds for frontend and backend (no hot reloading)
- `Development`: Uses hot reload for React frontend with volume mounts and live refresh

### ▶️ Run in Development Mode (with Hot Reload)

To run the app in `development mode`:

```bash
docker-compose down -v
docker-compose -f docker-compose.yml -f docker-compose.dev.yml up --build -d
```

This will:

- Build and start MySQL and Spring Boot backend
- Start the React frontend using `Dockerfile.dev`(with hot reload on port `3002`)
- Mount local `./frontend` folder for real-time changes

> 💡 Visit the frontend at: http://localhost:3002

### 🚀 Run in Production Mode

To run the app in `production mode` (optimized containers):

```bash
docker-compose down -v
docker-compose up --build -d
```

This will:

- Serve the React frontend from Nginx on port `3001`
- Run the Spring Boot backend on port `8081`

> 🔗 Frontend: http://localhost:3001 
> <br>
> 🔗 Backend: http://localhost:8081

### 📁 Docker Overview

| Service  | Port | Purpose                          | Mode         |
|----------|------|----------------------------------|--------------|
| database | 3307 | MySQL 8.0 database               | Both         |
| backend  | 8081 | Spring Boot REST API             | Both         |
| frontend | 3001 | React served via Nginx           | Production   |
| frontend | 3002 | React Dev Server (hot reload)    | Development  |

### 📄 Dockerfile Summary

| Path                      | Description                           |
|---------------------------|---------------------------------------|
| `frontend/Dockerfile`     | Production build served by Nginx      |
| `frontend/Dockerfile.dev` | Development container with hot reload |
| `backend/Dockerfile`      | Spring Boot app build image           |


## 📄 License

This project is open-source and available under the MIT License.


## 🙋‍♂️ Author

**Simon Hu**

GitHub: [Spicysimonroll](https://github.com/Spicysimonroll)
