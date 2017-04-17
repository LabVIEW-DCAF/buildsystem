def call(){
    echo 'Checking for commits that were not made by the build system' 
    MAX_MSG_LEN = 100
    def internalChangesOnly = true
    def changeString = ""

    echo "Gathering SCM changes"
    def changeLogSets = currentBuild.rawBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            truncated_msg = entry.msg.take(MAX_MSG_LEN)
            if truncated_msg != "Auto-update files from build, ignore this commit"{
                internalChangesOnly=false
            }
            changeString += " - ${truncated_msg} [${entry.author}]\n"
        }
    }

    if (!changeString) {
        changeString = " - No new changes"
        internalChangesOnly = false
    }
    echo changeString
}
