pipeline{
    agent {
       label 'chrome'
    }

    tools{
        maven 'mvn'
    }

    parameters{
        choice(
           name:'BROWSER',
           choices:['chrome','firefox'],
           description:'Select Browser'
        )

        choice(
           name:'RUN_TYPE',
           choices:['local','remote'],
           description:'Execute Type'
        )

        booleanParam(
            name:'HEADLESS',
            defaultValue:true,
            description:'Run browser in headless mode'
        )
    }

    stages{

        stage('Start Selenium Grid'){
           steps{
                 bat 'docker compose up -d'
           }
        }

        stage('wait for grid to be ready'){
           steps{
                 bat 'ping 127.0.0.1 -n 31 > nul'
           }
        }

        stage('Run selenium UI Tests'){

            parallel{
                stage('chrome'){
                   steps{
                      ws("${env.WORKSPACE}\\chrome") {
                        withCredentials([
                            usernamePassword(
                                credentialsId:'user',
                                usernameVariable:'USER_EMAIL',
                                passwordVariable:'USER_PASSWORD'
                            ),
                            usernamePassword(
                                credentialsId:'partner',
                                usernameVariable:'PARTNER_EMAIL',
                                passwordVariable:'PARTNER_PASSWORD'
                            ),
                            usernamePassword(
                                credentialsId:'admin',
                                usernameVariable:'ADMIN_EMAIL',
                                passwordVariable:'ADMIN_PASSWORD'
                            ),
                            string(credentialsId:'mongo-uri',variable:'MONGO_URI')
                        ]){
                            bat "mvn clean test -Dbrowser=chrome -DrunType=${params.RUN_TYPE} -Dheadless=${params.HEADLESS}"
                        }
                      }
                   }
                }

                stage('firefox'){
                   steps{
                      ws("${env.WORKSPACE}\\firefox") {

                        withCredentials([
                            usernamePassword(
                                credentialsId:'user',
                                usernameVariable:'USER_EMAIL',
                                passwordVariable:'USER_PASSWORD'
                            ),
                            usernamePassword(
                                credentialsId:'partner',
                                usernameVariable:'PARTNER_EMAIL',
                                passwordVariable:'PARTNER_PASSWORD'
                            ),
                            usernamePassword(
                                credentialsId:'admin',
                                usernameVariable:'ADMIN_EMAIL',
                                passwordVariable:'ADMIN_PASSWORD'
                            ),
                            string(credentialsId:'mongo-uri',variable:'MONGO_URI')
                        ]){
                            bat "mvn clean test -Dbrowser=firefox -DrunType=${params.RUN_TYPE} -Dheadless=${params.HEADLESS}"
                        }
                      }
                   }
                }

            }
        }
    }

    post{
        always{
                bat 'docker compose down'

                publishHTML([
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/extent-reports',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report'
                ])

                allure(
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                )

                archiveArtifacts(
                    artifacts: '''
                        target/extent-reports/**
                    ''',
                    fingerprint: true,
                    allowEmptyArchive: true
                )
        }
    }
}