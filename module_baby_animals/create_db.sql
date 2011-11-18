CREATE TABLE android_metadata(locale TEXT DEFAULT 'en_US');
INSERT INTO android_metadata VALUES ('en_US');
.separator "\t"
CREATE TABLE items(_id INT, title VARCHAR(255), description TEXT);
.import items.txt items
