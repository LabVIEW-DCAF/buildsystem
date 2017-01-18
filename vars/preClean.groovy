def call(){
    echo 'Cleaning out workspace  directory: '+env.WORKSPACE
    deleteDir()
    bat '''mkdir build_temp'''
}
