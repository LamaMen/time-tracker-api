job("Build and push Docker") {
    startOn {
        gitPush {
            enabled = true
            repository = "time-tracker-api"
            branchFilter {
                +"refs/heads/release"
            }
        }
    }

    container(displayName = "Build", image = "openjdk:8-jdk-alpine") {
        shellScript {
            content = """
            	./mvnw install -DskipTests
                mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
                cp -r ./target $mountDir/share
            """
        }
    }

    docker {
        beforeBuildScript {
            content = "cp -r $mountDir/share/target ./"
        }

        build {
            file = "./Dockerfile"
        }

        push("pampero.registry.jetbrains.space/p/time-tracker/services/main") {
            tags("0.1.0+\$JB_SPACE_EXECUTION_NUMBER", "latest")
        }
    }
}