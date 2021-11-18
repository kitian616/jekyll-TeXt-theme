var shell = require('shelljs');
const args = process.argv.slice(2)
var commit = args[0];
if(!commit || commit.length == 0) {
    console.log("git commit -m 注释为空")
    return;
}

console.log(commit);
shell.exec('export all_proxy=socks5://127.0.0.1:1080');
shell.exec('npm run build');
shell.exec('git add .');
shell.exec("git commit -m '" + commit + "' --no-verify");
shell.exec('git push');