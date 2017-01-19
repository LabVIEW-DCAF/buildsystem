def call(){
  echo 'Commit any changed files, but do not add any new files'
  bat 'git commit -am "Auto-update files from build"'
}
