plugins {
  java
  id("org.springframework.boot") version "3.5.3"
  id("io.spring.dependency-management") version "1.1.7"
}

group = "com.vicangel"
version = "0.0.1-SNAPSHOT"
val mapstructVersion = "1.6.3"
val assertjVersion = "3.6.1"
val jjwtVersion = "0.13.0"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")
  implementation("org.mapstruct:mapstruct:${mapstructVersion}")
  implementation("io.jsonwebtoken:jjwt-api:${jjwtVersion}")
  compileOnly("org.projectlombok:lombok")
  runtimeOnly("com.mysql:mysql-connector-j")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:${jjwtVersion}")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:${jjwtVersion}")
  annotationProcessor("org.projectlombok:lombok")
  annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.assertj:assertj-core:${assertjVersion}")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.test {
  jvmArgs("-XX:+EnableDynamicAgentLoading") // resolve warnings "A java agent has been loaded dynamically"
}

