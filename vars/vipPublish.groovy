def call(repoName){
        echo 'Push the package to the internal repo'
        echo 'Scan build_temp directory for *.vipb pattern, publish each one it finds'

        def files = findFiles(glob: 'build_temp\\*.vip')

        echo "Found "+files.length+" VI Package files."

        if(files.length){
//make this a loop across all items in the files[] array.
                files.each{vip_file->
                        echo """${vip_file.name}

                                ${vip_file.path}

                                ${vip_file.directory}

                                ${vip_file.length}

                                ${vip_file.lastModified}"""
                        bat 'labview-cli --kill L:\vipPublish.vi -- ${repoName}'
                }
        }
        else{
                echo 'No VIP files found.'
        }
}        
