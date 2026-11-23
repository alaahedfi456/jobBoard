# job microservice

Spring Boot 3.5 / Java 17 service exposing a REST API for **Job** and **Category**
(one category -> many jobs: `job.category_id -> category.id`), with MySQL and Swagger.

## 1. Create the database
MySQL must be running on `localhost:3306` (user `root`, empty password by default).

```bash
mysql -u root -p < database/job_db.sql
```
or import `database/job_db.sql` in phpMyAdmin / MySQL Workbench. It creates the `job_db`
database, the `category` and `job` tables, and some sample data.

No local MySQL? `docker compose up -d` starts MySQL 8.4 on port 3306 and runs the script.

Different credentials? Edit `spring.datasource.username/password` in `application.properties`.

## 2. Run
```bash
mvn spring-boot:run
```
- Swagger UI: http://localhost:8082/swagger-ui.html
- OpenAPI JSON: http://localhost:8082/v3/api-docs

`spring.jpa.hibernate.ddl-auto=none`: the SQL script owns the schema. Switch to `update` to let Hibernate manage it.

## Endpoints

### Actuator (atelier santé et observabilité)

- `GET http://localhost:8082/actuator/health` : santé globale, composants Spring et contrôle SQL personnalisé `jobDatabase`.
- `GET http://localhost:8082/actuator/info` : nom et version du service.
- `GET http://localhost:8082/actuator/metrics` et `/actuator/metrics/jvm.memory.used` : métriques disponibles et mémoire utilisée.
- `GET http://localhost:8082/actuator/loggers` : niveaux de logs; `POST /actuator/loggers/com.awd.job` avec `{"configuredLevel":"DEBUG"}` change le niveau sans redémarrage. Remettre `INFO` après la démonstration.

Les mêmes endpoints sont publiés par Candidat sur le port 8081. Couper MySQL après le démarrage de Job permet d'observer `DOWN` sur son contrôle de base et dans Eureka (après propagation du statut). Candidature, déjà équipé, expose aussi health, info, metrics et loggers sur le port 8085.

Ordre conseillé : MySQL, Eureka, puis les microservices Spring. Meeting et Notification exposent `/health` et `/info` et publient ces URL lors de leur enregistrement Eureka.

## Endpoints métier

| Method | Path | Description |
|---|---|---|
| GET | /api/jobs?available=&categoryId= | List jobs (filters optional, newest first) |
| GET | /api/jobs/{id} | Get one job |
| POST | /api/jobs | Create job |
| PUT | /api/jobs/{id} | Update job |
| PATCH | /api/jobs/{id}/availability?available=false | Open / close a job |
| DELETE | /api/jobs/{id} | Delete job |
| GET | /api/categories | List categories |
| GET | /api/categories/{id} | Get one category |
| GET | /api/categories/{id}/jobs | Jobs of a category |
| POST | /api/categories | Create category |
| PUT | /api/categories/{id} | Update category |
| DELETE | /api/categories/{id} | Delete category (409 if it still has jobs) |

Errors: 400 validation, 404 not found, 409 duplicate category name / category still in use (RFC 7807 JSON).

## Example
```json
POST /api/jobs
{
  "name": "Java Spring Boot Developer",
  "description": "Build microservices with Spring Boot",
  "available": true,
  "date": "2026-09-28",
  "categoryId": 1
}
```

## Tests
`mvn test` runs integration tests on in-memory H2 (MySQL mode), so MySQL is not needed for tests.
