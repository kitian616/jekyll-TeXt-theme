#! /bin/zsh
# 自动化推送脚本, 在 ~/.zshrc 里面设置脚本运行的 alias
# 获取脚本所在绝对路径
SHELL_FOLDER=$(cd "$(dirname "$0")";pwd)
cd $SHELL_FOLDER
git add .
git commit -m "$1"
git push origin

