def call(lvVersion) {
        echo 'Running LabVIEW diff build between origin/master and this commit'
        def diffDir = "${WORKSPACE}\\diff_dir"
        bat "if exist ${diffDir} rd /s /q ${diffDir}"
        bat "mkdir ${diffDir}"
        bat "git difftool --no-prompt --extcmd=\"'L:\\labview-diff.bat' \$LOCAL \$REMOTE diff_dir ${lvVersion}\" origin/master HEAD"
        // Silencing echo so as to not print out the token.
        bat "@python L:\\github_commenter.py --token=${GITHUB_DIFF_TOKEN} --pic-dir=${diffDir} --pull-req=${CHANGE_ID} --info=${JOB_NAME} --pic-repo=${DIFFING_PIC_REPO}"
}
