def call(vipName, lvVersion) {
        echo 'Install the package '+ vipName
        bat 'labview-cli --kill --lv-ver ${lvVersion} L:\vipInstall.vi -- ${vipName} ${lvVersion}'
}
