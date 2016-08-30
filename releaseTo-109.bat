echo "发布此项目到私服"
echo "当前脚本目录： %~dp0"
 
mvn deploy clean

rem 另类延时 
ping 127.0.0.1 -n 4 > %temp%/temp.txt
del %temp%\temp.txt
