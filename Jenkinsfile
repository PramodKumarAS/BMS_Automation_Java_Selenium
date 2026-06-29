pipeline{
    agent any

    tools{
        maven 'mvn'
    }

    stages{

        stage('Check out'){
           steps{
                echo 'Checking out source code'
           }
        }

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


           steps{
                 bat 'mvn clean test'
           }
        }
    }

    post{
        always{
               bat 'docker compose down'
        }
    }
}