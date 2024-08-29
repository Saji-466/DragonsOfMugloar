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


### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.2/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.2/gradle-plugin/packaging-oci-image.html)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

