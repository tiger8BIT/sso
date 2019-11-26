CREATE TABLE `session` (
  `id` int UNIQUE PRIMARY KEY NOT NULL,
  `jwt` text,
  `create_time` timestamp,
  `user_id` int NOT NULL
);

CREATE TABLE `user` (
  `id` int UNIQUE PRIMARY KEY NOT NULL,
  `login` varchar(20),
  `password` varchar(25),
  `info` text
);

CREATE TABLE `role` (
  `id` int UNIQUE PRIMARY KEY NOT NULL,
  `app_id` int NOT NULL,
  `role_name` varchar(20),
  `description` text
);

CREATE TABLE `role_table` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`)
);

CREATE TABLE `app` (
  `id` int UNIQUE PRIMARY KEY NOT NULL,
  `url` varchar(50)
);

ALTER TABLE `session` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `role` ADD FOREIGN KEY (`app_id`) REFERENCES `app` (`id`);

ALTER TABLE `role_table` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `role_table` ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
