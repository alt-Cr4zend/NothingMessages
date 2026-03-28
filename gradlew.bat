@rem Gradle startup script for Windows
@echo off
setlocal
set DIRNAME=%~dp0
set GRADLE_OPTS=%GRADLE_OPTS% -Dfile.encoding=UTF-8
call "%DIRNAME%\gradle\wrapper\gradle-wrapper.jar" %* 2>nul || gradle %*
