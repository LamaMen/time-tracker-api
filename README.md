# TimeTracker Api

Time Tracker is a service for tracking the time your team spent on different projects.

TimeTracker supports the Swagger specification. It is located:

```html 
/swagger-ui/index.html
```

## Env variables

* Server
    * PORT - port on which the server will be installed *(optional, 8080 default)*
* Security
    * JWT_SECRET - secret for encryption **(required)**
    * JWT_EXPIRATION - time until token expires. In days. **(required)**
* Database
    * JDBC_DATABASE_URL - url for database **(required)**
    * JDBC_DATABASE_USERNAME - db username **(required)**
    * JDBC_DATABASE_PASSWORD - db password **(required)**
* Scheduling
    * TIME_TO_CLOSE - time, when all open sessions will be closed. Cron format. *(optional, every day at 17 by default)*
    * TIME_ZONE - time zone of scheduling *(optional, Europe/Moscow by default)*

