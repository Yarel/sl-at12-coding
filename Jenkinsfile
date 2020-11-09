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
        stage('Test Coverage') {
            steps {
                echo 'Testing..'
                sh './gradlew test'
              }

            }
            post {
                always {
                  publishHTML target: [
                      allowMissing: false,
                      alwaysLinkToLastBuild: false,
                      keepAll: true,
                      reportDir: 'build/reports/jacoco/coverage',
                      reportFiles: 'index.html',
                      reportName: 'RCov Report'
                    ]
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