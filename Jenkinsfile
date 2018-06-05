
pipeline {
    agent any 
       //different stages are depicted here 
     stages {
     // Stage to check out the testcases from source repb
   
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
dir('/tmp/visetest1')
{
sh 'java -jar Fitnesse/fitnesse-standalone.jar -p 9090'
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
          subject: $PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS
          body: $PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:

        }
    }
}
