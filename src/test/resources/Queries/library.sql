select distinct count(id)
from users;

select count(id)
from book_borrow
where is_returned = 0;

SELECT name
from book_categories;

SELECT *
from books
where name = 'Clean Code';

SELECT *
FROM books b join book_categories bc on b.book_category_id = bc.id
where b.isbn='999239923' and b.description='Test';

SELECT  b.name, isbn, year, author, bc.name, b.description
FROM books b join book_categories bc on bc.id = b.book_category_id
where b.name='Son Ada';