def call(vipbPath, vipmRepo){
  echo "Calculating next build number for package at ${vipbPath}..."
  bat "labview-cli --kill \"L:\\setVipBuildNumber.vi\" -- \"${vipbPath}\" \"${WORKSPACE}\" \"${BUILD_NUMBER}\" \"${vipmRepo}\""
}
