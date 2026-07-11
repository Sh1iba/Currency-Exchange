plugins {
    id("java")
    war
}

group = "io.github.sh1iba"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.war {
    archiveFileName = "ROOT.war"
}