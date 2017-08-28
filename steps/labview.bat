@SET vi1=%~f1
@SET vi2=%~f2
@SET working_dir=%~f3
@SET lv_version=%4

@REM only run diff if both files are VIs or there was a newly added VI

@if "%vi1:~-3%" == ".vi" GOTO :VI2Check
@if "%vi1%" == "\\.\nul" GOTO :VI2Check
@GOTO :END

:VI2Check
@if "%vi2:~-3%" == ".vi" GOTO :DIFF_VI

:DIFF_VI
    labview-cli --kill --lv-ver %lv_version% L:\lvDiff.vi -- "%vi1%" "%vi2%" "%working_dir%"
:END