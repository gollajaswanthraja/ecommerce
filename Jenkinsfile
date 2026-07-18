pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
    }

    environment {
        //IMAGE_NAME = 'gollajaswanthraja/jenkins-app'
        IMAGE_NAME = 'ecommerce-app'
        IMAGE_TAG = "${BUILD_NUMBER}"
        //IMAGE_TAG = "1.0"
    }

    stages {
        stage('Git checkout') {
            steps {
                git branch: 'feature/v1.0.0', credentialsId: '8a68fc64-55a5-4f46-8415-5b0df6f9b145', url: 'https://github.com/gollajaswanthraja/ecommerce.git'
            }
        }


        stage("Compile & Build"){
            steps{
                 bat 'mvn clean package'
            }
        }

        // stage('OWASP Dependency Check') {
        //     steps {
        //         catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
        //         dependencyCheck(
        //             additionalArguments: '--scan ./ --format XML',
        //             odcInstallation: 'DP'
        //         )

        //         dependencyCheckPublisher(
        //             pattern: '**/dependency-check-report.xml'
        //         )
        //         }
        //     }
        // }


   //      stage('Docker Login') {
//            steps {
//                withCredentials([usernamePassword(
//                    credentialsId: 'dockerhub-creds',
//                    usernameVariable: 'DOCKER_USER',
//                    passwordVariable: 'DOCKER_PASS'
//                )]) {
//                    bat '"%DOCKER%" login -u %DOCKER_USER% -p %DOCKER_PASS%'
//                }
//            }
//        }



        stage('Run Docker Container') {
            steps {
                bat 'docker build -t %IMAGE_NAME%:%IMAGE_TAG% .'
                bat 'docker stop ecom-container ||  ver > nul'
                bat 'docker rm ecom-container ||  ver > nul'
                bat 'docker run -d --name ecom-container -p 8082:8082 %IMAGE_NAME%:%IMAGE_TAG%'
            }
        }


    }
     post {
        success {
            echo 'Docker image pushed successfully to Docker Hub.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
