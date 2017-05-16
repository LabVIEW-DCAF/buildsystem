def call(path, lvVersion) {
        echo 'Build the package at ${path} for LabVIEW ${lvVersion}'
        bat 'labview-cli --kill --lv-ver ${lvVersion} L:\vipBuild.vi -- ${path} ${lvVersion}'      
}
