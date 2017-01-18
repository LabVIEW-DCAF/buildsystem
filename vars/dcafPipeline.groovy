#!/usr/bin/env groovy

//note: this script assumes that it will be invoked from another script after that script has defined the necessary parameters

//These are: utfPath, vipbPath, vipbInstall, lvVersion, repoName

//This script further assumes that Jenkins is configured (via the Pipeline Shared Libraries plugin) to implicitly include https://github.com/LabVIEW-DCAF/buildsystem

def call(utfPath,vipbPath,lvVersion,repoName){

  node {
    bat 'dir'
      echo 'Starting build...'
      stage ('Pre-Clean'){
        preClean()
      }

      stage ('UTF'){
        bat 'dir'
          //utfTest(utfPath)    
      }

      stage ('VIPB_Build'){
        //vipbBuild(vipbPath,lvVersion)
        bat 'dir'
        def workspace_files = bat returnStdout: true, script: 'dir'
        echo workspace_files
      }

      stage ('VIP_Deploy'){
        bat 'dir'
        vipPublish(repoName)
      }

      stage ('Post-Clean'){
        bat 'dir'
        postClean()
      }    
  }
}
