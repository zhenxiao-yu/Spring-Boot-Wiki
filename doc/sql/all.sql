drop table if exists `ebook`;
create table `ebook`
(
    `id`           bigint not null comment 'id',
    `name`         varchar(50) comment 'name',
    `category1_id` bigint comment 'category1',
    `category2_id` bigint comment 'category2',
    `description`  varchar(250) comment 'description',
    `cover`        varchar(250) comment 'cover',
    `doc_count`    int comment 'document count',
    `view_count`   int comment 'view count',
    `vote_count`   int comment 'vote count',
    primary key (`id`)
) engine = innodb
  default charset utf8mb4 comment ='ebook';

insert into `ebook` (id, name, description)
values (1, 'Intro to GUN.js',
        'GUN gives you the most powerful digital weapons on the internet: encryption for privacy, and independence through decentralization...');
insert into `ebook` (id, name, description)
values (2, 'How Google Works',
        'Both Eric Schmidt and Jonathan Rosenberg came to Google as seasoned Silicon Valley business executives...');
insert into `ebook` (id, name, description)
values (3, 'Game Programming Golden Rules',
        'Game Programming Golden Rules'' provides indispensable techniques that should be part of every game...');
insert into `ebook` (id, name, description)
values (4, 'JavaScript: The Good Parts',
        'Most programming languages contain good and bad parts, but JavaScript has more than its share of the...');
insert into `ebook` (id, name, description)
values (5, 'Scrum and XP from the Trenches',
        'This book aims to give you a head start by providing a detailed down-to-earth account of how one Swe...');