/* global ga */
(function() {
  var ENVIRONMENT = window.TEXT_VARIABLES.jekyll.environment;
  var GA_TRACKING_ID = window.TEXT_VARIABLES.site.ga_tracking_id;
  if(GA_TRACKING_ID && ENVIRONMENT !== 'development') {
    /* eslint-disable */
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
    /* eslint-enable */
    ga('create', GA_TRACKING_ID, 'auto');
    ga('send', 'pageview');
  }
})();