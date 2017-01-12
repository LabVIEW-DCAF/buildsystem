#!/usr/bin/env groovy
//note: this script assumes that it will be invoked from another script after that script has defined the necessary parameters
//These are: utfPath, vipbPath, vipbInstall, lvVersion, repoName
//This script further assumes that Jenkins is configured (via the Pipeline Shared Libraries plugin) to implicitly include https://github.com/LabVIEW-DCAF/buildsystem
import groovy.json.JsonOutput

node {
    echo 'Starting build...'
    stage ('Pre-Clean'){
      preClean()
    }

    stage ('UTF'){
        utfTest(utfPath)    
    }
    
    stage ('VIPB_Build'){
      vipbBuild(vipbPath,lvVersion)
    }
    
    stage ('VIP_Deploy'){
      vipPublish(repoName)
    }
    
    stage ('Post-Clean'){
      postClean()
    }    
}
