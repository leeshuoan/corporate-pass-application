INSERT INTO `max_limits` (`id`, `bookings`,`loans`) VALUES (1, 2, 2);

INSERT INTO `role` (`id`, `name`) VALUES (1, 'admin');
INSERT INTO `role` (`id`, `name`) VALUES (2, 'employee');
INSERT INTO `role` (`id`, `name`) VALUES (3, 'gop');

INSERT INTO `attractions` (`attraction_id`, `name`, `replacement_fee`, `address`,`card_type`,`card_name`,`pass_type`) VALUES (1, 'Wildlife Reserves Singapore', 1000, '80 Mandai Lake Road,Singapore 729826', 'PREMIUM CORPORATE FRIENDS OF THE ZOO (CFOZ) MEMBERSHIP','CFOZ Premium Card', 'physical');

INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`, `enabled`, `verification_code`) VALUES ('admin@gmail.com', 91234567, 0.0, 'admin', '$2a$10$97vDf0bUz8OVmnaCTjmcyOjiwNhpivtUUe7GtsY.c7jWT9tAj64xu', 'salt', 'admin', '0', TRUE, 0);
INSERT INTO `user_role` (`email`, `role_id`) VALUES ('admin@gmail.com', 1);
INSERT INTO `user_role` (`email`, `role_id`) VALUES ('admin@gmail.com', 2);

INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`, `enabled`, `verification_code`) VALUES ('seah.pm99@gmail.com', 123, 0.0, 'pei ming', '$2a$10$97vDf0bUz8OVmnaCTjmcyOjiwNhpivtUUe7GtsY.c7jWT9tAj64xu', 'salt', 'pm', '0', TRUE, 0);
INSERT INTO `user_role` (`email`, `role_id`) VALUES ('seah.pm99@gmail.com',1);

INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`, `enabled`, `verification_code`) VALUES ('leeshuoan38@gmail.com', 91234567, 0.0, 'shuoan', '$2a$10$UBh0SdDZyqE13QRQUmMcwuXN86Jn1FdPnNtabobqNHECtmEb/fhgC', 'salt', 'weikhiang', '0', TRUE,0);
INSERT INTO `user_role` (`email`, `role_id`) VALUES ('leeshuoan38@gmail.com', 2);


INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`, `enabled`, `verification_code`) VALUES ('employee@gmail.com', 91234567, 0.0, 'employee', '$2a$10$UBh0SdDZyqE13QRQUmMcwuXN86Jn1FdPnNtabobqNHECtmEb/fhgC', 'salt', 'employee', '0', TRUE, 0);
INSERT INTO `user_role` (`email`, `role_id`) VALUES ('employee@gmail.com', 2);

INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`, `enabled`, `verification_code`) VALUES ('gop@gmail.com', 91234567, 0.0, 'gop', '$2a$10$Mt3QZDHR/TvO1MRk52XNged/z/ZGNv7Mh7cJWjR1f16y4aaQrygO.', 'salt', 'gop', '0', TRUE, 0);
INSERT INTO `user_role` (`email`, `role_id`) VALUES ('gop@gmail.com', 3);

INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (1, 'loaned', 1, 'seah.pm99@gmail.com');
INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (2, 'loaned', 1, 'seah.pm99@gmail.com');
INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (3, 'available', 1, '-');
INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (4, 'available', 1, '-');
INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (5, 'available', 1, '-');
INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (6, 'lost', 1, '-');
INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (7, 'lost', 1, '-');
INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (8, 'lost', 1, '-');
INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (9, 'available', 1, '-');
INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (10, 'available', 1, '-');
INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (11, 'available', 1, '-');

-- insert dummy data in loans table
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-10-29', 'seah.pm99@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-11-16', 'seah.pm99@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-22', 'alice@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-12', 'alice@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-26', 'alice@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-3-3', 'alice@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-3-15', 'alice@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-11', 'sarah@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-1-29', 'sarah@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-2-28', 'sarah@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-29', 'sarah@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-7-29', 'sarah@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-4', 'jenny@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-8', 'jenny@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-5-5', 'jenny@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-5', 'jenny@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-8-8', 'lisa@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-9', 'lisa@gmail.com');
INSERT INTO `loan` (`date`, `email`) VALUES ('2022-10-13', 'john@gmail.com');

INSERT INTO `loan_details` (`id`, `corporate_pass_id`, `loan_id`) VALUES ('1', '1', '1');
INSERT INTO `loan_details` (`id`, `corporate_pass_id`, `loan_id`) VALUES ('2', '2', '2');
INSERT INTO `loan_details` (`id`, `corporate_pass_id`, `loan_id`) VALUES ('3', '3', '3');

-- INSERT INTO `role` (`id`, `name`) VALUES (1, 'admin');
-- INSERT INTO `role` (`id`, `name`) VALUES (2, 'employee');
-- INSERT INTO `role` (`id`, `name`) VALUES (3, 'gop');

-- INSERT INTO `attractions` (`attraction_id`, `name`, `replacement_fee`, `address`,`card_type`,`card_name`,`pass_type`) VALUES (1, 'Wildlife Reserves Singapore', 1000, '80 Mandai Lake Road,Singapore 729826', 'PREMIUM CORPORATE FRIENDS OF THE ZOO (CFOZ) MEMBERSHIP','CFOZ Premium Card', 'electronic');

-- INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`) VALUES ('admin@gmail.com', 91234567, 0.0, 'admin', '$2a$10$97vDf0bUz8OVmnaCTjmcyOjiwNhpivtUUe7GtsY.c7jWT9tAj64xu', 'salt', 'admin', '0');
-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('admin@gmail.com', 1);
-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('admin@gmail.com', 2);

-- INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`) VALUES ('employee@gmail.com', 91234567, 0.0, 'employee', '$2a$10$UBh0SdDZyqE13QRQUmMcwuXN86Jn1FdPnNtabobqNHECtmEb/fhgC', 'salt', 'employee', '0');
-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('employee@gmail.com', 2);

-- INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`) VALUES ('weikhiang92000@gmail.com', 91234567, 0.0, 'weikhiang', '$2a$10$UBh0SdDZyqE13QRQUmMcwuXN86Jn1FdPnNtabobqNHECtmEb/fhgC', 'salt', 'weikhiang', '0');
-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('weikhiang92000@gmail.com', 2);

-- INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`) VALUES ('gop@gmail.com', 91234567, 0.0, 'gop', '$2a$10$Mt3QZDHR/TvO1MRk52XNged/z/ZGNv7Mh7cJWjR1f16y4aaQrygO.', 'salt', 'gop', '0');
-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('gop@gmail.com', 3);

-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (1, 'reserved', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (2, 'reserved', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (3, 'reserved', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (4, 'reserved', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (5, 'reserved', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (6, 'lost', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (7, 'reserved', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (8, 'lost', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (9, 'reserved', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (10, 'reserved', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (11, 'reserved', 1, '-');

-- -- insert dummy data in loans table
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-10-13', 'john@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-10-1', 'john@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-10', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-22', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-12', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-26', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-3-3', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-3-15', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-11', 'sarah@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-1-29', 'sarah@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-2-28', 'sarah@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-29', 'sarah@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-7-29', 'sarah@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-4', 'jenny@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-8', 'jenny@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-5-5', 'jenny@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-5', 'jenny@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-8-8', 'lisa@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-9', 'lisa@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-10-13', 'john@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-11-11', 'john@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-11-12', 'john@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-11-13', 'alice@gmail.com');

-- INSERT INTO `loan_details` (`corporate_pass_id`, `loan_id`) VALUES ('1', '1');
-- INSERT INTO `loan_details` (`corporate_pass_id`, `loan_id`) VALUES ('2', '20');
-- INSERT INTO `loan_details` (`corporate_pass_id`, `loan_id`) VALUES ('1', '21');
-- INSERT INTO `loan_details` (`corporate_pass_id`, `loan_id`) VALUES ('1', '22');
-- INSERT INTO `loan_details` (`corporate_pass_id`, `loan_id`) VALUES ('1', '23');


-- INSERT INTO `max_limits` (`id`, `bookings`,`loans`) VALUES (1, 2, 2);

-- INSERT INTO `role` (`id`, `name`) VALUES (1, 'admin');
-- INSERT INTO `role` (`id`, `name`) VALUES (2, 'employee');
-- INSERT INTO `role` (`id`, `name`) VALUES (3, 'gop');

-- INSERT INTO `attractions` (`attraction_id`, `name`, `replacement_fee`, `address`,`card_type`,`card_name`,`pass_type`) VALUES (1, 'Wildlife Reserves Singapore', 1000, '80 Mandai Lake Road,Singapore 729826', 'PREMIUM CORPORATE FRIENDS OF THE ZOO (CFOZ) MEMBERSHIP','CFOZ Premium Card', 'electronic');

-- INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`, `enabled`, `verification_code`) VALUES ('admin@gmail.com', 91234567, 0.0, 'admin', '$2a$10$97vDf0bUz8OVmnaCTjmcyOjiwNhpivtUUe7GtsY.c7jWT9tAj64xu', 'salt', 'admin', '0', TRUE, 0);
-- INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`, `enabled`, `verification_code`) VALUES ('seah.pm99@gmail.com', 123, 0.0, 'employee', '$2a$10$97vDf0bUz8OVmnaCTjmcyOjiwNhpivtUUe7GtsY.c7jWT9tAj64xu', 'salt', 'pm', '0', TRUE, 0);
-- INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`, `enabled`, `verification_code`) VALUES ('weikhiang92000@gmail.com', 123, 0.0, 'employee', '$2a$10$UBh0SdDZyqE13QRQUmMcwuXN86Jn1FdPnNtabobqNHECtmEb/fhgC', 'salt', 'wk', '0', TRUE, 0);

