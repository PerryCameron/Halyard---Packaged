import java.text.SimpleDateFormat
import java.util.*

plugins {
    application
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.palantir.git-version") version "3.1.0"
}

group = "com.ecsail"

// Retrieve the Git version directly from the project object
val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()

application {
    // Main class for the application
    mainClass.set("com.ecsail.BaseApplication")
}

tasks.jar {
    manifest {
        attributes["Implementation-Version"] = project.version
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // SLF4J and Logback for logging
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("ch.qos.logback:logback-core:1.4.14")

    // Apache POI dependencies for Excel handling
    implementation("org.apache.poi:poi:5.2.5")
    implementation("org.apache.poi:poi-ooxml:5.2.5")

    // Log4J bridge to SLF4J
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.20.0")
    implementation("org.slf4j:jcl-over-slf4j:2.0.5")

    // MariaDB JDBC Driver
    implementation("org.mariadb.jdbc:mariadb-java-client:3.2.0")

    // Spring JDBC
    implementation("org.springframework:spring-jdbc:6.1.7")

    // JSON library
    implementation("org.json:json:20240303")

    // Apache HTTP Client
    implementation("org.apache.httpcomponents:httpcore:4.4.15")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")

    // JSch for SSH connections
    implementation("com.github.mwiede:jsch:0.2.7")

    // iText PDF
    implementation("com.itextpdf:itext7-core:7.2.6")
    implementation("com.itextpdf:layout:7.2.6")

    // OkHttp for HTTP requests
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.14")

    // JavaMail API
    implementation("javax.mail:mail:1.4.7")

    // Jackson for JSON processing
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf("-Xlint:deprecation", "-Xlint:unchecked"))
}

// Create the fat JAR using shadowJar
//tasks.named<ShadowJar>("shadowJar") {
//    archiveBaseName.set("Halyard")
//    archiveVersion.set("")  // Remove version from file name
//    archiveClassifier.set("all")  // Add "all" to indicate a fat JAR
//    manifest {
//        attributes["Main-Class"] = "com.ecsail.BaseApplication"
//    }
//}

// Create the runtime using `jlink`
tasks.register<Exec>("generateRuntime") {
    group = "build"
    description = "Generates a custom Java runtime image using jlink"

    doFirst {
        delete(file("build/runtime"))
    }

    commandLine(
        "/Users/parrishcameron/.sdkman/candidates/java/17.0.11.fx-librca/bin/jlink",
        "--module-path", "/Users/parrishcameron/.sdkman/candidates/java/17.0.11.fx-librca/jmods",
        "--add-modules", "java.base,java.desktop,java.prefs,java.sql.rowset,javafx.controls,jdk.unsupported",
        "--output", "build/runtime",
        "--strip-debug",
        "--compress", "2",
        "--no-header-files",
        "--no-man-pages"
    )
}

// Package the macOS app image using jpackage
tasks.register<Exec>("packageAppMac") {
    group = "build"
    description = "Packages the application with a bundled JRE for macOS using jpackage"

    doFirst {
        delete(file("build/jpackage/Halyard"))
    }

    commandLine(
        "/Users/parrishcameron/.sdkman/candidates/java/17.0.11.fx-librca/bin/jpackage",
        "--input", "build/libs",  // Path to the JAR file directory
        "--main-jar", "Halyard-all.jar",  // Name of the fat JAR
        "--main-class", "com.ecsail.BaseApplication",  // Main application class
        "--name", "Halyard",  // Name of the app
        "--type", "dmg",  // Type of package: "dmg" for macOS
        "--runtime-image", "build/runtime",  // Path to custom runtime image
        "--dest", "build/jpackage",  // Output directory
        "--icon", "src/main/resources/images/TSELogo.icns"  // macOS .icns icon
    )
}

// Task for Windows (adjusted for app name "Halyard")
tasks.register<Exec>("packageAppWindows") {
    group = "build"
    description = "Packages the application as a Windows executable"

    doFirst {
        delete(file("build/jpackage/HalyardInstaller"))
    }

    commandLine(
        "/Users/parrishcameron/.sdkman/candidates/java/17.0.11.fx-librca/bin/jpackage",
        "--input", "build/libs",
        "--main-jar", "Halyard-all.jar",
        "--main-class", "com.ecsail.BaseApplication",
        "--name", "Halyard",
        "--type", "exe",
        "--runtime-image", "/Users/parrishcameron/.sdkman/candidates/java/17.0.11.fx-librca",
        "--dest", "build/jpackage/HalyardInstaller",
        "--install-dir", System.getenv("UserProfile") + "/Halyard",
        "--icon", "src/main/resources/images/TSELogo.ico",
        "--win-menu",
        "--win-shortcut",
        "--win-console"
    )
}

        tasks.register("generateBuildInfoProperties") {
            doLast {
                // Create timestamp
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val buildTimestamp = dateFormat.format(Date())
                val javaVersion = System.getProperty("java.version") ?: "Unknown"

                // Generate the properties file
                val propertiesFile = file("src/main/resources/version.properties")
                propertiesFile.parentFile.mkdirs()
                propertiesFile.writeText("""
           version=${project.version}
           build.timestamp=$buildTimestamp
           java.version=$javaVersion
       """.trimIndent())
            }
        }

// Ensure build info is generated during the build
tasks.processResources {
    dependsOn("generateBuildInfoProperties")
}

tasks.test {
    useJUnitPlatform()
}

// Ensure runtime is generated before packaging
tasks.named("packageAppMac") {
    dependsOn("generateRuntime")
}