# GetFlip - Short URL

## Requirements

- **Shorten URL Endpoint**: Create an endpoint that accepts a long URL and returns a shortened URL.
- **Retrieve Original URL Endpoint**: Create an endpoint that accepts a shortened URL and returns the original long URL.

## Architecture

- The application is built using java with SpringBoot framework.
- It uses Java 21 which brings new features and improvements to the language such as what I'm using which is virtual threads.
  Virtual threads are lightweight threads that are managed by the Java Virtual Machine (JVM) rather than the operating system.
  This allows for more efficient use I/O-bound operations and can improve the performance of applications since we are using database operations.
- To store the URL mappings, the application uses a PostgresSQL database.
- Flyway as a database migration tool to manage the database schema and migrations.
- The application is containerized using Docker and docker-compose to run the application and the database in a containerized environment.
- Zipkin is used to trace the requests and visualize the latency of the requests.

## Code Structure

The application follows a mix of hexagonal and clean architecture using use cases and entities to separate the business logic from the infrastructure.
With this architecture, the business logic is separated from the infrastructure and can be easily tested and replaced.

- Adapter IN package represent incoming adapters, such as Rest API.
- Adapter OUT package represent outgoing adapters, such as Database and ID Generators.
- Domain package contains the domain entities and the use cases that represent the business logic.
- Port in package contains the interfaces that represent the use cases that the application uses.
- Port out package contains the interfaces that represent the repositories that our domain uses.

## Database schema

The application uses PostgreSQL as the database. The database schema can be found in the resources folder. Take a look at the schema [here](/src/main/resources/db/migration) I take
advantage of Flyway to manage the database schema and migrations.

## API Reference

This project exposes a REST API with two endpoints:

- **Shorten URL Endpoint**: Create an endpoint that accepts a long URL and returns a shortened URL.
- **Retrieve Original URL Endpoint**: Create an endpoint that accepts a shortened URL and returns the original long URL.

