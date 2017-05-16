def call(lvVersion) {
        echo 'Install any updates to LabVIEW '+ lvVersion
        bat 'labview-cli --kill --lv-ver ${lvVersion} "L:\\vipmUpdate.vi" -- ${lvVersion}'
}
