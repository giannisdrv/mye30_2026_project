SET GLOBAL local_infile = 1;
SET FOREIGN_KEY_CHECKS = 0;
CREATE DATABASE IF NOT EXISTS advanced_databases_project_2026;

use advanced_databases_project_2026;
DROP TABLES IF EXISTS Article_Author, Article, Journal, BestSubjectArea, Conference, Author;

CREATE TABLE Author (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE Conference (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    acronym VARCHAR(255) NOT NULL,
    ranking VARCHAR(255) NOT NULL,
    primaryFOR VARCHAR(255) NOT NULL 
)ENGINE = InnoDB;

CREATE TABLE BestSubjectArea (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE Journal (
    id INT PRIMARY KEY,
    ranking INT,
    title VARCHAR(255) ,
    oa VARCHAR(255),
    country VARCHAR(255),
    sjr DECIMAL(10, 3),
    citescore DECIMAL(10, 1),
    hindex INT,
    bestquartile VARCHAR(255),
    bestSubjectArea_id INT,
    totalrefs INT,
    total_cites_3y INT,
    citable_docs_3y INT,
    cites_doc_2y INT,
    refs_doc DECIMAL(10, 2),
    publisher VARCHAR(255),
    FOREIGN KEY (bestSubjectArea_id) REFERENCES BestSubjectArea(id)
)ENGINE = InnoDB;

CREATE TABLE Article (
    id INT PRIMARY KEY,
    booktitle_id INT NULL,
    journal_id INT NULL,
    key_id VARCHAR(255) NOT NULL,
    mdate VARCHAR(255) NOT NULL,
    pages VARCHAR(255) NOT NULL, 
    title TEXT NOT NULL,
    url VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    FOREIGN KEY (booktitle_id) REFERENCES Conference(id),
    FOREIGN KEY (journal_id) REFERENCES Journal(id)
)ENGINE = InnoDB;

CREATE TABLE Article_Author (
    article_id INT,
    author_id INT,
    PRIMARY KEY (article_id, author_id),
    FOREIGN KEY (article_id) REFERENCES Article(id),
    FOREIGN KEY (author_id) REFERENCES Author(id)
)ENGINE = InnoDB;

LOAD DATA LOCAL INFILE 'C:/Users/driva/Desktop/cse/advanced_databases/project_2026/processed_data/iCore26_KilledColumnsForLoading_table.csv' INTO TABLE Conference FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' IGNORE 1 ROWS(id,title,acronym,ranking,primaryFOR);
LOAD DATA LOCAL INFILE 'C:/Users/driva/Desktop/cse/advanced_databases/project_2026/processed_data/author_table.csv' INTO TABLE Author FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\r\n' IGNORE 1 ROWS;
LOAD DATA LOCAL INFILE 'C:/Users/driva/Desktop/cse/advanced_databases/project_2026/processed_data/bestSubjectArea_table.csv' INTO TABLE BestSubjectArea FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\r\n' IGNORE 1 ROWS;
LOAD DATA LOCAL INFILE 'C:/Users/driva/Desktop/cse/advanced_databases/project_2026/processed_data/journal_ranking_data_table.csv' INTO TABLE Journal FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\r\n' IGNORE 1 ROWS;
LOAD DATA LOCAL INFILE 'C:/Users/driva/Desktop/cse/advanced_databases/project_2026/processed_data/article_author_table.csv' INTO TABLE Article_Author FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\r\n' IGNORE 1 ROWS;
LOAD DATA LOCAL INFILE 'C:/Users/driva/Desktop/cse/advanced_databases/project_2026/processed_data/final_article_table.csv' INTO TABLE Article FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\r\n' IGNORE 1 ROWS;
SET FOREIGN_KEY_CHECKS = 1;