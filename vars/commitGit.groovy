def call(vipbPath){
  echo 'Commit only the VIPB such that the build number is correctly updated between builds, even if the build machine is lost'
  
 withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'DCAF-Builder', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {
    bat 'git config user.name'
    bat 'git config user.email'
   echo env.GIT_BRANCH
    bat 'git config --global user.email "mpollock@ni.com"'
    bat 'git config --global user.name "DCAF Build Server"'
    bat 'git commit -m "Auto-update files from build" '+'"'+vipbPath+'"'
   bat "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${GIT_URL} HEAD:master"
 }
}
