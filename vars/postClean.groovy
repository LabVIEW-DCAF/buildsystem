def call(){
    echo 'Kill VIPM so it cannot hold onto any file or folder handles'
    bat 'taskkill /IM "VI Package Manager.exe" /F /FI "STATUS eq RUNNING"'
    echo 'List everything in the build-temp directory (debug line to be removed)'
    bat 'dir .\\build_temp'
    echo 'Archiving all artifacts in the build-temp directory'
    archiveArtifacts artifacts: 'build_temp/**'
    echo 'Cleaning out workspace directory'
    deleteDir()
}
