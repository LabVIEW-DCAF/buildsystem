
import groovy.json.JsonOutput

def call(vipName, lvVersion) {

        echo 'Install the package '+ vipName

        def vip_install_json = JsonOutput.toJson(['VIP Name': "${vipName}","LabVIEW Version":"${lvVersion}"])

        echo vip_install_json       

        def vip_install_response = httpRequest validResponseCodes: "200,500", url: "http://localhost:8002/LabVIEWCIService/VIP_INSTALL?JSON="+java.net.URLEncoder.encode(vip_install_json, "UTF-8").replaceAll("\\+", "%20")

        println("Status: "+vip_install_response.status)

        println("Content: "+vip_install_response.content)       
            if (vip_install_response.status!=200){
                error("Call to CI Server method VIP_INSTALL failed with error: "+vip_response.content)
            }
        echo 'Magic wait of 5 seconds...'

        sleep(5)

}
