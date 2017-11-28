DROP TABLE IF EXISTS `item`;
DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `name` varchar(255) DEFAULT NULL,
  `id` INT(10)AUTO_INCREMENT PRIMARY KEY,
  KEY `idx_category_id` (`id`)
);

CREATE TABLE `item` (
  `id` INT(10)AUTO_INCREMENT PRIMARY KEY,
  `category_id` INT,
  `count` int(11) NOT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  KEY `idx_item_id` (`id`),
  INDEX cat_ind (category_id),
    FOREIGN KEY (category_id)
        REFERENCES category(id)
        ON DELETE CASCADE
);