CREATE TABLE `sm_tasks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `task_no` varchar(128) DEFAULT NULL,
  `task_state` int(11) DEFAULT NULL,
  `total_step` int(11) DEFAULT NULL,
  `current_step` int(11) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `success` int(11) DEFAULT NULL,
  `result` text,
  `ext` text,
  `duration` bigint(20) DEFAULT NULL,
  `owner_user_id` bigint(20) DEFAULT NULL,
  `task_type` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1	 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sm_task_defines` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `key` varchar(128) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sm_task_logs` (
  `id` bigint(20) NOT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  `current_step` int(11) DEFAULT NULL,
  `execute_time` datetime DEFAULT NULL,
  `task_state` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
