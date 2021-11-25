read -p  "please input commit comments:" msg
git add .
git commit -m "$msg"
git push origin
