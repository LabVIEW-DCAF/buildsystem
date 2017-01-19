def call(vipbPath){
  echo 'Commit only the VIPB such that the build number is correctly updated between builds, even if the build machine is lost'
  bat 'git config --global user.email "mpollock@ni.com"'
  bat 'git config --global user.name "DCAF Build Server"'
  bat 'git commit -m "Auto-update files from build" '+'"'+vipbPath+'"'
  bat 'git push origin HEAD:master'
}
