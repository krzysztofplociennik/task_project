call runcrud
if "%ERRORLEVEL%" == "0" goto openlink
echo.
echo runcrud caused some errors.
goto fail

:openlink
set url="http://localhost:8080/crud/v1/task/getTasks"
start firefox.exe %url%
goto end

:fail
echo.
echo showtasks failed.

:end
echo.
echo showtasks worked successfully.