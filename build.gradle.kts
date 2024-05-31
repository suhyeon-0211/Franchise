plugins {
	java
	id("org.springframework.boot") version "2.5.3"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.franchise"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
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
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("io.springfox:springfox-swagger-ui:3.0.0")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")

	implementation("mysql:mysql-connector-java:8.0.32")

	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	implementation("org.springframework.boot:spring-boot-starter-validation")

	implementation("org.springframework.boot:spring-boot-starter-security")

	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
