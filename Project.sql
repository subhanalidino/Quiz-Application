SELECT * FROM quiz.topic;

SELECT * FROM quiz.subtopics;


CREATE DATABASE quiz;
-- subtopics table 

CREATE TABLE quiz.question (
    question_id INT NOT NULL,
    question_name VARCHAR(225),
    subtopic_id INT NOT NULL,
    PRIMARY KEY (question_id),
    FOREIGN KEY (subtopic_id)
        REFERENCES quiz.subtopics(subtopic_id)
        ON DELETE CASCADE
);

CREATE table quiz.subtopics(
subtopic_id INT NOT NULL , 
subtopic_name VARCHAR(45) NOT NULL,
topic_id INT, 
primary key(subtopic_id),
foreign key (topic_id) references quiz.topic(topic_id)
 );
 select * FROM quiz.subtopics;

CREATE Table quiz.choice(
choice_name text NOT NULL ,
question_id INT ,
is_correct boolean NOT NULL ,  
primary key (choice_id),
foreign key (question_id) references quiz.question(question_id)
);

CREATE TABLE quiz.topic (
topic_id INT NOT NULL ,
topic_name VARCHAR(45) ,
primary key (topic_id)
);


INSERT INTO quiz.topic VALUES 
(1 , 'English'),
(2 , 'Database');

SELECT * FROM quiz.choice;

SHOW TABLES;

INSERT INTO quiz.subtopics VALUES
(1 , 'Verb' , 1),
(2 , 'Tenses' , 1);

INSERT INTO quiz.subtopics VALUES
(3 , 'Table and Records' , 2),
(4 , 'Database Constraints' , 2);

SELECT * FROM quiz.subtopics;

INSERT INTO quiz.question VALUES 
(1 , 'The Scissors ________ on the table.' , 1), 
(2 , 'He enjoys _______ photos. ' , 1),
(3 , 'Each of the players _______ a trophy. ' , 1),
(4 , 'I would rather _______ at home. ' , 1),
(5 , 'She _______ play the piano very well. ' , 1);

insert into quiz.question values
(6, 'I _______ my breakfast yet.' ,2),
(7, 'Last night, we _______ a loud noise.',2),
(8,'It _______ since 8 o clock this morning',2),
(9,'Water _______ at 100°C.',2),
(10,'Tomorrow at this time, I _______ on a beach.',2);

SELECT * FROM quiz.question;

insert into quiz.question values
(11,'In a relational database, what is a "Record"?',3),
(12,'Which of the following best describes a "Table"?',3),
(13,'If a table is designed to store "Books," what would an individual "Record" represent?',3),
(14,'Tables are often referred to by another technical term in formal database theory. What is it?',3),
(15,'When looking at a table, the vertical sections that define the data categories (like "First Name" or "Date of Birth") are called:',3);

insert into quiz.question values
(16,'Which constraint is used to uniquely identify each record in a table and strictly prohibits NULL values?',4),
(17,'What happens if you try to insert a record into a column with a "NOT NULL" constraint without providing a value?',4),
(18,'Which constraint ensures that a value in one table must exist in a specific column of another table ?',4),
(19,'If you want to ensure that a "Price" column only accepts values greater than 0, which constraint should you use?',4),
(20,'What is a primary difference between a PRIMARY KEY and a UNIQUE constraint?',4);

SELECT * FROM quiz.choice;

DESCRIBE quiz.question;
DESCRIBE quiz.choice;

DROP TABLE quiz.choice;
DROP TABLE quiz.questions;

insert into quiz.choice values
('is',1,0),
('are',1,1),
('be',1,0),
('take',2,0),
('to take',2,0),
('taking',2,1),
('receive',3,0),
('receives',3,1),
('receiving',3,0);

INSERT INTO quiz.choice VALUES 
('stay' , 4 , 1),
('stays' , 4 , 0),
('staying', 4 , 0),
('can' , 5 , 1),  
('could' , 5 , 0),
('must' , 5 , 0);

INSERT INTO quiz.choice VALUES 
('didn’t eat' , 6 , 0),
('haven’t eaten' , 6 , 1),
('don’t eat' , 6 , 0),
('hear' , 7 , 0),
('heard' , 7 , 1 ),
('hearing' , 7 , 0),
('is raining' , 8 , 0),
('has been raining' , 8 , 1), 
('rained' , 8 , 0),
('boils' , 9 , 1),
('is boiling' , 9 , 0),
('boiled' , 9 , 0),
('will be lying' , 10 , 1),
('am lying' , 10 , 0),
('was lying' , 10 , 0);

INSERT INTO quiz.choice VALUES 
('Column in table' , 11 , 0), 
('Row in table' , 11 , 1),
('A database schema' , 11 , 0),
('A collection of rows and columns' , 12 , 1),
('A single record' , 12 , 0),
('A constraint rule' , 12 , 0),
('A book entry' , 13 , 1),
('A column name' , 13 , 0),
('A database' , 13 , 0),
('Entity' , 14 , 0),
('Relation' , 14 , 1),
('Attribute' , 14 , 0),
('Rows' , 15 , 0),
('Columns' , 15 , 1 ),
('Records' , 15 , 0);

INSERT INTO quiz.choice VALUES
('UNIQUE' , 16 , 0),
('PRIMARY KEY' , 16 , 1),
('CHECK' , 16 , 0),
('Value becomes NULL automatically' , 17 , 0),
('Error occurs' , 17 , 1),
('Default value is used' , 17 , 0),
('PRIMARY KEY' , 18 , 0),
('FOREIGN KEY' , 18 , 1),
('UNIQUE' , 18 , 0),
('CHECK' , 19 , 1),
('NOT NULL' , 19 , 0),
('UNIQUE' , 19 , 0),
('UNIQUE allows NULL values' , 20 , 1),
('PRIMARY KEY allows duplicates' , 20 , 0),
('Both are identical' , 20 , 0);

SELECT * FROM quiz.choice;

SELECT * FROM quiz.question;

