create table players (
  player_id int(12) not null auto_increment,
  name varchar(40) not null,
  dateOfBirth date default null,
  nationality varchar(20) default null,
  dominantHand char(1) default 'r',
  points int(4) default 100,
  primary key(player_id)
);

create table tournaments (
  tournament_id int(12) not null auto_increment,
  name varchar(40) not null,
  pointsModifier double not null,
  primary key(tournament_id)
);

create table matches (
  tournament_id int(12) not null,
  match_id int(12) not null auto_increment,
  surface varchar(10) default null,
  indoor varchar(10) default null,
  numberOfSetsToWin int(1) not null,
  player1_id int(12) not null,
  player2_id int(12) not null,
  primary key(match_id),
  foreign key(tournament_id) references tournaments(tournament_id),
  foreign key(player1_id) references players(player_id),
  foreign key(player2_id) references players(player_id)
);

create table scores (
  score_id int(12) not null auto_increment,
  match_id int(12) not null,
  scor1 int(1) default null,
  scor2 int(1) default null,
  primary key(score_id),
  foreign key(match_id) references matches(match_id)
);

alter table scores
add constraint fk_scores foreign key(match_id) references matches(match_id) on delete cascade;

