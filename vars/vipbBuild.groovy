def call(path, lvVersion) {
        echo "Build the package at ${path}"
        bat "labview-cli --kill \"L:\\vipbBuild.vi\" -- \"${path}\" \"${WORKSPACE}\""  
        //bat "labview-cli --kill \"L:\\build.vi\" -- \"${path}\" \"${WORKSPACE}\\build_temp\""
}
