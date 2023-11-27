pipeline {
    agent { label "Jenkins-Agent" }
    tools {
             jdk 'Java 17'
             maven 'Maven3'
    }

    stages {
        stage("Cleanup Workspace") {
            steps {
                cleanWs()
            }
        }

        stage("Checkout from SCM") {
               steps {
                   git branch: 'main', credentialsId: 'github', url: 'https:https://github.com/walmart-2023-sep-ascend/Team08-OrderHistoryTracker'
               }
        }

       stage("Build Application"){
              steps {
                  sh "mvn clean package"  
          }
       stage("Test Application"){
              steps{
                 sh "mvn test"
        }
}
}
}