def call(String repoUrl) {
   pipeline{
       agent any
       tools {
           maven 'Maven 3.5.0'
           jdk 'jdk8'
       }
       environment {
           LAMBDA_TEST_CRED = credentials('f9123f1b-ffc7-44d1-a4a4-0ec93734e9f8')
       }
       stages {
           stage("Tools initialization") {
               steps {
                       sh "mvn --version"
                       sh "java -version"
                   }
               }
           stage("Checkout Code") {
               steps {
                   git branch: 'main',
                   url: "${repoUrl}"
               }
           }
           stage("Running Testcase") {
               steps {
                   sh "mvn -Dusername=${LAMBDA_TEST_CRED_USR} -DaccessKey=${LAMBDA_TEST_CRED_PSW} test"
               }
           }
       }
   }
}
