@echo off
echo ========================================
echo Current Versions Information
echo ========================================
echo.

echo Maven Repository:
echo ----------------
if exist "%USERPROFILE%\.m2\repository\com\skax\eatool\ksa-java" (
    dir /b /o-n "%USERPROFILE%\.m2\repository\com\skax\eatool\ksa-java" | findstr /v "maven-metadata" | findstr /v "resolver-status" > temp_versions.txt
    if exist "temp_versions.txt" (
        for /f "delims=" %%i in ('type temp_versions.txt ^| findstr /r ".*" ^| tail -1') do echo ksa-java: %%i
        del temp_versions.txt
    )
)

if exist "%USERPROFILE%\.m2\repository\com\skax\eatool\kji-java" (
    dir /b /o-n "%USERPROFILE%\.m2\repository\com\skax\eatool\kji-java" | findstr /v "maven-metadata" | findstr /v "resolver-status" > temp_versions.txt
    if exist "temp_versions.txt" (
        for /f "delims=" %%i in ('type temp_versions.txt ^| findstr /r ".*" ^| tail -1') do echo kji-java: %%i
        del temp_versions.txt
    )
)

if exist "%USERPROFILE%\.m2\repository\com\skax\eatool\mbc01-java" (
    dir /b /o-n "%USERPROFILE%\.m2\repository\com\skax\eatool\mbc01-java" | findstr /v "maven-metadata" | findstr /v "resolver-status" > temp_versions.txt
    if exist "temp_versions.txt" (
        for /f "delims=" %%i in ('type temp_versions.txt ^| findstr /r ".*" ^| tail -1') do echo mbc01-java: %%i
        del temp_versions.txt
    )
)

echo.
echo Runtime Directories:
echo -------------------
if exist "ksa-lib\runtime" (
    dir /b /o-n "ksa-lib\runtime\*.jar" > temp_runtime.txt 2>nul
    if exist "temp_runtime.txt" (
        for /f "delims=" %%i in ('type temp_runtime.txt ^| findstr /r ".*" ^| tail -1') do echo ksa-lib/runtime: %%i
        del temp_runtime.txt
    )
)

if exist "kji-lib\runtime" (
    dir /b /o-n "kji-lib\runtime\*.jar" > temp_runtime.txt 2>nul
    if exist "temp_runtime.txt" (
        for /f "delims=" %%i in ('type temp_runtime.txt ^| findstr /r ".*" ^| tail -1') do echo kji-lib/runtime: %%i
        del temp_runtime.txt
    )
)

if exist "mbc01-lib\runtime" (
    dir /b /o-n "mbc01-lib\runtime\*.jar" > temp_runtime.txt 2>nul
    if exist "temp_runtime.txt" (
        for /f "delims=" %%i in ('type temp_runtime.txt ^| findstr /r ".*" ^| tail -1') do echo mbc01-lib/runtime: %%i
        del temp_runtime.txt
    )
)

echo.
echo ========================================
echo.
pause 