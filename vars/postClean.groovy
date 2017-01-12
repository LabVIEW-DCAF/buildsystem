def call(){
    echo 'Cleaning out workspace temp directory'
    bat '''IF EXIST build_temp (
        rmdir /s /q build_temp
    )'''
}
