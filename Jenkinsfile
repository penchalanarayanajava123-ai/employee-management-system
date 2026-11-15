pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    environment {
        TOMCAT_HOME = '/home/ec2-user/apache-tomcat-10.1.61'
        TOMCAT_WEBAPPS = '/home/ec2-user/apache-tomcat-10.1.61/webapps'
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
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                sh '''
                    echo "Stopping Tomcat..."
                    $TOMCAT_HOME/bin/shutdown.sh || true
                    sleep 5
                    pkill -f "catalina" || true
                    sleep 3

                    echo "Removing old WAR..."
                    rm -rf $TOMCAT_WEBAPPS/ROOT.war $TOMCAT_WEBAPPS/ROOT

                    echo "Deploying new WAR..."
                    cp target/*.war $TOMCAT_WEBAPPS/ROOT.war

                    echo "Starting Tomcat..."
                    $TOMCAT_HOME/bin/startup.sh
                    sleep 10
                    curl -f http://localhost:8080/api/employees || echo "App starting, check logs..."
                '''
            }
        }
    }

    post {
        success {
            echo 'DEPLOY SUCCESS! API LIVE at http://16.178.153.169:8080/api/employees'
        }
        failure {
            echo 'DEPLOY FAILED! Check logs'
        }
    }
}
