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
                        mvnHome =  tool name: 'apache-maven-3.6.1', type: 'maven'
                        try{
                            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '327fa637-c43a-489a-b8b2-09a376261557', url: 'http://srv03wapsdlc2:8080/tfs/DefaultCollection/TESTGIT/_git/TESTGIT']]])
                                if (isUnix()) {
                                    sh "'${mvnHome}/bin/mvn' clean package -DskipTests docker:build"
                                } else {
                                    bat label: 'Build', script: "\"${mvnHome}\"\\bin\\mvn -Dmaven.test.failure.ignore clean package" 
                                }
                        }catch(err){
                            if (currentBuild.result == 'FAILURE')
                            // Email Notfication triggers
                            emailext body: " Results: ${currentBuild.currentResult} Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} Exception: ${err}\n More info at: ${env.BUILD_URL}",
                            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                            subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
                        }
                        echo "Code Checkout Ends"
                }
            }
        }
        
        stage('Build') {
            steps{
                script{
                    echo "Build Begins"
                    mvnHome =  tool name: 'apache-maven-3.6.1', type: 'maven'
                        try{
                           if (isUnix()) {
                                echo 'Build process in Unix'
                                sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
                            } else {
                                echo 'Build process Windows'
                                bat label: 'Build', script: "\"${mvnHome}\"\\bin\\mvn -Dmaven.test.failure.ignore clean package"
                            }
                        }catch(err){
                            if (currentBuild.result == 'FAILURE')
                                     // Email Notfication triggers
                                    emailext body: " Results: ${currentBuild.currentResult} Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} Exception: ${err}\n More info at: ${env.BUILD_URL}",
                                    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                                    subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"  
                        }
                    echo "Build Ends"
                }
            }
        }

       stage('SonarQube Analysis') {
            steps{
                script{
                    echo "SonarQube Analysis Begins"
                                mvnHome =  tool name: 'apache-maven-3.6.1', type: 'maven'
                                try{
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
                                }catch(errr){
                                    if (currentBuild.result == 'FAILURE')
                                     // Email Notfication triggers
                                    emailext body: " Results: ${currentBuild.currentResult} Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} Exception: ${err}\n More info at: ${env.BUILD_URL}",
                                    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                                    subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
                                }
                    echo "SonarQube Analysis Ends"
                }
            }
        }
        
        stage("SonarQube Quality Gate Status Check"){
            steps{
                script{
                    echo "SonarQube Quality Gate Status Check Begins"
                        try {
                                timeout(time: 1, unit: 'HOURS') {
                                    def qg = waitForQualityGate()
                                        if (qg.status != 'OK') {
                                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                                            // Email Notfication triggers
                                            emailext body: " Results: ${currentBuild.currentResult} Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} \n More info at: ${env.BUILD_URL}",
                                            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                                            subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"    
                                        }
                                }
                        }catch(errr){
                            if (currentBuild.result == 'FAILURE')
                                // Email Notfication triggers
                                emailext body: " Results: ${currentBuild.currentResult} Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} Exception: ${err}\n More info at: ${env.BUILD_URL}",
                                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                                subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
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
                    mvnHome =  tool name: 'apache-maven-3.6.1', type: 'maven'
                        if (isUnix()) {
                            echo "Test Process in Unix"
                            sh "${mvnHome}/bin/mvn test -B"
                        } 
                        else {
                            echo 'Test Process in Windows'
                            bat label: 'Junit Testing process', script: "\"${mvnHome}\"\\bin\\mvn test -B"
                        }
                    } catch(err) {
                        step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
                          	if (currentBuild.result == 'FAILURE')
                                echo "ERROR:  ${err}"
                                echo "RESULT: ${currentBuild.result}"
                            
                            // Email Notfication triggers
                            emailext body: " Results: ${currentBuild.currentResult} Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} Error: ${err}\n More info at: ${env.BUILD_URL}",
                            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                            subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
                          	throw exception
                    }
                echo "Test Ends"
                }
            }
        }
        
        stage ('Deploy/Deliver') {
            steps {
                script{
                    echo 'Deploy/Deliver Begins'
                    bat label: '', script: 'build \'Tomcat_Deploy\''
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
            
             // Email Notfication triggers
            emailext body: " Results: ${currentBuild.currentResult} Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} \n More info at: ${env.BUILD_URL}",
            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
            subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
        }
        aborted {
            echo 'JENKINS PIPELINE ABORTED'
             // Email Notfication triggers
            emailext body: " Results: ${currentBuild.currentResult} Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} \n More info at: ${env.BUILD_URL}",
            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
            subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
        }
      
        failure {
            echo 'JENKINS PIPELINE FAILED'
            archive "target/**/*"
            junit 'target/surefire-reports/**/*.xml'
            
             // Email Notfication triggers
            emailext body: " Results: ${currentBuild.currentResult} Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} Exception: ${err}\n More info at: ${env.BUILD_URL}",
            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
            subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
        }
    }
        stages('workitems')
        {
        powershell label: '', script: '''# using env vars passed from VSTS build
        $collectionuri = $Env:SYSTEM_TEAMFOUNDATIONCOLLECTIONURI
        $token = $Env:SYSTEM_ACCESSTOKEN # need to configure build to allow passing OAuth tokens

        $basicAuth = "{0}:{1}"-f "ivan-the-terrible", $token
        $basicAuth = [System.Text.Encoding]::UTF8.GetBytes($basicAuth)
        $basicAuth = [System.Convert]::ToBase64String($basicAuth)
        $headers = @{Authorization=("Basic {0}"-f $basicAuth)}

        $WorkItemType = \'User Story\'

        $url = $collectionuri + \'DefaultCollection/_apis/wit/wiql?api-version=1.0\'

        $WIQL_query = "Select [System.Id], [System.Title], [System.State] From WorkItems Where [System.WorkItemType] = \'" + $WorkItemType + "\' AND [State] = \'Closed\' order by [Microsoft.VSTS.Common.Priority] asc, [System.CreatedDate] desc"
        $body = @{ query = $WIQL_query }
        $bodyJson=@($body) | ConvertTo-Json

        $response = Invoke-RestMethod -Uri $url -headers $headers -Method Post -ContentType "application/json" -Body $bodyJson

        $workitems = $response.workItems

        Write-Host "Found" $workitems.Count "work items of type:" $WorkItemType'''
        }
   
}