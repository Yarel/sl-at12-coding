pipeline {
    agent any
    stages {
        stage('Assemble') {
            steps {
                echo 'Assembling..'
                sh './gradlew clean assemble'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'build/**/*.jar'
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                sh './gradlew test'
            }
            post {
                always {
                    junit 'build/test-results/test/**/*.xml'
                    archiveArtifacts 'build/reports/**/*'
                }
            }
        }
        stage('Package') {
            steps {
                sh './gradlew build'
            }
            post {
                always {
                    archiveArtifacts 'build/distributions/**/*.tar'
                    archiveArtifacts 'build/distributions/**/*.zip'
                }
            }
        }
    }
}