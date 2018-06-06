
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
//sh 'java -jar Fitnesse/fitnesse-standalone.jar -p 9090'
     timeout(5) {
    waitUntil {
       script {
         def r = sh script: 'java -jar Fitnesse/fitnesse-standalone.jar -p 9090', returnStatus: true
         return (r == 0);
       }
    }
}
  echo 'fitness server is started'
}
  }
}
    
}
    
   
    
    
    
    post
    {
        always
        {
            mail to: 'Balachandar_gurusamy@cable.comcast.com',
                 subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      body: """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
        <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>"""
      
 
        }
    }
}
