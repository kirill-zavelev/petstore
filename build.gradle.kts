plugins {
    id("java")
    id("io.qameta.allure") version "2.9.6"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation ("org.testng:testng:7.8.0")
    implementation ("io.rest-assured:rest-assured:5.3.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    compileOnly ("org.projectlombok:lombok:1.18.30")
    annotationProcessor ("org.projectlombok:lombok:1.18.24")
    implementation ("com.github.javafaker:javafaker:1.0.2") {exclude(module = "snakeyaml")}
    implementation(group = "org.yaml", name = "snakeyaml", version = "1.17")
    implementation ("org.assertj:assertj-core:3.24.2")

    testImplementation("org.aspectj:aspectjweaver:1.9.5")
    testImplementation("io.qameta.allure:allure-testng:2.17.3")
    implementation("io.qameta.allure:allure-rest-assured:2.20.1")
    testImplementation("io.qameta.allure:allure-java-commons:2.20.1")
    testImplementation("io.qameta.allure:allure-assertj:2.12.1")
    testImplementation("io.qameta.allure:allure-commandline:2.12.1")
}

tasks.test {
    useTestNG()
}