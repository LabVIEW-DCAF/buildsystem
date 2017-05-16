def call(path, lvVersion) {
        echo 'Build the package at ${path}'
        bat 'labview-cli --kill --lv-ver ${lvVersion} "L:\vipbBuild.vi" -- ${path} ${WORKSPACE}'      
}
