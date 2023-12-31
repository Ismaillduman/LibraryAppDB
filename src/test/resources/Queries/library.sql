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



SELECT  b.name, isbn, year, author, b.description,bc.id as category
                FROM books b join book_categories bc on b.book_category_id = bc.id
                where b.name='Son Ada';

select bc.name,count(*) from book_borrow join books b on b.id = book_borrow.book_id
join book_categories bc on b.book_category_id = bc.id
group by name
order by 2 desc;

select bc.name,count(*) from books b join book_categories bc on b.book_category_id = bc.id
                        join book_borrow bb on b.id = bb.book_id

group by name
order by 2 desc;

select name,isbn from books where isbn;
SELECT name from books;
SELECT name, isbn from books;

select name, books.id from books join book_borrow bb on books.id = bb.book_id
group by id desc;

SELECT  name, author FROM books ORDER BY id DESC;


select full_name, name,returned_date from books join book_borrow bb on books.id = bb.book_id
join users u on bb.user_id = u.id
order by borrowed_date desc;

select status, count(*) from users
where status='INACTIVE';

