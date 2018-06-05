
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

   stage('start the fitness server')
   {
    steps{
dir('/tmp/visetest1')
{
sh 'java -jar Fitnesse/fitnesse-standalone.jar -p 9090'
  echo 'fitness server is started'
}
  }
}
   stage('execute the  test cases')
   {
    steps{
  echo 'checkout test cases'
   checkout scm
  }
}
}
}
