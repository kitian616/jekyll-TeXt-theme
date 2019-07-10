#!/usr/bin/env node
var path = require('path');
var fs = require('fs');


var imgRg = /!\[([^\]]*)\]\((\/[^/]\S*)\)/gm;
var basePath = 'https://raw.githubusercontent.com/kitian616/jekyll-TeXt-theme/master';
var srcPaths = ['./test/_posts', './docs/en', './docs/zh'];

function runner() {
  srcPaths.forEach(function(srcPath) {
    fs.readdir(srcPath, function(err, files) {
      files.forEach(function(file) {
        var curPath = srcPath + '/' + file;
        fs.stat(curPath, function(err, stat) {
          if (stat.isFile() && path.extname(curPath) === '.md') {
            fs.readFile(curPath, 'utf8', function(err, data) {
              if (err) throw err;
              data = assertUrl(data);
              fs.writeFile(curPath, data, 'utf8', function(err) {
                if (err) throw err;
              });
            });
          }
        });
      });
    });
  });
}


function assertUrl(content) {
  return content.replace(imgRg, '![$1](' + basePath + '$2)');
}

runner();