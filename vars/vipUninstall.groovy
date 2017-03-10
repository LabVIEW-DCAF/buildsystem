
import groovy.json.JsonOutput

def call(vipName, lvVersion) {

        echo 'Uninstall the package '+ vipName

        def vip_uninstall_json = JsonOutput.toJson(['VIP Name': "${vipName}","LabVIEW Version":"${lvVersion}"])

        echo vip_uninstall_json       

        def vip_uninstall_response = httpRequest "http://localhost:8002/LabVIEWCIService/VIP_UNINSTALL?JSON="+java.net.URLEncoder.encode(vip_uninstall_json, "UTF-8").replaceAll("\\+", "%20")

        println("Status: "+vip_uninstall_response.status)

        println("Content: "+vip_uninstall_response.content)       

        echo 'Magic wait of 5 seconds...'

        sleep(5)

}
