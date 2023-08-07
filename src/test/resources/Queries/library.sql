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

select bc.name,count(*) from book_borrow join books b on b.id = book_borrow.book_id
join book_categories bc on b.book_category_id = bc.id
group by name
order by 2 desc;

select bc.name,count(*) from books b join book_categories bc on b.book_category_id = bc.id
                        join book_borrow bb on b.id = bb.book_id

group by name
order by 2 desc;

;