
pipeline {
    agent any 
       //different stages are depicted here 
     stages {
     // Stage to check out the testcases from source repb
   
   stage('checkout test cases')
   {
    steps{
//sh 'chmod 777 /tmp'
sh 'mkdir /tmp/visetest1'  
echo 'checking out test cases'
dir('/tmp/visetest1') 
{  
checkout scm
sh 'java -jar Fitnesse/fitnesse-standalone.jar -p 9090'
} 
 }
}

   stage('start the fitness server')
   {
    steps{
  echo 'start the fitness server'
dir('/tmp/visetest1')
{
sh 'java -jar Fitnesse/fitnesse-standalone.jar -p 9090'
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
