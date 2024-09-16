pipeline {
    agent any
    tools {
        // Specify the name of the Maven installation configured in Jenkins
        maven 'MAVEN'
    }
    stages {
        stage("Clone Code") {
            steps {
                git branch: 'main', credentialsId: 'fasoft-git credentials', url: 'https://github.com/fasoft01/online-resource-hub.git'
            }
        }
        stage("Build Code") {
            steps {
                bat 'mvn clean install'
            }
        }
        stage("Copy JAR and Restart Application") {
            steps {
                script {
                    def jarFile = 'app/target/online-resource-hub.jar'
                    def destinationDir = '/data/jars'

                    bat "cp ${jarFile} ${destinationDir}/"

                    bat 'sudo systemctl restart online-resource-hub.service'
                }
            }
        }
    }
}