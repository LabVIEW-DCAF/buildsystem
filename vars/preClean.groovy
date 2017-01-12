def call(){
    echo 'Cleaning out workspace temp directory: '+env.WORKSPACE+'\\build_temp'
    bat '''IF EXIST build_temp (
    rmdir /s /q build_temp
    )
    mkdir build_temp'''
}
