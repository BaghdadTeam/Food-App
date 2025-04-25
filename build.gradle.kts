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

    // junit params
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
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

    doFirst {
        val testFiles = fileTree("src/test/kotlin").matching {
            include("**/*Test.kt")
        }

        // Map test files to main class patterns (e.g., MyClassTest.kt → MyClass.*)
        val mainClassPatterns = testFiles.map { testFile ->
            val testPath = testFile.relativeTo(file("src/test/kotlin")).path
            val mainPath = testPath.replace("Test.kt", ".kt")
            val mainClassPath = mainPath.replace(".kt", ".*") // Include inner classes
            "**/$mainClassPath"
        }.toSet()

        if (mainClassPatterns.isEmpty()) {
            // No tests found, skip coverage checks
            tasks.jacocoTestCoverageVerification.get().isEnabled = false
            return@doFirst
        }

        // Include only classes that have corresponding tests
        classDirectories.setFrom(files(classDirectories.files.map { dir ->
            fileTree(dir) {
                include(mainClassPatterns)
            }
        }))
    }

    sourceDirectories.setFrom(files("src/main/kotlin"))
    executionData.setFrom(files("build/jacoco/test.exec"))
}

tasks.withType<JacocoCoverageVerification> {
    doFirst {
        val execFile = file("build/jacoco/test.exec")
        if (!execFile.exists() || !execFile.isFile) {
            isEnabled = false
            return@doFirst
        }

        violationRules {
            rule {
                limit {
                    minimum = BigDecimal.valueOf(1.0) // Enforce 100% coverage for tested classes
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