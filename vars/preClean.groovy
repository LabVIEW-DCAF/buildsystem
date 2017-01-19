def call(){
    echo 'Cleaning out workspace  directory: '+env.WORKSPACE
    deleteDir()
}
