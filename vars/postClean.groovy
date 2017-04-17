def call(){
    echo 'Archiving all artifacts in the build-temp directory'
    archiveArtifacts artifacts: 'build-temp/**'
    echo 'Cleaning out workspace directory'
    deleteDir()
}
