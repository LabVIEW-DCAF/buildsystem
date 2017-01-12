import groovy.json.JsonOutput

def call(repoName){
        echo 'Push the package to the internal repo'
        echo 'Scan build_temp directory for *.vipb pattern, call the web service each time it finds one.'



        def files = findFiles(glob: 'build_temp\\*.vip')

        echo """${files[0].name}

                ${files[0].path}

                ${files[0].directory}

                ${files[0].length}

                ${files[0].lastModified}"""

//make this a loop across all items in the files[] array.
        def vip_json = JsonOutput.toJson(['VIP Path': env.WORKSPACE+"\\"+files[0].path,"Repo Name":"${repoName}"])
        echo vip_json
        
        def vip_response = httpRequest "http://localhost:8002/LabVIEWCIService/VIP_Publish?JSON="+java.net.URLEncoder.encode(vip_json, "UTF-8").replaceAll("\\+", "%20")
        println("Status: "+vip_response.status)
        println("Content: "+vip_response.content)
        
        echo 'Magic sleep to let LabVIEW breathe'
        sleep(5)
}        
