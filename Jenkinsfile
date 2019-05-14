pipeline {
    agent {
        label 'master'
    }
    options { 
      skipDefaultCheckout() 
    }
    stages {
        stage('Code'){
            steps {
                script {
                        echo "Code Checkout Begins"
                        mvnHome =  tool name: 'apache-maven-3.6.0', type: 'maven'
                            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '6df16e81-b5bf-452b-863d-c192110dbc06', url: 'http://srv03wapsdlc2:8080/tfs/DefaultCollection/TESTGIT/_git/TESTGIT']]])                            
                            if (isUnix()) {
                             	sh "'${mvnHome}/bin/mvn' clean package -DskipTests docker:build"
                          	} else {
                                bat label: 'Build', script: "\"${mvnHome}\"\\bin\\mvn -Dmaven.test.failure.ignore clean package" 
                          	}
                        echo "Code Checkout Ends"
                }
            }
        }
        
        stage('Build') {
            steps{
                script{
                    echo "Build Begins"
                    mvnHome =  tool name: 'apache-maven-3.6.0', type: 'maven'
                    if (isUnix()) {
                        echo 'Build process in Unix'
                        sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
                    } else {
                        echo 'Build process Windows'
                        bat label: 'Build', script: "\"${mvnHome}\"\\bin\\mvn -Dmaven.test.failure.ignore clean package"
                    }
                    echo "Build Ends"
                }
            }
        }

        stage('SonarQube Analysis') {
            steps{
                script{
                    echo "SonarQube Analysis Begins"
                                mvnHome =  tool name: 'apache-maven-3.6.0', type: 'maven'
                                withSonarQubeEnv('SonarQube') {
                                    if (isUnix()) {
                                        echo 'SonarQube Analysis in Unix'
                                        sh "${mvnHome}/bin/mvn sonar:sonar"
                                        } 
                                    else {
                                       echo 'SonarQube Analysis in Windows'
                                       bat label: 'SonarQube Analysis', script: "\"${mvnHome}\"\\bin\\mvn sonar:sonar"
                                    }
                                }
                    echo "SonarQube Analysis Ends"
                }
            }
        }
        
        stage("SonarQube Quality Gate Status Check"){
            steps{
                script{
                     echo "SonarQube Quality Gate Status Check Begins"
                        timeout(time: 1, unit: 'HOURS') {
                            def qg = waitForQualityGate()
                                if (qg.status != 'OK') {
                                error "Pipeline aborted due to quality gate failure: ${qg.status}"
                            }
                        }
                        echo "SonarQube Quality Gate Status Check Ends"
                }
            }
        }
        
        stage ('Test') {
            steps {
                script{
                echo 'Test Begins'
                try {
                    // Any maven phase that that triggers the test phase can be used here.
                    mvnHome =  tool name: 'apache-maven-3.6.0', type: 'maven'
                        if (isUnix()) {
                            echo "Test Process in Unix"
                            sh "${mvnHome}/bin/mvn test -B"
                        } 
                        else {
                            echo 'Test Process in Windows'
                            bat label: 'Junit Testing process', script: "\"${mvnHome}\"\\bin\\mvn test -B"
                        }
                    } catch(Exception err) {
                        step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
                          	if (currentBuild.result == 'FAILURE')
                                echo "ERROR:  ${err}"
                                echo "RESULT: ${currentBuild.result}"
                          	throw err
                    }
                echo "Test Ends"
                }
            }
        }
        
        stage ('Deploy/Deliver') {
            steps {
                script{
                    echo 'Deploy/Deliver Begins'
                    bat label: '', script: 'copy D:\\Tools\\Jenkins\\JenkinsHome\\workspace\\scm_pipeline1\\target\\*.war D:\\apache-tomcat-8.0\\webapps'
                    echo 'DeploymentDeploy/Deliver Ends'
                }
            }
        }
   }
        
    post {
        always {
            echo 'JENKINS PIPELINE'
        }
        success {
            echo 'JENKINS PIPELINE SUCCESSFUL'
            archive "target/**/*"
            junit 'target/surefire-reports/**/*.xml'
        }
        aborted {
            echo 'JENKINS PIPELINE ABORTED'
        }
        
        failure {
            echo 'JENKINS PIPELINE FAILED'
            archive "target/**/*"
            junit 'target/surefire-reports/**/*.xml'
            emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
            subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
        }
        
    }
}