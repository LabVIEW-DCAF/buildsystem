def call(){
  //This is a test to determine if the mailer works correctly by forcing an unstable build.
  currentBuild.result = 'FAILURE'
  step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: emailextrecipients([[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']])])
}
