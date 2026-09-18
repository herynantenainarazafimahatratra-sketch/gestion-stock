pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/herynantenainarazafimahatratra-sketch/gestion-stock.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Tests') {
            steps {
                bat 'mvnw.cmd test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t gestion-stock-backend .'
            }
        }
    }

    post {
        success {
            echo 'Pipeline terminé avec succès !'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}