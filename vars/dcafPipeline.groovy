#!/usr/bin/env groovy

//note: this script assumes that it will be invoked from another script after that script has defined the necessary parameters

//These are: utfPath, vipbPath, vipbInstall, lvVersion, repoName

//This script further assumes that Jenkins is configured (via the Pipeline Shared Libraries plugin) to implicitly include https://github.com/LabVIEW-DCAF/buildsystem

def call(utfPath,vipbPath,lvVersion,repoName){

  node ('Alfred'){
        //bat 'dir'
        echo 'Starting build...'
      stage ('Pre-Clean'){
        preClean()
      }
      stage ('SCM_Checkout'){
        echo 'Attempting to get source from repo...'
        checkout scm
        //bat 'dir'
      }
      stage ('UTF'){
        //bat 'dir'
          utfTest(utfPath)    
      }

      stage ('VIPB_Build'){
        vipbBuild(vipbPath,lvVersion)
        //bat 'dir'
      }

      stage ('VIP_Deploy'){
        //bat 'dir'
        vipPublish(repoName)
      }
    stage ('SCM commit'){
      commitGit()
    }
      stage ('Post-Clean'){
        //bat 'dir'
        //postClean()
        echo 'skipping post clean for debug purposes.'
      }    
  }
}
