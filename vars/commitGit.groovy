def call(vipbPath){
  echo 'Commit only the VIPB such that the build number is correctly updated between builds, even if the build machine is lost'
  
 withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'DCAF-Builder', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {
    bat 'git config user.name'
    bat 'git config user.email'
    
   echo '-------------------'
   bat 'git rev-parse --abbrev-ref HEAD > GIT_BRANCH'
    git_branch = readFile('GIT_BRANCH').trim()
    echo git_branch
   echo '--------------------'
   //These lines are commented out because they were only needed once to set up the build environment.  They needed to be called from script because script runs as a different user, and I couldn't be arsed to find the right spot for it globally for all users.
   // bat 'git config --global user.email "mpollock@ni.com"'
   // bat 'git config --global user.name "DCAF Build Server"'
    bat 'git commit -m "Auto-update files from build" '+'"'+vipbPath+'"'
    def git_remote_url=bat returnStdout: true, script: '@git remote get-url origin'
   echo "Remote_URL raw: "+git_remote_url
   git_remote_url=git_remote_url.trim()     //get rid of any trailing end of line characters
   git_remote_url=git_remote_url.drop(8)    //Drop first 8 characters, which in our case are the https:// chars.  This might break if we change access modes.
   bat "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${git_remote_url} ${git_branch)"
   //Note: need to replace master in above line with actual branch.  Currently git environment variables are not populated in pipeline scripts, so need to find another way from command line.
 }
}