-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('admin@gmail.com', 1);
-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('admin@gmail.com', 2);
-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('seah.pm99@gmail.com',2);
-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('weikhiang92000@gmail.com',2);

-- INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`, `enabled`, `verification_code`) VALUES ('employee@gmail.com', 91234567, 0.0, 'employee', '$2a$10$UBh0SdDZyqE13QRQUmMcwuXN86Jn1FdPnNtabobqNHECtmEb/fhgC', 'salt', 'employee', '0', TRUE, 0);
-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('employee@gmail.com', 2);

-- INSERT INTO `user` (`email`, `contact`, `dues`, `name`, `password`, `salt`, `username`, `is_banned`, `enabled`, `verification_code`) VALUES ('gop@gmail.com', 91234567, 0.0, 'gop', '$2a$10$Mt3QZDHR/TvO1MRk52XNged/z/ZGNv7Mh7cJWjR1f16y4aaQrygO.', 'salt', 'gop', '0', TRUE, 0);
-- INSERT INTO `user_role` (`email`, `role_id`) VALUES ('gop@gmail.com', 3);

-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (1, 'reserved', 1, 'seah.pm99@gmail.com');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (2, 'loaned', 1, 'john@gmail.com');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (3, 'loaned', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (4, 'loaned', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (5, 'loaned', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (6, 'lost', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (7, 'loaned', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (8, 'lost', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (9, 'loaned', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (10, 'loaned', 1, '-');
-- INSERT INTO `corporate_pass` (`corporate_pass_id`, `status`, `attraction_id`, `email`) VALUES (11, 'loaned', 1, '-');

-- -- insert dummy data in loans table
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-11-29', 'seah.pm99@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-10-1', 'john@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-10', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-22', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-12', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-26', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-3-3', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-3-15', 'alice@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-11', 'sarah@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-1-29', 'sarah@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-2-28', 'sarah@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-29', 'sarah@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-7-29', 'sarah@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-4', 'jenny@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-4-8', 'jenny@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-5-5', 'jenny@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-5', 'jenny@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-8-8', 'lisa@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-9-9', 'lisa@gmail.com');
-- INSERT INTO `loan` (`date`, `email`) VALUES ('2022-10-13', 'john@gmail.com');

-- INSERT INTO `loan_details` (`id`, `corporate_pass_id`, `loan_id`) VALUES ('1', '1', '1');
-- INSERT INTO `loan_details` (`id`, `corporate_pass_id`, `loan_id`) VALUES ('2', '2', '20');