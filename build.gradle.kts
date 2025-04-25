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

tasks.jacocoTestReport {
    reports {
        csv.required.set(false)
        xml.required.set(true)
        html.required.set(true)
    }
    dependsOn(tasks.test)
}

tasks.jacocoTestCoverageVerification {
    executionData.setFrom(fileTree(buildDir).include("/jacoco/test.exec"))
    classDirectories.setFrom(fileTree("build/classes/kotlin/main/logic/usecase"))
    sourceDirectories.setFrom(files("src/main/kotlin/logic/usecase"))
    violationRules {
        rule {
            limit {
                counter = "CLASS"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal() // 100% coverage

            }
        }
    }
}

kotlin {
    jvmToolchain(20)
}