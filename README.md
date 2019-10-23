## Car API

API REST para cadastro de veículos e seus consumos de combustível para prever e otimizar gastos.

### Stack

- Java 11
- Spring Boot 2.1.7.RELEASE
- PostgreSQL 10

### Frameworks

- SpringFox Swagger
- Mapstruct
- Lombok
- Flyway

### Configuração de banco de dados

- Após instalação do PostgreSQL, é necessário criar um banco de dados com nome `car-api`:

```console
createdb -U postgres "car-api"
```

### Como subir aplicação

```sh
mvn clean install
java -jar car-api-core/target/car-api-core-0.0.1-SNAPSHOT.jar
```

### API

```console
http://localhost:8080/swagger-ui.html
```

### cURL para teste

- Cadastro de dados de carro:

```
curl -X POST "http://localhost:8080/v1/cars" -H "Content-Type: application/json" -d "{ \"averageCityConsumption\": 10, \"averageHighwayConsumption\": 14, \"brand\": \"Ford\", \"manufacturingDate\": \"16/01/1996\", \"model\": \"III\", \"name\": \"Corcel\"}"
```

- Lista de consumo de combustível ranqueada:

```
curl -X POST "http://localhost:8080/v1/cars/consumption/details" -H "Content-Type: application/json" -d "{ \"cityRouteKilometers\": 100, \"fuelPrice\": 3.97, \"highwayRouteKilometers\": 200}"
```
