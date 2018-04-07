var gulp = require('gulp');
var rename = require('gulp-rename');
var svg2png = require('gulp-svg2png');
var ico = require('gulp-to-ico');

var iconBasename = 'icon';
var iconDestPath = './assets/images/logo';
var faviconDestPath = './';

function coverSvg(width, height) {
  height || (height = width);
  return gulp.src('./assets/images/logo/logo.svg')
    .pipe(svg2png({ width: width, height: height }))
    .pipe(rename({
      basename: iconBasename,
      suffix: '-' + width + 'x' + height
    }))
    .pipe(gulp.dest(iconDestPath));
}

gulp.task('icon310', function() {
  return coverSvg(310);
});
gulp.task('icon310x150', function() {
  return coverSvg(310, 150);
});
gulp.task('icon192', function() {
  return coverSvg(192);
});
gulp.task('icon180', function() {
  return coverSvg(180);
});
gulp.task('icon167', function() {
  return coverSvg(167);
});
gulp.task('icon152', function() {
  return coverSvg(152);
});
gulp.task('icon150', function() {
  return coverSvg(150);
});
gulp.task('icon128', function() {
  return coverSvg(128);
});
gulp.task('icon120', function() {
  return coverSvg(120);
});
gulp.task('icon70', function() {
  return coverSvg(70);
});
gulp.task('icon48', function() {
  return coverSvg(48);
});
gulp.task('icon16', function() {
  return coverSvg(16);
});

gulp.task('favicon', ['icon128', 'icon48', 'icon16'], function() {
  return gulp.src([
    iconDestPath + '/icon-128x128.png',
    iconDestPath + '/icon-48x48.png',
    iconDestPath + '/icon-16x16.png'])
      .pipe(ico({ path: 'favicon.ico'}))
      .pipe(gulp.dest(faviconDestPath));
});

gulp.task('icons', [
  'icon310', 'icon310x150', 'icon192', 'icon180', 'icon167', 'icon152', 'icon150', 'icon120', 'icon70'
]);

gulp.task('artwork', ['favicon', 'icons']);