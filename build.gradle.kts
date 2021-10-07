plugins {
    kotlin("jvm") version "1.5.30"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    `maven-publish`
}

group = "kr.kro.sweetwolf"
version = "0.0.0"

repositories {
    mavenCentral()
}

dependencies {
}

tasks{
    compileKotlin{
        kotlinOptions.jvmTarget = "16"
    }
    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }
    create<Jar>("sourcesJar") {
        archiveClassifier.set("source")
        from(sourceSets["main"].allSource)
    }
    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")
        archiveVersion.set(project.version.toString())
        doLast {
            // jar file copy
            copy {
                from(archiveFile)
                val plugins = File("C:\\Users\\SweetWolf\\Desktop\\바탕화면")
                into(if (File(plugins, archiveFileName.get()).exists()) plugins else plugins)
            }
        }
    }
}
publishing {
    publications {
        create<MavenPublication>(project.name) {
            artifact(tasks["sourcesJar"])
            from(components["java"])
        }
    }
}