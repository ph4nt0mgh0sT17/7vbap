-- category: table
CREATE TABLE `category` (
  `pk_category_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_category_id`),
  UNIQUE KEY `category_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- user: table
CREATE TABLE `user` (
  `pk_user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(80) NOT NULL,
  `password` varchar(128) NOT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `role` varchar(20) NOT NULL,
  `creation_date` date NOT NULL,
  PRIMARY KEY (`pk_user_id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- post: table
CREATE TABLE `post` (
  `pk_post_id` int NOT NULL AUTO_INCREMENT,
  `author_id` int NOT NULL,
  `title_name` varchar(255) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `html_content` text NOT NULL,
  `thumbnail_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_post_id`),
  UNIQUE KEY `post_title_name_uindex` (`title_name`),
  KEY `post_user_pk_user_id_fk` (`author_id`),
  CONSTRAINT `post_user_pk_user_id_fk` FOREIGN KEY (`author_id`) REFERENCES `user` (`pk_user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- post_category: table
CREATE TABLE `post_category` (
  `pk_post_id` int NOT NULL,
  `pk_category_id` int NOT NULL,
  KEY `post_category_category_pk_category_id_fk` (`pk_category_id`),
  KEY `post_category_post_pk_post_id_fk` (`pk_post_id`),
  CONSTRAINT `post_category_category_pk_category_id_fk` FOREIGN KEY (`pk_category_id`) REFERENCES `category` (`pk_category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `post_category_post_pk_post_id_fk` FOREIGN KEY (`pk_post_id`) REFERENCES `post` (`pk_post_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- post_comment: table
CREATE TABLE `post_comment` (
  `pk_post_comment_id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `author_id` int NOT NULL,
  `text` varchar(500) NOT NULL,
  `creation_datetime` datetime NOT NULL,
  PRIMARY KEY (`pk_post_comment_id`),
  KEY `post_comment_post_pk_post_id_fk` (`post_id`),
  KEY `post_comment_user_pk_user_id_fk` (`author_id`),
  CONSTRAINT `post_comment_post_pk_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`pk_post_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `post_comment_user_pk_user_id_fk` FOREIGN KEY (`author_id`) REFERENCES `user` (`pk_user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- post_reaction: table
CREATE TABLE `post_reaction` (
  `author_id` int NOT NULL,
  `post_id` int NOT NULL,
  `reaction` int NOT NULL,
  PRIMARY KEY (`author_id`,`post_id`),
  KEY `post_reaction_post_pk_post_id_fk` (`post_id`),
  CONSTRAINT `post_reaction_post_pk_post_id_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`pk_post_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `post_reaction_user_pk_user_id_fk` FOREIGN KEY (`author_id`) REFERENCES `user` (`pk_user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
