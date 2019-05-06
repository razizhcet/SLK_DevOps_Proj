pipeline {
  agent any
  stages {
  stage ('Build Process') {
        
     steps {
            checkout([$class: 'GitSCM', branches: [[name: '*/final']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'd5bc041c-753b-4f55-8a88-ef82c6c868c3', url: 'http://srv03wapsdlc2:8080/tfs/DefaultCollection/TESTGIT/_git/TESTGIT']]])
            bat label: '', script: 'mvn clean compile'
            }
    }
     stage ('Test Process') {
      steps {
        bat label: '', script: 'mvn test'
        junit 'target/surefire-reports/**/*.xml'
        echo 'JUnit Test cases are executed successfully.'
      }
    }
    
    stage ('SonarQube Code Analasys Process') {
      steps {
        bat label: '', script: 'mvn install sonar:sonar'
        echo 'SonarQube code Analysys has been checked.'
      }
    }
    stage ('Release Process') {
      steps {
        echo 'Released 1.0 Version.'
      }
    }
    stage ('Deploy / Delivery Porces') {
      steps {
        echo 'Application has been Deployed'
      }
    }
  }
  
}
