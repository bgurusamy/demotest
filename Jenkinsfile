
pipeline {
    agent any 
       //different stages are depicted here 
     stages {
     // Stage to check out the testcases from source repb
         
       stage('send initial notification') {
            steps {
                slackSend channel: '#visetest', color: 'good', message: "STARTED: '${JOB_NAME} ${BUILD_NUMBER}' ",baseUrl:'https://cim.slack.com/services/hooks/jenkins-ci/',teamDomain:'cim',token:'ldhDuSiSkvnLEeFLUPyWndJF'
            }
        }
 
 stage('Delete old files')
         {
            steps 
             {
                 dir('/tmp/visetest') {
                    deleteDir()
                     echo 'old files are deleted'
}
             }
         }
         
         
stage('create custom work space') 
{
steps
{
sh 'mkdir /tmp/visetest'  
echo 'custom work space is created'
}
}
  

stage('checkout test cases')
   {
    steps{
dir('/tmp/visetest') 
{  
checkout scm
echo 'test cases are downloaded to the local folder'
} 
 }
}

   stage('Execute fitnesse')
   {
    steps{
        
        
        
dir('/tmp/visetest')
{

  script{
                withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                    sh "java -jar Fitnesse/fitnesse-standalone.jar -p 9090  &"
                }
            }
  
}
  }
}
    
}
    post
    {
     success
        {
     emailext  subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], to: 'Balachandar_gurusamy@cable.comcast.com', body: """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
       <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>"""

        }
        failure
        {
     emailext  subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], to: 'Balachandar_gurusamy@cable.comcast.com', body: """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
       <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>"""

        }
    }
    
}
