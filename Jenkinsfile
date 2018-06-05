
pipeline {
    agent any 
       //different stages are depicted here 
     stages {
     // Stage to check out the testcases from source repb
   
   stage('checkout test cases')
   {
    steps{
  echo 'checking out test cases'
dir('/users/bgurus001c/git/visetest') 
{  
checkout scm
} 
 }
}
   stage('prepare jar file')
   {
    steps{
  echo 'preparing jar file'
dir('/users/bgurus001c/git/visetest')
{
 sh 'javac *.java'
sh 'jar cvfe fitnesse-standalone.jar *.class' 
  }
 }
}
   stage('start the fitness server')
   {
    steps{
  echo 'start the fitness server'
dir('/users/bgurus001c/git/visetest')
{
sh 'java -jar fitnesse-standalone.jar -p 9090'
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
