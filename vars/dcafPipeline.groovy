#!/usr/bin/env groovy

//note: this script assumes that it will be invoked from another script after that script has defined the necessary parameters

//These are: utfPath, vipbPath, lvVersion

//This script further assumes that Jenkins is configured (via the Pipeline Shared Libraries plugin) to implicitly include https://github.com/LabVIEW-DCAF/buildsystem

def call(utfPaths,vipbPaths,lvVersion){
def continueBuild
  node{
        echo 'Starting build...'
      stage ('Pre-Clean'){
        preClean()
      }
      stage ('SCM_Checkout'){
        echo 'Attempting to get source from repo...'
        checkout scm
      }
        stage ('Check Preconditions for Build'){
        continueBuild=checkCommits()
      }
    if(continueBuild){
        stage ('Temp Directories'){
          bat 'mkdir build_temp'
        }
        stage ('UTF'){
          utfPaths.each{utfPath->
            echo 'UTF path: '+utfPath
            utfTest(utfPath, lvVersion)  //Run tests on all projects    
          }
        }

        stage ('VIPB_Build'){
          vipbPaths.each{vipbPath->
            echo 'VIPB path: '+vipbPath
            vipbBuild(vipbPath,lvVersion)
          }
        }

        stage ('VIP_Deploy'){
          vipPublish('DCAF Unstable')
        }
      //stage ('SCM commit'){
      //  vipbPaths.each{vipbPath->
      //    commitPackageToGit(vipbPath)
      //  }
     // }
        stage ('Post-Clean'){
          postClean()
        }    
    }
  }
}
