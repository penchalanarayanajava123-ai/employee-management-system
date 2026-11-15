pipeline {
    agent any

    tools {
        maven 'Maven-3'
        jdk 'Java-17'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Code Checkout Started...'
                git branch: 'main', url: 'https://github.com/penchalanarayanajava123-ai/employee-management-system.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Maven Build Started...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test'
            }
        }

        stage('Archive WAR') {
            steps {
                echo 'Saving WAR file...'
                archiveArtifacts artifacts: 'target/*.war', followSymlinks: false
            }
        }
    }

    post {
        success {
            echo 'BUILD SUCCESS - DevOps Pipeline Completed!'
        }
        failure {
            echo 'BUILD FAILED - Check Console Output'
        }
    }
}
