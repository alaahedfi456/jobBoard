# Guide de Démarrage des Microservices et Corrections Apportées

## 🌐 URLs Importantes
* **Eureka Dashboard (Interface Web) :** [http://localhost:8761/](http://localhost:8761/)
* **Eureka Service URL (Enregistrement) :** `http://localhost:8761/eureka`
* **Candidat API / Swagger :** [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) (H2 Console: [http://localhost:8081/h2](http://localhost:8081/h2) - JDBC URL: `jdbc:h2:mem:candidatdb`)
* **Canditature API :** [http://localhost:8085/mic3/candidature/hello](http://localhost:8085/mic3/candidature/hello)
* **Job API / Swagger :** [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)
* **Meeting API / Swagger :** [http://localhost:8083/swagger-ui](http://localhost:8083/swagger-ui)
* **Notification API / Swagger :** [http://localhost:8084/swagger-ui](http://localhost:8084/swagger-ui)

---

## 🛠️ Corrections et Améliorations Apportées au Projet

1. **Création d'un script de lancement direct pour Eureka Server (`demarrer-eureka.bat`) :**
   * Détecte automatiquement votre JDK 17 (`C:\Users\manel\.jdks\ms-17.0.17`).
   * Lance immédiatement le fichier JAR déjà compilé sans avoir besoin d'installer Maven au préalable.

2. **Création d'un Menu Lanceur interactif (`demarrer-microservices.bat`) :**
   * Permet de lancer chaque microservice dans sa propre fenêtre en un seul clic.

3. **Correction de la base de données de `candidat` :**
   * `spring.datasource.url` était commenté, ce qui pouvait causer des erreurs ou générer une URL H2 dynamique inconnue.
   * Configuré avec `jdbc:h2:mem:candidatdb;DB_CLOSE_DELAY=-1` pour un accès direct et garanti à la console H2 (`http://localhost:8081/h2`).

4. **Ajout d'un mode de développement sans MySQL pour `job` :**
   * Le service `job` dépend par défaut d'un serveur MySQL actif sur le port 3306. Si MySQL n'est pas lancé, le service plante.
   * Ajout d'un profil `dev` avec une base en mémoire H2 (`application-dev.properties`). Vous pouvez maintenant lancer `job` même sans MySQL (`-Dspring-boot.run.profiles=dev`).

5. **Ajout d'un POM agrégateur parent (`pom.xml`) :**
   * Placé dans `codeSource_correctionProsit/backend/backend/microservices/pom.xml`.
   * Permet à **IntelliJ IDEA** et **VS Code** de détecter automatiquement les 4 projets Spring Boot comme modules d'un même projet.

6. **Déploiement du Maven Wrapper (`mvnw.cmd`) :**
   * Copié dans `eureka-server`, `candidat`, `job` et la racine des microservices pour pouvoir exécuter les projets sans avoir Maven préinstallé dans le PATH Windows.

---

## 🚀 Comment Démarrer le Projet

### Méthode 1 : Avec les scripts 1-clic (Le plus simple)
1. **Démarrer Eureka en premier :**
   Double-cliquez sur `demarrer-eureka.bat`.
   Attendez l'affichage de Tomcat sur le port 8761 et ouvrez [http://localhost:8761/](http://localhost:8761/).
2. **Démarrer les autres services :**
   Double-cliquez sur `demarrer-microservices.bat` et choisissez le service à lancer (ex: 2 pour Candidat, 4 pour Job en mode H2, etc.).

### Méthode 2 : Dans IntelliJ IDEA
1. Ouvrez le dossier `codeSource_correctionProsit\codeSource_correctionProsit\backend\backend\microservices` dans IntelliJ IDEA.
2. IntelliJ détectera le nouveau `pom.xml` et chargera les modules.
3. Configurez le Project SDK sur **Java 17** (File > Project Structure > SDK: `ms-17.0.17`).
4. Lancez `EurekaServerApplication` (Run ▶).
5. Lancez ensuite les autres applications (`CandidatApplication`, `JobApplication`, etc.).
