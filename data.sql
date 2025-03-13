
-- 1. In PostgreSQL, you don't use "USE ToDoList;".
--    Instead, you connect directly to the database in your psql client or JDBC URL.

-- 2. Drop the table if it exists:
DROP TABLE IF EXISTS tbl_user CASCADE;

-- 3. Create the table
CREATE TABLE tbl_user (
                          id SERIAL PRIMARY KEY,
                          username VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          display_name VARCHAR(256),
                          UNIQUE (username)
);

-- 4. Insert initial data
--    If you specifically want `id = 1` for this record,
--    you can provide the ID in the INSERT statement. 
--    By default, SERIAL will auto-generate IDs starting at 1 anyway.
INSERT INTO tbl_user (id, username, password, display_name)
VALUES (1, 'admin3', '$2a$10$PrlN/6A0qLWjOgVbpD7eROGtj9XgW7qGWnW8aRGbJh3TbfmhTUsPO', 'Adm3in2');

-- If you want to ensure the next auto-generated ID starts at 2:
-- ALTER SEQUENCE tbl_user_id_seq RESTART WITH 2;
