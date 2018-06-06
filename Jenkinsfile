
pipeline {
    agent any 
       //different stages are depicted here 
     stages {
     // Stage to check out the testcases from source repb
 
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
  echo 'fitness server is started'
    emailext body: 'A Test EMail', subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test', to: 'Balachandar_gurusamy@cable.comcast.com', body: """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
       <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>"""

}
  }
}
    
}
    
   
    
    
    
   // post
   // {
       // always
       // {
         //   mail to: 'Balachandar_gurusamy@cable.comcast.com',
              // subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      //body: """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
     //  <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>"""
      
 
     //   }
        
       // always {
         //   step([$class: 'Mailer',
                 //notifyEveryUnstableBuild: true,
              //  recipients: "Balachandar_gurusamy@cable.comcast.com",
               // sendToIndividuals: true])
       // }
    //}
}
