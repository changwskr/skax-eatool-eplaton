@echo off
echo ========================================
echo SKAX EA Tool Eplaton - Package Migration
echo From: com.skax.eatool.mbc
echo To: com.skax.eatool
echo ========================================

echo.
echo Step 1: Creating new package directories...
if not exist "mbc-java\src\main\java\com\skax\eatool\mbc" (
    mkdir "mbc-java\src\main\java\com\skax\eatool\mbc"
    echo Created: mbc-java\src\main\java\com\skax\eatool\mbc
)

if not exist "mbc01-java\src\main\java\com\skax\eatool\mbc01" (
    mkdir "mbc01-java\src\main\java\com\skax\eatool\mbc01"
    echo Created: mbc01-java\src\main\java\com\skax\eatool\mbc01
)



echo.
echo Step 2: Package structure migration completed!
echo.
echo New package structure:
echo - com.skax.eatool.ksa (ksa-java, ksa-lib)
echo - com.skax.eatool.mbc (mbc-java)
echo - com.skax.eatool.mbc01 (mbc01-java)
echo.
echo Note: You need to manually move Java files from old package to new package
echo and update package declarations in each Java file.
echo.
pause 