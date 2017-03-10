
import groovy.json.JsonOutput

def call(lvVersion) {

        echo 'Install any updates to LabVIEW '+ lvVersion

        def vipm_update_json = JsonOutput.toJson(["LabVIEW Version":"${lvVersion}"])

        echo vipm_update_json       

        def vipm_update_response = httpRequest "http://localhost:8002/LabVIEWCIService/VIPM_UPDATE?JSON="+java.net.URLEncoder.encode(vipm_update_json, "UTF-8").replaceAll("\\+", "%20")

        println("Status: "+vipm_update_response.status)

        println("Content: "+vipm_update_response.content)       

        echo 'Magic wait of 5 seconds...'

        sleep(5)

}
