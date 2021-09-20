#!/bin/sh

echo Commit

d=$(date)


git add .
git commit -m "Commit at $d"
git pull --rebase
git push

echo Deploy

# compress
# cp index.html index.html.backup
# gzip -9 index.html

s3-website create brothersouchan.org

bundle exec jekyll build # --watch

cp .s3-website.json _site/
cd _site
s3-website deploy .
cd ..

# roll back
# rm index.html.gz
# mv index.html.backup index.html

# remove cloudfront cache by invalidation
# aws cloudfront create-invalidation --distribution-id=EWMKSYP4AE9P9 --paths "/*"
