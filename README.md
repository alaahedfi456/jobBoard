# Atelier Eureka

Projet de microservices Spring Boot avec serveur Eureka, API Gateway et services Node.js/Python.

## Team Members

This project is developed by the following team members:

- **Douaa Ben Rejab**
- **Maryem Bdira Filali**
- **Manel Hosni**
- **Mahdi Ben Fattoum**

## Atelier : santé et routage dynamique

Les microservices Candidat, Candidature et Job exposent les endpoints Actuator de santé et d'information. Meeting et Notification exposent `/health` et `/info`. La gateway utilise Eureka pour trouver Candidat et Candidature. Le load balancing de Candidat est aléatoire ; Candidature utilise Round Robin.

### Démarrer et vérifier

Démarrer MySQL si le profil MySQL est utilisé, puis Eureka sur le port `8761`, les microservices et enfin la gateway sur le port `8080`. Pour le développement local, Job peut être démarré avec le profil H2 `dev`.

Enregistrer une seconde instance de Candidature sur le port `8086` et une seconde de Candidat sur le port `8087` :

```powershell
mvn -f codeSource_correctionProsit/codeSource_correctionProsit/backend/backend/microservices/canditature/pom.xml "-Dspring-boot.run.arguments=--server.port=8086" spring-boot:run
mvn -f codeSource_correctionProsit/codeSource_correctionProsit/backend/backend/microservices/candidat/pom.xml "-Dspring-boot.run.arguments=--server.port=8087" spring-boot:run
```

Vérifier les instances sur [Eureka](http://localhost:8761), puis lancer ces appels PowerShell pour voir les ports choisis :

```powershell
1..12 | ForEach-Object { Invoke-RestMethod http://localhost:8080/candidates/instance }
1..12 | ForEach-Object { Invoke-RestMethod http://localhost:8080/candidature/instance }
```

La sélection Random de Candidat est aussi journalisée dans la console de la gateway. Les instructions détaillées sont dans [le README de l’API Gateway](codeSource_correctionProsit/codeSource_correctionProsit/backend/backend/apiGateway/README.md).

---

*Collaborative project developed as part of Atelier Eureka.*
