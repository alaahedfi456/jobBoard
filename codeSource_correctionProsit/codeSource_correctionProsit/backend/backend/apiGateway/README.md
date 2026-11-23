# API Gateway: routage Eureka et équilibrage

La gateway écoute sur `http://localhost:8080`. Les routes configurées dans `src/main/resources/application.properties` utilisent les identifiants Eureka :

- `/candidature/**` → `CANDITATURE`
- `/candidates/**` → `CANDIDAT`

Spring Cloud LoadBalancer utilise **Round Robin** par défaut. La configuration `@LoadBalancerClient` remplace cet algorithme par **Random** pour `CANDIDAT` uniquement ; les autres services conservent Round Robin. D’autres options Spring Cloud incluent notamment l’équilibrage pondéré et la préférence de zone.

Pour lancer une seconde instance de Candidature sur le port 8086 :

```powershell
mvn -f ..\microservices\canditature\pom.xml "-Dspring-boot.run.arguments=--server.port=8086" spring-boot:run
```

Pour lancer une seconde instance de Candidat sur le port 8087 et comparer les sélections Random :

```powershell
mvn -f ..\microservices\candidat\pom.xml "-Dspring-boot.run.arguments=--server.port=8087" spring-boot:run
```

Dans Eureka, vérifier que `CANDITATURE` et `CANDIDAT` ont chacun deux instances UP. Répéter ensuite ces appels :

```powershell
1..12 | ForEach-Object { Invoke-RestMethod http://localhost:8080/candidates/instance }
1..12 | ForEach-Object { Invoke-RestMethod http://localhost:8080/candidature/instance }
```

Les réponses indiquent le port de l’instance sélectionnée. La console de la gateway affiche également la sélection Random pour Candidat. Les logs détaillés des routes sont activés dans `application.properties`.
