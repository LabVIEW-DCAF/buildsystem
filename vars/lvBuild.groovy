import groovy.json.JsonOutput

def call(path, target_name, spec_name, lv_version) {
        echo 'Build the package'
        
        bat 'labview-cli --kill --lv-ver ${lv_version} L:\lv_build.vi -- ${path} ${target_name} ${spec_name}'
}
