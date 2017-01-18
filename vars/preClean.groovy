def call(){
    echo 'Cleaning out workspace  directory: '+env.WORKSPACE
    deleteDir()
    mkdir build_temp
}
