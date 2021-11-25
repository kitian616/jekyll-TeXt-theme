# 自动化推送脚本, 在 ~/.zshrc 里面设置脚本运行的 alias
cd /Users/holon/Documents/GitHub/wholon.github.io
read -p  "please input commit comments:" msg
git add .
git commit -m "$msg"
git push origin
