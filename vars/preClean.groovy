def call(){
    echo 'Cleaning out workspace  directory: '+env.WORKSPACE
    bat 'dir'
    deleteDir()
    bat '''mkdir build_temp'''
    bat 'dir'
}
