#!/usr/bin/env groovy

//note: this script assumes that it will be invoked from another script after that script has defined the necessary parameters

//These are: utfPath, vipbPath, lvVersion

//This script further assumes that Jenkins is configured (via the Pipeline Shared Libraries plugin) to implicitly include https://github.com/LabVIEW-DCAF/buildsystem

def call(utfPaths,vipbPaths,lvVersion){

switch(lvVersion){  //This is to abstract out the different Jenkinsfile conventions of setting version to 14.0 instead of 2014.
  case "14.0":
    lvVersion="2014"
    break
  case "15.0":
    lvVersion="2015"
    break
  case "16.0":
    lvVersion="2016"
    break
  case "17.0":
    lvVersion="2017"
    break
}

def continueBuild
  node(lvVersion){
      echo 'Starting build...'
      stage ('Pre-Clean'){
        preClean()
      }
      stage ('SCM_Checkout'){
        echo 'Attempting to get source from repo...'
        timeout(time: 5, unit: 'MINUTES'){
          checkout scm
        }
      }
      
      stage ('Check Preconditions for Build'){
        continueBuild=checkCommits()
      }
    if(continueBuild){
        stage ('Temp Directories'){
          bat 'mkdir build_temp'
        }
        stage ('UTF'){          
          for(int i=0; i < utfPaths.size(); i++){
            
            //Iterate through each item in utfPaths array
            utfPath = utfPaths[i]
            echo 'UTF Project Path: ' +utfPath
            
            //Run unit tests for specified project with 30 minute timeout
            timeout(time: 30, unit: 'MINUTES'){
              utfTest(utfPath, lvVersion)    
            }
          }  
        }


      
      stage ('VIPB_Build'){
          for(int j=0; j < vipbPaths.size(); j++){
            
            //Iterate through each item in vipbPaths array
            vipbPath = vipbPaths[j]
            echo 'VIPB version check'
            setVIPBuildNumber(vipbPath,'DCAF Unstable')
            echo 'VIPB path: '+vipbPath
            
            //Start VI Package build with 60 minute timeout
            timeout(time: 60, unit: 'MINUTES'){
              vipbBuild(vipbPath,lvVersion)
            }
          }
        }
        if (env.BRANCH_NAME == 'master') { // Only deploy if on the master branch.
          stage ('VIP_Deploy'){
            timeout(time: 10, unit: 'MINUTES'){
              vipPublish('DCAF Unstable')
            }
          }
        }
      //stage ('SCM commit'){
      //  vipbPaths.each{vipbPath->
      //    commitPackageToGit(vipbPath)
      //  }
      //}
      
            // If this change is a pull request and the DIFFING_PIC_REPO variable is set on the jenkins master, diff vis.
      if (env.CHANGE_ID && env.DIFFING_PIC_REPO) {
        stage ('Diff VIs'){
          try {
            timeout(time: 60, unit: 'MINUTES') {
              lvDiff(lvVersion)
              echo 'Diff Succeeded!'
            }
          } catch (err) {
            currentBuild.result = "SUCCESS"
            echo "Diff Failed: ${err}"
          }  
        }
      }
      
        stage ('Post-Clean'){
          postClean()
        }    
    }
  }
}
