import groovy.json.JsonOutput

def call(path, lvVersion) {
        echo 'Build the package'
        
        def vipb_json = JsonOutput.toJson(['VIPB Path': "${WORKSPACE}\\${path}","Install after build": false, "LabVIEW Version":"${lvVersion}", Executor_Number: env.EXECUTOR_NUMBER.toString(), Workspace_Path : env.WORKSPACE+'\\build_temp'])
        echo vipb_json
        
        def vipb_response = httpRequest validResponseCodes: "200,500", url: "http://localhost:8002/LabVIEWCIService/VIPB_Build?JSON="+java.net.URLEncoder.encode(vipb_json, "UTF-8").replaceAll("\\+", "%20")
        println("Status: "+vipb_response.status)
        println("Content: "+vipb_response.content)
        if (vipb_response.status!=200){
                error("Call to CI Server method VIPB_Build failed with error: "+vipb_response.content)
            }
        echo 'Magic wait of 5 seconds...'
        sleep(5)
}
