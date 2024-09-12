pipeline {
  agent any
     tools {
        maven 'MAVEN'
    }
  stages{
    stage('Install Dependencies'){
      steps{
        bat 'mvn clean install'
      }
    }
    stage('test'){
      steps{
        bat 'echo "testing application...."'
      }
    }
    stage('Deploy apps'){
      steps{
        bat 'echo "deploying app..."'
      }
    }
  }
}
