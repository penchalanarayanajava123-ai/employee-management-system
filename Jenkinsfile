pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'JDK17'
    }
    environment {
        TOMCAT_HOME = '/home/ssm-user/apache-tomcat-10.1.61'
        TOMCAT_WEBAPPS = '/home/ssm-user/apache-tomcat-10.1.61/webapps'
    }
    stages {
        stage('Checkout') { steps { checkout scm } }
        stage('Build WAR') { steps { sh 'mvn clean package -DskipTests' } }
        stage('Deploy to Tomcat') {
            steps {
                sh '''
                    $TOMCAT_HOME/bin/shutdown.sh || true
                    sleep 5
                    pkill -f "catalina" || true
                    sleep 3
                    rm -rf $TOMCAT_WEBAPPS/ROOT.war $TOMCAT_WEBAPPS/ROOT
                    cp target/*.war $TOMCAT_WEBAPPS/ROOT.war
                    $TOMCAT_HOME/bin/startup.sh
                    sleep 10
                    curl -f http://localhost:8080/api/employees || echo "App starting..."
                '''
            }
        }
    }
}
