@echo off

cd ./simple_project/backend_nodeJs
start cmd /k "node ./server.js"

cd ../frontend_react/
start cmd /k "npm run dev"

cd ../..

cd ./task/automation_test/test/
call mvn clean test

ping -n 6 127.0.0.1 >nul

cd ../../api_test_automation/

call newman run todo.postman_collection.json

cd ../../
rm nul


