# Blog API – INF222 TAF1

API REST Spring Boot pour la gestion d'articles de blog.

## Prerequis

- Java 21+
- Maven 3.8+
- MySQL 8+

## Configuration de la base de donnees

```sql
CREATE DATABASE article;
CREATE USER 'spring'@'localhost' IDENTIFIED BY '@Spring123';
GRANT ALL PRIVILEGES ON article.* TO 'spring'@'localhost';
FLUSH PRIVILEGES;
```

La table `article` est cree automatiquement au demarrage (JPA/Hibernate).

## Lancement

```bash
mvn spring-boot:run
```

API disponible sur : `http://localhost:8081`  
Swagger UI : `http://localhost:8081/swagger-ui/index.html`

---

## Endpoints

### POST /api/articles -- Creer un article

**Body (JSON) :**
```json
{
  "title": "Introduction a Spring Boot",
  "content": "Spring Boot facilite le developpement...",
  "auteur": "Charles",
  "date": "2026-03-20",
  "categorie": "Technologie",
  "tags": "java,spring,backend"
}
```
**Reponses :** `201 Created` | `400 Bad Request` (champ manquant ou vide)

---

### GET /api/articles -- Lister les articles

```
GET /api/articles
GET /api/articles?categorie=Technologie
GET /api/articles?date=2026-03-20
GET /api/articles?auteur=Charles
GET /api/articles?categorie=Technologie&date=2026-03-20
```
**Reponse :** `200 OK` -- tableau JSON

---

### GET /api/articles/{id} -- Lire un article

```
GET /api/articles/1
```
**Reponses :** `200 OK` | `404 Not Found`

---

### PUT /api/articles/{id} -- Modifier un article

```
PUT /api/articles/1
```
```json
{
  "title": "Nouveau titre",
  "categorie": "Developpement"
}
```
Seuls les champs fournis sont modifies.  
**Reponses :** `200 OK` | `404 Not Found`

---

### DELETE /api/articles/{id} -- Supprimer un article

```
DELETE /api/articles/1
```
**Reponses :** `200 OK` | `404 Not Found`

---

### GET /api/articles/search -- Rechercher par mot-cle

```
GET /api/articles/search?query=spring
```
**Reponse :** `200 OK` -- articles dont le titre ou le contenu contient le mot-cle

---

## Codes HTTP

| Code | Signification                      |
|------|------------------------------------|
| 200  | OK                                 |
| 201  | Article cre                        |
| 400  | Champ obligatoire manquant ou vide |
| 404  | Article non trouve                 |
| 500  | Erreur serveur                     |

---

## Tests avec Postman

Pour toutes les requêtes avec body, ajouter le header : `Content-Type: application/json`

| Action     | Methode  | URL                                                      |
|------------|----------|----------------------------------------------------------|
| Creer      | POST     | `http://localhost:8081/api/articles`                     |
| Lister     | GET      | `http://localhost:8081/api/articles`                     |
| Filtrer    | GET      | `http://localhost:8081/api/articles?auteur=Charles`      |
| Lire       | GET      | `http://localhost:8081/api/articles/1`                   |
| Modifier   | PUT      | `http://localhost:8081/api/articles/1`                   |
| Supprimer  | DELETE   | `http://localhost:8081/api/articles/1`                   |
| Rechercher | GET      | `http://localhost:8081/api/articles/search?query=spring` |
