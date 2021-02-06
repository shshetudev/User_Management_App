CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `city` varchar(255) DEFAULT NULL,
                        `created_at` datetime(6) DEFAULT NULL,
                        `first_name` varchar(255) NOT NULL,
                        `last_name` varchar(255) DEFAULT NULL,
                        `state` varchar(255) DEFAULT NULL,
                        `street` varchar(255) DEFAULT NULL,
                        `updated_at` datetime(6) DEFAULT NULL,
                        `user_type` varchar(255) NOT NULL,
                        `zip_code` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;