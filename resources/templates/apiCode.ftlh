<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet"
          target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
    <body>
        <span id="load_date">${date}</span> </br>
        <#if 0 < time><span id="time_restriction">Time: ${time}</span> </br></#if>
        <#if views != 0><span id="views_restriction">Views: ${views}</span> </br></#if>
        <pre id="code_snippet"><code>${code}</code></pre>
    </body>
</html>