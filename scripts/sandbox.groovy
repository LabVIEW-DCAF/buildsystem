#!/usr/bin/env/ groovy

import groovy.json.JsonOutput

node {
    echo 'Starting build...'
    stage ('Pre-Clean'){
        echo 'Cleaning out workspace temp directory: '+env.WORKSPACE+'\\build_temp'
bat '''IF EXIST build_temp (
rmdir /s /q build_temp
)
mkdir build_temp'''
    }
   stage ('checkout'){
        echo 'get code from github'
   }

    stage ('Dependencies'){
        echo 'Install dependencies of this build from internal repo'
    }
    
    stage ('UTF'){
        echo 'Run basic tests before build'
        def utf_json = JsonOutput.toJson(['Project Path': 'C:\\Users\\Benjamin\\Documents\\GitHub\\buildtest\\source\\write.lvproj', Executor_Number: env.EXECUTOR_NUMBER.toString(), 'Workspace_Path': env.WORKSPACE+'\\build_temp'])
        echo utf_json
        
        def utf_response = httpRequest "http://localhost:8002/LabVIEWCIService/Run_UTF?JSON="+java.net.URLEncoder.encode(utf_json, "UTF-8").replaceAll("\\+", "%20")
        println("Status: "+utf_response.status)
        println("Content: "+utf_response.content)
        
        echo 'Junit'
        junit allowEmptyResults: true, testResults: 'build_temp\\utf_results.xml'

        echo 'Magic delay of 5 seconds to let LabVIEW breathe'
        sleep(5)
        echo 'Done waiting.'
    }
    
    stage ('VIPB_Build'){
        echo 'Build the package'
        
        def vipb_json = JsonOutput.toJson(['VIPB Path': 'C:\\Users\\Benjamin\\Documents\\GitHub\\buildtest\\Build Test.vipb',"Install after build": false, "LabVIEW Version":"14.0", Executor_Number: env.EXECUTOR_NUMBER.toString(), Workspace_Path : env.WORKSPACE+'\\build_temp'])
        echo vipb_json
        
        def vipb_response = httpRequest "http://localhost:8002/LabVIEWCIService/VIPB_Build?JSON="+java.net.URLEncoder.encode(vipb_json, "UTF-8").replaceAll("\\+", "%20")
        println("Status: "+vipb_response.status)
        println("Content: "+vipb_response.content)
        
        echo 'Magic wait of 5 seconds...'
        sleep(5)
    }
    stage ('VIP_Deploy'){
        echo 'Push the package to the internal repo'
        echo 'Scan build_temp directory for *.vipb pattern, call the web service each time it finds one.'



        def files = findFiles(glob: 'build_temp\\*.vip')

        echo """${files[0].name}

                ${files[0].path}

                ${files[0].directory}

                ${files[0].length}

                ${files[0].lastModified}"""


        def vip_json = JsonOutput.toJson(['VIP Path': env.WORKSPACE+"\\"+files[0].path,"Repo Name":"SE_Test"])
        echo vip_json
        
        def vip_response = httpRequest "http://localhost:8002/LabVIEWCIService/VIP_Publish?JSON="+java.net.URLEncoder.encode(vip_json, "UTF-8").replaceAll("\\+", "%20")
        println("Status: "+vip_response.status)
        println("Content: "+vip_response.content)
    }
    
    stage ('Post-Clean'){
        echo 'Cleaning out workspace temp directory'
        bat '''IF EXIST build_temp (
            rmdir /s /q build_temp
        )'''
    }
    
}
