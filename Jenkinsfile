pipeline {
    agent any

    stages {
        stage('User Input') {
            steps {
                script {
                    try {
                        def userInput = timeout(time: 30, unit: 'SECONDS') {
                            input message: 'Select deployments', parameters: [
                                booleanParam(name: 'DEPLOY_FRONT', defaultValue: true),
                                booleanParam(name: 'DEPLOY_BACK', defaultValue: true)
                            ]
                        }
                        env.DEPLOY_FRONT = userInput.DEPLOY_FRONT.toString()
                        env.DEPLOY_BACK = userInput.DEPLOY_BACK.toString()
                    } catch (err) {
                        env.DEPLOY_FRONT = 'true'
                        env.DEPLOY_BACK = 'true'
                    }
                }
            }
        }
        stage('Setup and Cleanup') {
            steps {
                script {
                    if (env.DEPLOY_FRONT == 'true') {
                        sh '''
                        if docker ps -aq -f name=angular-container | grep -q .; then
                            echo "Stopping and removing the existing container..."
                            docker stop angular-container
                            docker rm angular-container
                        fi
                        '''
                    }

                    if (env.DEPLOY_BACK == 'true') {
                        sh '''
                        if docker ps -aq -f name=spring-app-container | grep -q .; then
                            echo "Stopping and removing the existing container..."
                            docker stop spring-app-container
                            docker rm spring-app-container
                        fi
                        '''
                    }

                    sh '''
                    echo "Cleaning up dangling images..."
                    docker system prune -f --volumes
                    '''
                }
            }
        }

        stage('Build Server Docker Image') {
            when {
                expression { return env.DEPLOY_BACK == 'true' }
            }
            steps {
                script {
                    sh 'docker build -t poke-app-server -f ./deployment/server/Dockerfile .'
                }
            }
        }

          stage('Build Angular Docker Image') {
            when {
                expression { return env.DEPLOY_FRONT == 'true' }
            }
            steps {
                script {
                    sh 'docker build --no-cache -t poke-app-front -f ./deployment/angular/Dockerfile .'
                }
            }
        }

        stage('Run Angular Container') {
            when {
                expression { return env.DEPLOY_FRONT == 'true' }
            }
            steps {
                script {
                    sh 'docker run -d -p 80:80 --name angular-container poke-app-front'
                }
            }
        }

        stage('Run Server Container') {
            when {
                expression { return env.DEPLOY_BACK == 'true' }
            }
            steps {
                script {
                    sh 'docker run -d -p 4445:4445 --name spring-app-container poke-app-server'
                }
            }
        }

        stage('Verify Containers') {
            steps {
                script {
                    sh 'docker ps'
                }
            }
        }
    }

    post {
        always {
            script {
                sh 'rm -rf *'
            }
        }
    }
}