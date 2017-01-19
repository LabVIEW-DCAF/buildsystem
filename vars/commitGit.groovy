def call(){
  echo 'Commit any changed files, but do not add any new files'
  bat 'git config --global user.email "mpollock@ni.com"'
  bat 'git config --global user.name "DCAF Build Server"'
  bat 'git commit -am "Auto-update files from build"'
}
