<html lang="en">
<head>
    <link rel="stylesheet" href="/books.css", type="text/css">
</head>
<body>
<div class="container">
    <h2>BOOKS</h2>
    <ul class="responsive-table">
        <li class="table-header">
            <div class="col col-1">ID</div>
            <div class="col col-2">NAME</div>
            <div class="col col-3">GENRE</div>
            <div class="col col-4"> </div>
        </li>
        <#list books as book>
        <li class="table-row">
            <div class="col col-1">${book.id}</div>
            <div class="col col-2">${book.name}</div>
            <div class="col col-3">${book.genre}</div>
            <div class="col col-4"><form action="/books/book" method="get">
                                               <input type="submit" value="Посмотреть">
                                               </form></div>
        </li>
    </#list>
    </ul>
</div>
</body>
</html>