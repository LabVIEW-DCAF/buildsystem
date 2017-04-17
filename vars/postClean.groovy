def call(){
    echo 'List everything in the build-temp directory (debug line to be removed)'
    bat 'dir .\\build_temp'
    echo 'Archiving all artifacts in the build-temp directory'
    archiveArtifacts artifacts: 'build_temp/**'
    echo 'Cleaning out workspace directory'
    deleteDir()
}
