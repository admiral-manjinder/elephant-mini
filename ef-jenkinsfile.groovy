pipeline{
    agent any

    parameters{
        string (name:'stackname', defaultValue:'elephant', description:'stackname here') 

    }

    environment{
        stackname ="${params.stackname}"
    }

    stages{

        stage('deploy cf stack'){
            steps{
                script{
                    sh "echo ${stackname}"
                    withAWS(region:'us-east-1', credentials:'AWS_KEYS'){
                        def outputs = cfnUpdate(stack:"${stackname}", file:'ef-cf-template.yml',timeotInMinutes:45,pollInterval:1000)
                    }
                }
            }
        }
    }

    post{
        success{
            sh "echo success"
        }
        failure{
            sh "echo failure"
        }
    }
}

