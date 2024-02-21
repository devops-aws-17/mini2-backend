pipeline {
    agent any

    stages {
        stage('docker build') {
            steps {
                sh 'docker build -t javaspring:v1 .'
            }
        }
      stage('docker tag') {
            steps {
                sh 'docker tag javaspring:v1 prasadchandu/java:java-v1'
            }
        }
      stage('docker login') {
            steps {
                sh 'docker login'
            }
        }
      stage('docker push') {
            steps {
                sh 'docker push prasadchandu/java:java-v1'
            }
      }
        
    }
}
