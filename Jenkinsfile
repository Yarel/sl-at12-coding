pipeline {
    agent any
    environment {
      ARTIFACTORY_USER = credentials('artifactory_user')
      ARTIFACTORY_PASSWORD = credentials('artifactory_password')
    }
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
        stage('Test Coverage') {
            steps {
                echo 'Testing..'
                sh './gradlew test'
            }
            post {
                always {
                  publishHTML target: [
                      allowMissing: false,
                      alwaysLinkToLastBuild: false,
                      keepAll: true,
                      reportDir: 'build/reports/jacoco/test/html',
                      reportFiles: 'index.html',
                      reportName: 'Test Coverage'
                    ]
                }
                failure {
                  publishHTML target: [
                      allowMissing: false,
                      alwaysLinkToLastBuild: false,
                      keepAll: true,
                      reportDir: 'build/reports/checkstyle',
                      reportFiles: 'main.html',
                      reportName: 'CheckStyle'
                    ]
                }
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build artifactoryPublish --console verbose -P artifactory_user=$ARTIFACTORY_USER -P artifactory_password=$ARTIFACTORY_PASSWORD'
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