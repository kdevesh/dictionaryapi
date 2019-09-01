# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table dictionary (
  id                            integer auto_increment not null,
  word                          varchar(255),
  constraint uq_dictionary_word unique (word),
  constraint pk_dictionary primary key (id)
);


# --- !Downs

drop table if exists dictionary;

