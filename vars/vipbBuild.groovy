def call(path, lvVersion) {
        echo "Build the package at ${path}"
        //bat "labview-cli --kill --lv-ver ${lvVersion} \"L:\\vipbBuild.vi\" -- \"${path}\" \"${WORKSPACE}\\build_temp\""  
        bat '"C:\\Program Files (x86)\\National Instruments\\LabVIEW 2014\\labview.exe" "C:\\Users\\Benjamin\\Desktop\\build.vi"'
        sleep(240)
}
