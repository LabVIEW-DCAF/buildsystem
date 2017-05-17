def call(path, lv_version){
        echo 'Run basic tests before build'
        bat "labview-cli --kill --lv-ver ${lv_version} L:\\run-utf.vi -- \"${path}\" \"build_temp\\utf_results.xml\" \"${WORKSPACE}\""
}        
