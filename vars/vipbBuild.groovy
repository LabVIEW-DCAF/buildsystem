def call(path, lvVersion, buildNumber) {
        echo "Build the package at ${path}"
        bat "labview-cli --kill \"L:\\vipbBuild.vi\" -- \"${path}\" \"build_temp\" \"${WORKSPACE}\" ${buildNumber}"  
        //bat "labview-cli --kill \"L:\\build.vi\" -- \"${path}\" \"${WORKSPACE}\\build_temp\""
}
