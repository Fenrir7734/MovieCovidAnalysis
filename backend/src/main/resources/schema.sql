DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `movie_premiere_month_count`;
DROP TABLE IF EXISTS `movie_genre`;
DROP TABLE IF EXISTS `movie`;
DROP TABLE IF EXISTS `covid_report`;
DROP TABLE IF EXISTS `title_type`;
DROP TABLE IF EXISTS `genre`;
DROP TABLE IF EXISTS `country`;

CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `email` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `role` varchar(255) DEFAULT NULL,
                        `username` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
);

CREATE TABLE `country` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) NOT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UK_c_name` (`name`)
);

CREATE TABLE `genre` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) NOT NULL,
                         PRIMARY KEY (`id`)
);

CREATE TABLE `title_type` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `name` varchar(255) NOT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `UK_tt_name` (`name`)
);

CREATE TABLE `covid_report` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `cumulative_cases` int NOT NULL,
                                `cumulative_deaths` int NOT NULL,
                                `date` datetime(6) NOT NULL,
                                `new_cases` int NOT NULL,
                                `new_deaths` int NOT NULL,
                                `country_id` bigint DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                CONSTRAINT `FK_country_id` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
);

CREATE TABLE `movie` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `average_rating` double NOT NULL,
                         `end_year` int DEFAULT NULL,
                         `is_adult` bit(1) NOT NULL,
                         `num_votes` int NOT NULL,
                         `runtime_minutes` int NOT NULL,
                         `start_year` int NOT NULL,
                         `title` varchar(255) NOT NULL,
                         `title_id` varchar(255) NOT NULL,
                         `title_type_id` bigint DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         CONSTRAINT `FK_title_type_id` FOREIGN KEY (`title_type_id`) REFERENCES `title_type` (`id`)
);

CREATE TABLE `movie_genre` (
                               `movie_id` bigint NOT NULL,
                               `genre_id` bigint NOT NULL,
                               PRIMARY KEY (`movie_id`,`genre_id`),
                               CONSTRAINT `FK_genre_id` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`),
                               CONSTRAINT `FK_movie_id` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
);

CREATE TABLE `movie_premiere_month_count` (
                                              `id` bigint NOT NULL AUTO_INCREMENT,
                                              `count` int DEFAULT NULL,
                                              `date` date DEFAULT NULL,
                                              PRIMARY KEY (`id`)
);
