<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create</title>
    <script>
        function send() {
            let object = {
                "code": document.getElementById("code_snippet").value,
                "timeLeft": document.getElementById("time_restriction").value,
                "viewsLeft": document.getElementById("views_restriction").value,
            };

            let json = JSON.stringify(object);
            console.log(json);

            let xhr = new XMLHttpRequest();
            xhr.open("POST", '/api/code/new', false)
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.send(json);

            if (xhr.status === 200) {
                alert("Success!");
            }
        }
    </script>
</head>
<body>
<form>
    Czas: <input id="time_restriction" type="text"/> </br>
    Ilosc wejsc: <input id="views_restriction" type="text"/> </br>
    Twoj kod: <textarea id="code_snippet">// write your code here</textarea> </br>
    <button id="send_snippet" type="submit" onclick="send()">Submit</button>
</form>
</body>
</html>