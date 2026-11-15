
pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    environment {
        TOMCAT_HOME = '/home/ec2-user/apache-tomcat-10.1.61'
        TOMCAT_WEBAPPS = '/home/ec2-user/apache-tomcat-10.1.61/webapps'
        APP_URL = 'http://localhost:8080/api/employees'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn clean package -DskipTests'

                sh '''
                    echo "Checking WAR file..."
                    ls -lh target/*.war
                '''
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                sh '''
                    set -e

                    echo "Stopping Tomcat..."

                    $TOMCAT_HOME/bin/shutdown.sh || true

                    sleep 5

                    echo "Removing old deployment..."

                    rm -rf $TOMCAT_WEBAPPS/ROOT.war
                    rm -rf $TOMCAT_WEBAPPS/ROOT

                    echo "Deploying new WAR..."

                    cp target/*.war $TOMCAT_WEBAPPS/ROOT.war

                    echo "Starting Tomcat..."

                    $TOMCAT_HOME/bin/startup.sh

                    echo "Waiting for application..."

                    for i in $(seq 1 30)
                    do
                        if curl -fsS $APP_URL
                        then
                            echo "Application deployed successfully!"
                            exit 0
                        fi

                        sleep 5
                    done

                    echo "Application deployment failed!"
                    exit 1
                '''
            }
        }
    }

    post {
        success {
            echo 'DEPLOY SUCCESS! API LIVE at http://16.178.153.169:8080/api/employees'
        }

        failure {
            echo 'DEPLOY FAILED! Check Jenkins and Tomcat logs.'
        }
    }
}
