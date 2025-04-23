import java.io.ByteArrayOutputStream

plugins {
    kotlin("jvm") version "2.1.10"
    jacoco
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // koin
    implementation("io.insert-koin:koin-core:4.0.3")

    // kotlin date time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")

    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // kotest, assertion
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")

    // google truth
    testImplementation("com.google.truth:truth:1.4.2")

    // mockk
    testImplementation("io.mockk:mockk:1.14.0")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.10"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    // Dynamically include only modified files related to tests
    doFirst {
        // Get modified files in the current PR (this assumes you're using Git)
        val modifiedFiles = mutableListOf<String>()
        val outputStream = ByteArrayOutputStream()

        // Execute the git command to get modified files
        exec {
            commandLine("git", "diff", "--name-only", "origin/develop...HEAD")
            standardOutput = outputStream
        }

        // Capture the output of the command
        modifiedFiles.addAll(outputStream.toString().split("\n"))

        // Filter only Kotlin files in src/test (or any test directories you have)
        val testFiles = modifiedFiles.filter { it.startsWith("src/test") && it.endsWith(".kt") }

        if (testFiles.isEmpty()) {
            // If no test files are modified, skip JaCoCo verification
            tasks.jacocoTestCoverageVerification.get().isEnabled = false
        }

        // Include only the test-related classes
        classDirectories.setFrom(fileTree("build/classes/kotlin/main") {
            include(testFiles.map { it.replace("src/test/kotlin/", "build/classes/kotlin/main/") })
        })
    }

    sourceDirectories.setFrom(files("src/main/kotlin"))
    executionData.setFrom(files("build/jacoco/test.exec"))
}


// Coverage verification configuration (only enabled if tests exist)
tasks.withType<JacocoCoverageVerification> {
    doFirst {
        val execFile = file("build/jacoco/test.exec")
        if (!execFile.exists()) {
            // Skip verification if no test execution file exists
            enabled = false
        } else {
            violationRules {
                rule {
                    limit {
                        minimum = BigDecimal.valueOf(1.0) // 100% coverage
                    }
                }
            }
        }
    }
}

tasks.named("check").configure {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

kotlin {
    jvmToolchain(20)
}
