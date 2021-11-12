<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
</head>
<body>
    <table>
        <tr>
            <th>Code</th>
            <th>Date</th>
        </tr>
        <#list snippets as snippet>
            <tr>
                <td><pre id="code_snippet">${snippet.getCode()}</pre></td>
                <td><span id="load_date">${snippet.getFormattedDateTime()}</span></td>
            </tr>
        </#list>
    </table>
</body>
</html>