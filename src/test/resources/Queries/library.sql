select distinct count(id) from users;

select count(id) from book_borrow where is_returned=0;