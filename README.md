test-jdo
========

Sample project to test the problem with primary keys with the valueStrategy "IdGeneratorStrategy.IDENTITY" with PostgreSQL database and Datanucleus 5.x.

The problems is that the SQL query generated adds too many double quotes to the primary key column name at the 'RETURNING "PRIMARY_KEY_COLUMN_NAME"' section.


- Steps:

1. To test you need a PostgreSQL server running at 127.0.0.1:5432, create de user "test" with password "test" and a database "test". You must execute this commands as postgres user:

```
createuser test
psql -c "ALTER USER test WITH PASSWORD 'test';"
createdb -O test test
```

2. With the database created, execute to check the error:

```
mvn clean install
```


