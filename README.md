# 📦 Système de Gestion de Colis et Transporteurs

## 📝 Description du projet

Ce projet consiste à développer une **API REST sécurisée** pour une entreprise de logistique souhaitant moderniser son système de gestion de colis et de transporteurs.

L'application permet :

* La gestion de **colis de types différents** (STANDARD, FRAGILE, FRIGO)
* La gestion des **utilisateurs** avec des rôles distincts (**ADMIN** et **TRANSPORTEUR**)
* Une **authentification stateless via JWT**
* L'exploitation du **schéma flexible de MongoDB**
* Une architecture **Spring Boot** respectant les bonnes pratiques (Controller, Service, Repository, DTO, etc.)

---

## 🛠️ Stack technique

* **Java 17**
* **Spring Boot**
* **Spring Data MongoDB**
* **Spring Security + JWT (stateless)**
* **MongoDB**
* **Docker & Docker Compose**
* **JUnit & Mockito**
* **Swagger / OpenAPI**
* **Lombok**

---

## 🗄️ Modélisation MongoDB

### Collection `users`

Une seule collection pour tous les utilisateurs.

```json
{
  "login": "string",
  "password": "string",
  "role": "ADMIN | TRANSPORTEUR",
  "active": true,
  "statut": "DISPONIBLE | EN_LIVRAISON", // TRANSPORTEUR uniquement
  "specialite": "STANDARD | FRAGILE | FRIGO" // TRANSPORTEUR uniquement
}
```

### Collection `colis`

Une seule collection avec schéma flexible.

#### Champs communs

```json
{
  "type": "STANDARD | FRAGILE | FRIGO",
  "poids": 10.5,
  "adresseDestination": "string",
  "statut": "EN_ATTENTE | EN_TRANSIT | LIVRE | ANNULE",
  "transporteurId": "ObjectId"
}
```

#### Champs spécifiques

* **FRAGILE** : `instructionsManutention`
* **FRIGO** : `temperatureMin`, `temperatureMax`

---

## 🔐 Sécurité & Authentification

* Authentification **JWT stateless**
* Rôles : `ADMIN`, `TRANSPORTEUR`
* Un utilisateur désactivé (`active=false`) ne peut pas se connecter

### Contenu du JWT

* `issuer`
* `subject` (login utilisateur)
* `roles`
* `expiration`
* Signature sécurisée

### Endpoint d'authentification

```http
POST /api/auth/login
```

---

## 📦 Gestion des Colis

### Règles métier importantes

* Un colis de type **X** ne peut être assigné qu'à un transporteur de spécialité **X**
* ADMIN : accès à tous les colis
* TRANSPORTEUR : accès uniquement à ses colis

### Endpoints TRANSPORTEUR

```http
GET    /api/transporteur/colis
PUT    /api/transporteur/colis/{id}/statut
```

### Endpoints ADMIN

```http
POST   /api/admin/colis
PUT    /api/admin/colis/{id}
DELETE /api/admin/colis/{id}
PUT    /api/admin/colis/{id}/assign/{transporteurId}
GET    /api/admin/colis
```

Fonctionnalités :

* Pagination
* Filtres par type et statut
* Recherche par adresse de destination

---

## 👤 Gestion des Utilisateurs & Transporteurs (ADMIN)

```http
GET    /api/admin/users
GET    /api/admin/transporteurs
POST   /api/admin/transporteurs
PUT    /api/admin/transporteurs/{id}
DELETE /api/admin/transporteurs/{id}
```

Fonctionnalités :

* Pagination
* Filtrage par spécialité
* Activation / désactivation de comptes
* Rôle non modifiable

---

## 🧱 Architecture applicative

```
colis/
├── src/
│   ├── main/
│   │   ├── java/com/colis/
│   │   │   ├── config/          # Configuration (OpenAPI)
│   │   │   ├── controller/      # Contrôleurs REST
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Exceptions personnalisées
│   │   │   ├── mapper/          # MapStruct mappers
│   │   │   ├── model/           # Entités et enums
│   │   │   ├── repository/      # Repositories MongoDB
│   │   │   ├── security/        # Configuration sécurité et JWT
│   │   │   └── service/         # Logique métier
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # Tests unitaires
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

## 🧪 Tests

* Tests unitaires avec **JUnit 5** et **Mockito**
* Couverture des services principaux
* Tests des règles métier

---

## 📘 Documentation API

Swagger est disponible après lancement de l'application :
http://localhost:8080/swagger-ui.html


---

## 🐳 Dockerisation

### Lancer le projet avec Docker Compose

```bash
docker-compose up --build
```

Services :

* Application Spring Boot
* MongoDB


---



## 📊 UML

* Diagramme de cas d'utilisation
<img width="1124" height="850" alt="image" src="https://github.com/user-attachments/assets/c7d2efe0-19ae-4145-b732-fbef548d80b6" />

* Diagramme de classes
  <img width="1063" height="766" alt="image" src="https://github.com/user-attachments/assets/c643bdc9-7bf3-404a-a396-73aa4cf53bdf" />



---
