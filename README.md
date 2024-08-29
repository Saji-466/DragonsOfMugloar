# Getting Started

## System requirements 
* Java 21
* Internet connection

## Compile source code and build artifacts
``` 
./gradlew build
```

## Launch built application
```
java -jar .\build\libs\mugloar-0.0.1-SNAPSHOT.jar
```
Launch with multiple games
```
java -jar .\build\libs\mugloar-0.0.1-SNAPSHOT.jar 10
```
Launch with more detailed application event logs
```
java -jar .\build\libs\mugloar-0.0.1-SNAPSHOT.jar --logging.level.com.dragons.mugloar=DEBUG
```
Debug mode for more request/response logs
```
java -jar .\build\libs\mugloar-0.0.1-SNAPSHOT.jar --logging.level.com.dragons.mugloar.client=DEBUG
```