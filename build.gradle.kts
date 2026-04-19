plugins {
    java
    id("org.springframework.boot") version "4.0.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.flywaydb.flyway") version "11.1.0"
}

group = "p466.team2"
version = "0.0.1-SNAPSHOT"
description = "Provides functionality for managing videos in the schedule."

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(annotationProcessor)
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2025.1.1"
extra["testContainersVersion"] = "1.17.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.flywaydb:flyway-core:11.1.0")
    implementation("org.flywaydb:flyway-database-postgresql:11.1.0")

    runtimeOnly("org.postgresql:postgresql:42.7.2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-thymeleaf-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.testcontainers:postgresql")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

flyway {
    url = "jdbc:postgresql://localhost:5432/mytvdb_schedule"
    user = "user"
    password = "password"
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.testcontainers:testcontainers-bom:${property("testContainersVersion")}")
    }
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {systemProperty("spring.profiles.active", "testdata")}

tasks.withType<Test> {
    useJUnitPlatform()
}
