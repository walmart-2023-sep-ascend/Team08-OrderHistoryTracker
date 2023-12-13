pipeline {
    agent any
    tools {
             jdk 'OpenJDK 17'
             maven 'Maven3'
             git 'Default'
    }
     environment {
	    APP_NAME = "order-history-tracker"
            RELEASE = "1.0.0"
            DOCKER_USER = "sathishkph"
	    DOCKER_PASS = 'dockerhubcredentials'
	    DOCKER_IMAGE_NAME = "${DOCKER_USER}/${APP_NAME}"
            DOCKER_IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
     }

    stages {
        stage("Cleanup Workspace") {
            steps {
                deleteDir()
            }
        }

        stage("Checkout from SCM") {
               steps {
                   git branch: 'main', credentialsId: 'github', url: 'https://github.com/walmart-2023-sep-ascend/Team08-OrderHistoryTracker'
               }
        }

       stage("Build Application") {
              steps {
                  sh 'mvn clean package -DCART_URI="https://ascend-team08.free.beeceptor.com/cart/"  -DORDER_URI="https://ascend-team08.free.beeceptor.com/order/"'  
              }
       }   
       stage("Test Application") {
              steps{
                 sh 'mvn test -DCART_URI="https://ascend-team08.free.beeceptor.com/cart/"  -DORDER_URI="https://ascend-team08.free.beeceptor.com/order/"'
              }
      }
        stage("SonarQube Analysis") {
            steps {
                script {
                    withSonarQubeEnv(credentialsId: 'jenkins-sonarqube-token') {
                    sh "mvn sonar:sonar"
                    }
                }
            }
        }
        stage("Quality Gate") {
            steps {
                script {
                    waitForQualityGate abortPipeline: false, credentialsId: 'jenkins-sonarqube-token'
                }
            }
        } 
        stage("Build & Push Docker Image") {
            steps {
                script {
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image = docker.build("${DOCKER_IMAGE_NAME}")
                    }

                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image.push("${DOCKER_IMAGE_TAG}")
                        docker_image.push('latest')
                    }
                }
            }

       }
    }
}
