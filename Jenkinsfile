pipeline {
    agent any
    
    tools {
        maven 'Maven3'
    }
    
    environment {
        registry = "615299729731.dkr.ecr.us-east-1.amazonaws.com/my_repo"
        SONARQUBE_SERVER = 'SonarQube' // Replace with your SonarQube server name
    }
    
    stages {
        stage('git checkout') {      
            steps {
                git 'https://github.com/venkatasupriyaa/docker-springboot'
            }
        }
        
       stage('building and SonarQube Analysis') {
            steps {
                script {
                    // This will set up SonarQube environment for the analysis
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn clean install sonar:sonar -Dsonar.login=sqa_e91d70f421d493ef798e86ea08449ab3d3953c32'
                    }
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
                script {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Pipeline failed due to quality gate failure: ${qg.status}"
                    }
                }
            }
        }

        
        stage('Building image') {
        steps{
           script {
            dockerImage = docker.build registry 
            dockerImage.tag("$BUILD_NUMBER")
             }
          }
       }
        
    stage('Pushing to ECR') {
     steps{  
         script {
                sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 615299729731.dkr.ecr.us-east-1.amazonaws.com'
                sh 'docker push 615299729731.dkr.ecr.us-east-1.amazonaws.com/my_repo:$BUILD_NUMBER'
         }
        }
    }
      
     stage('Helm Deploy') {
            steps {
                 sh "helm upgrade first --install mychart --namespace helm-deployment --set image.tag=$BUILD_NUMBER"
             }
      }
  
  }
}
