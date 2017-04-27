
import groovy.json.JsonOutput

def call(vipName, lvVersion) {

        echo 'Uninstall the package '+ vipName

        def vip_uninstall_json = JsonOutput.toJson(['VIP Name': "${vipName}","LabVIEW Version":"${lvVersion}"])

        echo vip_uninstall_json       

        def vip_uninstall_response = httpRequest validResponseCodes: "200,500", url:"http://localhost:8002/LabVIEWCIService/VIP_UNINSTALL?JSON="+java.net.URLEncoder.encode(vip_uninstall_json, "UTF-8").replaceAll("\\+", "%20")

        println("Status: "+vip_uninstall_response.status)

        println("Content: "+vip_uninstall_response.content)       
            if (vipb_uninstall_response.status!=200){
                error("Call to CI Server method VIP_UNINSTALL failed with error: "+vipb_uninstall_response.content)
            }
        echo 'Magic wait of 5 seconds...'

        sleep(5)

}