- The service exposes a swagger ui where you can find the API documentation [here](http://localhost:8080/swagger-ui/index.html)

## Validations

The application uses bean validation to validate the input parameters of the API endpoints.
Not only API endpoint but the use cases also have validations to ensure that the input parameters are valid. That will ensure that no matter what adapter calls the use case the
validations will be the same.

## Error Handling

The application uses a custom exception handler [here](/src/main/java/com/example/adapter/in/web/ExceptionTranslator.java) to handle exceptions and return appropriate error
responses to the client.
It uses the @ControllerAdvice annotation to handle exceptions globally across all controllers and follows the RFC 7807 standard for error responses.

## Logging and distributed tracing

The application uses zipkin to trace the requests and visualize the latency of the requests.
You can visualize the traces by accessing the zipkin dashboard [here](http://localhost:9411/zipkin/)
With the help of @Observed annotation, I can make a class or method observable and traceable by zipkin.

The application uses logback as the logging framework with SLF4J to log messages to the console.

## Short URL Strategy

The application implements the strategy pattern to generate the short URL.
This allows us to easily change the strategy to generate the short URL without changing the application's code.
The application uses the Base62 strategy to generate the short URL.

### Base62 Strategy with Unique ID

#### Why Base62?

Base62 is a good choice for generating short URLs for several reasons:

- Compactness: Base62 encoding uses 62 characters (0-9, a-z, A-Z), which allows for a more compact representation of data compared to other encoding schemes like Base10 or Base16.
  This results in shorter URLs.
- URL Safety: The characters used in Base62 are URL-safe, meaning they do not include special characters that need to be encoded in URLs. This ensures that the short URLs are clean
  and do not require additional encoding.
- Readability: Base62 encoded strings are more readable and user-friendly compared to other encoding schemes that might include special characters or be case-insensitive.
- Efficiency: Base62 encoding is efficient in terms of both storage and computation. It provides a good balance between the length of the encoded string and the complexity of the
  encoding/decoding process.

Alternatives:

- Base36: Base36 uses 36 characters (0-9, a-z) and is a good alternative to Base62 for alphanumeric encoding.
- However, it is less compact than Base62 and may result in longer URLs.

| **Base Encoding** | **Character Set**                    | **Length Efficiency** | **Readability** | **URL Safety** | **Use Case**                                                           |
|-------------------|--------------------------------------|-----------------------|-----------------|----------------|------------------------------------------------------------------------|
| Base2             | 0, 1                                 | Low                   | Low             | High           | Binary data representation                                             |
| Base8             | 0-7                                  | Medium                | Medium          | High           | Compact binary data representation                                     |
| Base10            | 0-9                                  | Medium                | High            | High           | Decimal number representation                                          |
| Base16            | 0-9, a-f                             | High                  | Medium          | High           | Hexadecimal representation, often used in computing                    |
| Base32            | 0-9, a-v                             | High                  | Medium          | High           | Compact representation with a larger character set                     |
| Base36            | 0-9, a-z                             | High                  | High            | High           | Alphanumeric representation, often used for short URLs                 |
| Base58            | 0-9, A-Z, a-z (excluding 0, O, I, l) | High                  | High            | High           | Similar to Base62 but avoids confusing characters                      |
| Base62            | 0-9, A-Z, a-z                        | High                  | High            | High           | Short URLs, compact and readable representation                        |
| Base64            | A-Z, a-z, 0-9, +, /                  | High                  | Medium          | Medium         | Binary-to-text encoding, often used in data serialization and encoding |

#### Why Unique ID vs Base62 directly on the long url?

The approach of using a unique ID and then encoding it with Base62 is better than encoding the long URL directly with Base62 for several reasons:

- Uniqueness: By using a unique ID, I ensure that each short URL is unique. Encoding the long URL directly could result in collisions if two different long URLs produce the same
  Base62 encoded string.
- Fixed Length: The unique ID approach typically results in short URLs of a consistent length, which is more predictable and manageable. Encoding long URLs directly can result in
  variable-length short URLs, which can be longer and less user-friendly.
- Performance: Encoding the long URL directly can be computationally expensive, especially for long URLs. Encoding a unique ID is much faster and more efficient. Without the unique
  ID I would have to query the database to check if the long URL already exists to avoid creating duplicate entries.
- Scalability: The unique ID approach scales better as it decouples the short URL generation from the content of the long URL. This makes it easier to manage and store mappings in
  a database.

- The approach used to generate the unique id is to use UUID. An easy way to obtain unique Ids. UUID has a very low probability of getting collisions. So in order to simplify the
  challenge I followed this approached.
  Nevertheless the code is prepared to receive a different approach to generate the unique id by implementing the interface LoadUniqueIDPort.

#### Why not use a hash function?

- Hashing algorithms can be more computationally intensive and may require additional processing to ensure uniqueness and handle collisions.
- Hash values are usually hexadecimal and can be longer and harder to read.
- Hashing algorithms like MD5 or SHA-1 produce fixed-length outputs that are often longer than Base62 encoded strings.

## Building the application

To build the application, run the following command

```bash
./gradlew build
```

## Installation

This project takes the leverage of spring docker support to run the application in a containerized environment.
So no need to install any dependencies other than docker and docker-compose.
The docker-compose file is already present in the project root directory called compose.yml
This will spin up the database in a container.

## Start the application

```bash
./gradlew bootRun
```

## Usage/Examples

### Create Short URL Endpoint

To create a short URL, send a POST request to the following endpoint with the long URL as a query parameter:

```bash
curl -X POST "http://localhost:8080/api/v1/url/shorten?long_url=https://www.google.com"
```

### Get Long URL Endpoint

To retrieve the original long URL from a short URL, send a GET request to the following endpoint with the short URL as a path parameter:

```bash
curl -X GET "http://localhost:8080/api/v1/url/Uic5tq2K"
```

## Running Tests

To run unit and integration tests, run the following command. It uses testcontainers to run the integration tests.

```bash
./gradlew test
```

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Data JDBC
- PostgresSQL
- Docker
- Testcontainers
- Swagger
- Flyway
- Zipkin

## Future Improvements

- Contract First: I've used spring doc to generate the API documentation and open api specification.
  I would like to generate the interfaces and the pojos from the open api specification to have a contract first approach.
  Nevertheless, since I've used the approach of single responsibility principle, the library to generate the interface isn't prepare to have separate interfaces and I will end up
  with one interface with all the endpoints.

- Cache: I would like to implement a cache mechanism to store the short URL mappings in memory to improve performance and reduce database queries.
- Metrics: I would like to implement metrics to monitor the number of time the short URL is accessed and the number of times the long URL is shortened.
- Rate Limiting: I would like to implement rate limiting to prevent abuse of the short URL service.
- Security: I would like to implement security features such as authentication and authorization to protect the short URL service from unauthorized access.
- Generation of IDs in a distributed environment: I would like to implement a more robust strategy for generating unique IDs in a distributed environment to avoid collisions and
  ensure consistency. Even UUID has a very low probability of getting collisions, in a distributed environment it can happen.

## Authors

- [@pedrorlmarques](https://www.github.com/pedrorlmarques)
