# Microservicio de Torneos
Este microservicio está diseñado para gestionar los torneos y los equipos en el por ejemplo si
un equipo se inscribe en X torneo mostrara de manera ordenada los equipos que hay en el guardando su ID.
Tambien tiene sus estados que pueden ser planificacion, inscripción, en curso, etc. Este microservicio se comunica con los otros a traves del client(Feign) para asi sacar los datos que necesite.

## Dependencias que se utilizaron
* Spring Web
* Validation
* Spring Data JPA
* MYSQL DRIVER
* OpenFeign
* Lombok
