@echo off
set /p title="What is the commit title: "
set /p message="What is the commit message: "
echo Adding all files...
git add .
echo Commiting all files...
git commit -m "%title%" -m "%message%"
echo Pushing changes...
git push
echo DONE!
pause