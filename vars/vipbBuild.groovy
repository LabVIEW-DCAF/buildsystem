def call(path, lvVersion) {
        echo "Build the package at ${path}"
        //bat "labview-cli --kill --lv-ver ${lvVersion} \"L:\\vipbBuild.vi\" -- \"${path}\" \"${WORKSPACE}\\build_temp\""  
        bat 'labview-cli --kill "C:\\Users\\Benjamin\\Desktop\\build.vi"'
}
