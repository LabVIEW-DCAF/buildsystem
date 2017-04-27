

import groovy.json.JsonOutput

def call(repoName){
        echo 'Push the package to the internal repo'
        echo 'Scan build_temp directory for *.vipb pattern, call the web service each time it finds one.'



        def files = findFiles(glob: 'build_temp\\*.vip')

        echo "Found "+files.length+" VI Package files."

        if(files.length){
//make this a loop across all items in the files[] array.
                files.each{vip_file->
                        echo """${vip_file.name}

                                ${vip_file.path}

                                ${vip_file.directory}

                                ${vip_file.length}

                                ${vip_file.lastModified}"""
                        def vip_json = JsonOutput.toJson(['VIP Path': env.WORKSPACE+"\\"+vip_file.path,"Repo Name":"${repoName}"])
                        echo vip_json

                        def vip_response = httpRequest validResponseCodes: "200,500", url:"http://localhost:8002/LabVIEWCIService/VIP_Publish?JSON="+java.net.URLEncoder.encode(vip_json, "UTF-8").replaceAll("\\+", "%20")
                        println("Status: "+vip_response.status)
                        println("Content: "+vip_response.content)
                        if (vip_response.status!=200){
                                error("Call to CI Server method VIP_Publish failed with error: "+vip_response.content)
                        }
                }
        }
        else{
                echo 'No VIP files found.'
        }
        echo 'Magic sleep to let LabVIEW breathe'
        sleep(5)
}        
