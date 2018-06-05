
pipeline {
    agent any 
       //different stages are depicted here 
     stages {
     // Stage to check out the testcases from source repb
   
   stage('checkout test cases')
   {
    steps{
sh 'mkdir /users/bgurus001c/git/visetest1'  
echo 'checking out test cases'
dir('/users/bgurus001c/git/visetest1') 
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
dir('/users/bgurus001c/git/visetest1')
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
