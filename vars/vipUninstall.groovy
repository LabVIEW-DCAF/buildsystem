def call(vipName, lvVersion) {
        echo 'Uninstall the package '+ vipName
        bat "labview-cli --kill --lv-ver ${lvVersion} \"L:\\vipUninstall\" -- \"${vipName}\" ${lvVersion}"
}
