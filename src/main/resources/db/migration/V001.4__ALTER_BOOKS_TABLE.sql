alter table books
alter column publication_year type smallint using extract(year from publication_year);
