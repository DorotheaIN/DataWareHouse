CREATE TABLE `movie` (
  `movie_id` int(7) NOT NULL,
  `title` text NOT NULL,
  `runtime` int(11) NOT NULL,
  `releasedate` date NOT NULL,
  PRIMARY KEY (`movie_id`),
  KEY `create_date_index` (`releasedate`) USING BTREE,
  KEY `create_title_index` (`title`(500)) USING BTREE,
  KEY `movie_id` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `actor` (
  `movie_id` int(11) NOT NULL,
  `actor_id` int(11) NOT NULL,
  `actor_name` varchar(255) NOT NULL,
  `isleading` varchar(6) NOT NULL,
  PRIMARY KEY (`movie_id`,`actor_id`),
  KEY `actor_id` (`actor_id`) USING BTREE,
  KEY `movie_id` (`movie_id`) USING BTREE,
  KEY `actor_name` (`actor_name`) USING BTREE,
  CONSTRAINT `actor_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `director` (
  `movie_id` int(7) NOT NULL,
  `director_id` int(10) NOT NULL,
  `director_name` varchar(255) NOT NULL,
  PRIMARY KEY (`movie_id`,`director_id`),
  KEY `movie_id` (`movie_id`),
  KEY `director_id` (`director_id`),
  KEY `director_name` (`director_name`),
  CONSTRAINT `director_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `writer` (
  `movie_id` int(7) NOT NULL,
  `writer_id` int(11) NOT NULL,
  `writer_name` varchar(255) NOT NULL,
  PRIMARY KEY (`movie_id`,`writer_id`),
  KEY `writer_name` (`writer_name`),
  KEY `movie_id` (`movie_id`),
  KEY `writer_id` (`writer_id`),
  CONSTRAINT `writer_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `genres` (
  `movie_id` int(11) NOT NULL,
  `genres_name` varchar(255) NOT NULL,
  PRIMARY KEY (`movie_id`,`genres_name`),
  KEY `genres_name` (`genres_name`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `genres_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product` (
  `product_asin` varchar(255) NOT NULL,
  `movie_id` int(11) NOT NULL,
  PRIMARY KEY (`product_asin`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `review` (
  `movie_id` int(11) NOT NULL,
  `reviewer_id` varchar(255) NOT NULL,
  `review_time` date NOT NULL,
  `score` float(2,1) NOT NULL,
  `text` text NOT NULL,
  `summary` text NOT NULL,
  `reviewer_name` varchar(255) NOT NULL,
  `helpness` char(5) DEFAULT NULL,
  PRIMARY KEY (`movie_id`,`reviewer_id`,`review_time`),
  KEY `movie_id` (`movie_id`),
  KEY `score` (`score`),
  KEY `review_time` (`review_time`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `movie_actor` (
  `movie_id` int(11) NOT NULL,
  `title` text,
  `runtime` int(11) DEFAULT NULL,
  `releasedate` date DEFAULT NULL,
  `actor_id` int(11) NOT NULL,
  `actor_name` varchar(255) DEFAULT NULL,
  `isleading` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`movie_id`,`actor_id`),
  KEY `actor_id` (`actor_id`) USING BTREE,
  KEY `movie_id` (`movie_id`) USING BTREE,
  KEY `actor_name` (`actor_name`),
  CONSTRAINT `movie_actor_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  CONSTRAINT `movie_actor_ibfk_2` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`actor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `movie_director` (
  `movie_id` int(11) NOT NULL,
  `title` text,
  `runtime` int(11) DEFAULT NULL,
  `releasedate` date DEFAULT NULL,
  `director_id` int(11) NOT NULL,
  `director_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`movie_id`,`director_id`),
  KEY `director_name` (`director_name`) USING BTREE,
  KEY `director_id` (`director_id`) USING BTREE,
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `movie_director_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  CONSTRAINT `movie_director_ibfk_2` FOREIGN KEY (`director_id`) REFERENCES `director` (`director_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `movie_writer` (
  `movie_id` int(11) NOT NULL,
  `title` text,
  `runtime` int(11) DEFAULT NULL,
  `releasedate` date DEFAULT NULL,
  `writer_id` int(11) NOT NULL,
  `writer_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`movie_id`,`writer_id`),
  KEY `writer_name` (`writer_name`),
  KEY `writer_id` (`writer_id`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `movie_writer_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
  CONSTRAINT `movie_writer_ibfk_2` FOREIGN KEY (`writer_id`) REFERENCES `writer` (`writer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


