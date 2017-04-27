import groovy.json.JsonOutput

def call(path){
        echo 'Run basic tests before build'
        def utf_json = JsonOutput.toJson(['Project Path': "${WORKSPACE}\\${path}", Executor_Number: env.EXECUTOR_NUMBER.toString(), 'Workspace_Path': env.WORKSPACE+'\\build_temp'])
        echo utf_json
        
        def utf_response = httpRequest validResponseCodes: "200,500", url:"http://localhost:8002/LabVIEWCIService/Run_UTF?JSON="+java.net.URLEncoder.encode(utf_json, "UTF-8").replaceAll("\\+", "%20")
        println("Status: "+utf_response.status)
        println("Content: "+utf_response.content)
        if (vipb_response.status!=200){
                error("Call to CI Server method Run_UTF failed with LabVIEW error: "+vipb_response.content)
            }
        echo 'Junit'
        junit allowEmptyResults: true, testResults: 'build_temp\\utf_results.xml'

        echo 'Magic delay of 5 seconds to let LabVIEW breathe'
        sleep(5)
        echo 'Done waiting.'
}        
       
