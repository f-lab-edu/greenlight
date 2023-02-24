### flyway
```shell
flyway -user=grnmaster -password=password -url='jdbc:mysql://localhost:3307/greenlight?useSSL=false' -locations=filesystem:src/main/resources/db/migration/local,src/main/resources/db/migration/seed clean -ignoreMigrationPatterns='*:pending' -baselineOnMigrate='true' -cleanDisabled='false'
flyway -user=grnmaster -password=password -url='jdbc:mysql://localhost:3307/greenlight?useSSL=false' -locations=filesystem:src/main/resources/db/migration/local,src/main/resources/db/migration/seed migrate -ignoreMigrationPatterns='*:pending' -baselineOnMigrate='true' -cleanDisabled='false'
```
