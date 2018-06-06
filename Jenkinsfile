
pipeline {
    agent any 
       //different stages are provided  below 
     stages {
    
          // to send initial slack notification
       stage('send initial notification') {
            steps {
                slackSend channel: '#visetest', color: 'good', message: "STARTED: '${JOB_NAME} ${BUILD_NUMBER}' ",baseUrl:'https://cim.slack.com/services/hooks/jenkins-ci/',teamDomain:'cim',token:'ldhDuSiSkvnLEeFLUPyWndJF'
            }
        }
           // to delete old files in the system

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
         
               // to create temp work space
    
stage('create custom work space') 
{
steps
{
sh 'mkdir /tmp/visetest'  
echo 'custom work space is created'
}
}
  
 // Stage to check out the testcases from source repb
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
//  to execute fitnesse test cases
         
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
         //  to send  final slack notification
     stage('send final notification') {
            steps {
                slackSend channel: '#visetest', color: 'good', message: "COMPLETED: '${JOB_NAME} ${BUILD_NUMBER}' ",baseUrl:'https://cim.slack.com/services/hooks/jenkins-ci/',teamDomain:'cim',token:'ldhDuSiSkvnLEeFLUPyWndJF'
            }
        }
}
    //  post actions
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
