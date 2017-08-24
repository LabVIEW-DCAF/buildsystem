@SET vi1=%~f1
@SET vi2=%~f2
@SET working_dir=%~f3
@SET lv_version=%4

@REM only run if both files are VIs.

@if "%vi1:~-3%" == ".vi" (
    if "%vi2:~-3%" == ".vi" GOTO :DIFF_VI
)
GOTO :END

:DIFF_VI
    labview-cli --kill --lv-ver %lv_version% L:\lvDiff.vi -- "%vi1%" "%vi2%" "%working_dir%"
:END