# teppo-backend
Microservices-based REST interfaced service for DB storage access (Postgresql and S3) 
Unique Access to the mentioned data storages

## Importing gradle project to IntelliJ IDEA
- JDK 1.8.0 update 131 is supported
- Import `pythia_backend` subfolder (having `build.gradle` script) as Gradle project
- Make sure that `Use auto-import` is checked
- Build project after dependencies are loaded
- Run `PythiaBackendApplication`
- Shorten command line as instructed in: https://blog.jetbrains.com/idea/2017/10/intellij-idea-2017-3-eap-configurable-command-line-shortener-and-more/ 

## Environments
TEPPO_ENV environment value is used for selecting which environment is used. Possible values:
- local (only this is currently available for db selection, other values point to default)
- dev
- test
- staging
- prod

## Database setup
SQL scripts for initializing PostgresSQL database for TEPPO (formerly known as Pythia) are located in database folder.

To initialize the database (with no data):
* Create a database named as `pythia`
* Create loggable user `pythiaservice` having password `pythiaservice`
* Run `pythia.sql` for `pythia` db
* Run `create_latest_plans_view.sql` for `pythia` db
