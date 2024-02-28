import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    java
    kotlin("jvm") version "1.9.22"
    id("org.springframework.boot") version "3.2.1"
    id("com.diffplug.spotless") version "6.25.0"
}

val springBootVersion = "3.2.1"
val grpcBomVersion = "1.61.0"
val springCloudDependenciesVersion = "2023.0.0"
val springCloudGcpDependenciesVersion = "5.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

configure<SpotlessExtension> {
    java {
        googleJavaFormat()
    }
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-starter-parent:$springBootVersion"))
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:$springCloudDependenciesVersion"))

    // Server
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Logging
    implementation("io.micrometer:context-propagation")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")

    // Metrics
    implementation("io.micrometer:micrometer-registry-prometheus")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("org.mock-server:mockserver-spring-test-listener:5.15.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
