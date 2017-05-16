def call(path, lv_versison){
        echo 'Run basic tests before build'
        bat 'labview-cli --kill --lv-ver ${lv_version} L:\run_utf.vi -- ${path}'
}        
       
