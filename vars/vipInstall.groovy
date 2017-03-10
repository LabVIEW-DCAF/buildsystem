
import groovy.json.JsonOutput

def call(vipName, lvVersion) {

        echo 'Install the package '+ vipName

        def vip_install_json = JsonOutput.toJson(['VIP Name': "${vipName}","LabVIEW Version":"${lvVersion}"])

        echo vip_install_json       

        def vip_install_response = httpRequest "http://localhost:8002/LabVIEWCIService/VIP_INSTALL?JSON="+java.net.URLEncoder.encode(vip_install_json, "UTF-8").replaceAll("\\+", "%20")

        println("Status: "+vip_install_response.status)

        println("Content: "+vip_install_response.content)       

        echo 'Magic wait of 5 seconds...'

        sleep(5)

}
