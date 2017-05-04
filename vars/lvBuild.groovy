import groovy.json.JsonOutput

def call(path, target_name, spec_name) {
        echo 'Build the package'
        
        def lvBuild_json = JsonOutput.toJson([Executor_Number: env.EXECUTOR_NUMBER.toString(), Workspace_Path : env.WORKSPACE+'\\build_temp'], 'Build_Spec': ${spec_name},'Target':${target_name},'Project_Path': "${WORKSPACE}\\${path}","Auto_Version_Increment": true)
        echo lvBuild_json
        
        def lvb_response = httpRequest validResponseCodes: "200,500", url: "http://localhost:8002/LabVIEWCIService/LabVIEW_Build?JSON="+java.net.URLEncoder.encode(lvBuild_json, "UTF-8").replaceAll("\\+", "%20")
        println("Status: "+lvb_response.status)
        println("Content: "+lvb_response.content)
        if (vipb_response.status!=200){
                error("Call to CI Server method LabVIEW_Build failed with error: "+lvb_response.content)
            }
        echo 'Magic wait of 5 seconds...'
        sleep(5)
}
