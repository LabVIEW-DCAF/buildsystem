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
      // If this change is a pull request and the DIFFING_ENABLED variable is set on the jenkins master, diff vis.
      if (env.CHANGE_ID && env.DIFFING_ENABLED) {
        stage ('Diff VIs'){
          echo 'Running LabVIEW diff build between origin/master and this commit' 
          def diffDir = "${WORKSPACE}\\diff_dir"
          bat "if exist ${diffDir} rd /s /q ${diffDir}"
          bat "mkdir ${diffDir}"
          bat "git difftool --no-prompt --extcmd=\"'L:\\labview.bat' \$LOCAL \$REMOTE diff_dir ${lvVersion}\" origin/master HEAD"
          // Silencing echo so as to not print out the token.
          bat "@python L:\\github_commenter.py --token=${GITHUB_DIFF_TOKEN} --pic-dir=${diffDir} --pull-req=${CHANGE_ID} --info=${JOB_NAME}"
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
          utfPaths.each{utfPath->
            echo 'UTF path: '+utfPath
            timeout(time: 30, unit: 'MINUTES'){
              utfTest(utfPath, lvVersion)  //Run tests on all projects    
            }
          }
        }

        stage ('VIPB_Build'){
          vipbPaths.each{vipbPath->
            echo 'VIPB version check'
            setVIPBuildNumber(vipbPath,'DCAF Unstable')
            echo 'VIPB path: '+vipbPath
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
     // }
        stage ('Post-Clean'){
          postClean()
        }    
    }
  }
}
