# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table defensa (
  id                        bigint auto_increment not null,
  seguimiento_especial      tinyint(1) default 0,
  tipo_autoridad            varchar(255),
  constraint pk_defensa primary key (id))
;

create table documento_defensa (
  id                        integer auto_increment not null,
  uuid_ecm                  varchar(255),
  path_ecm                  varchar(255),
  name_ecm                  varchar(255),
  content_type              varchar(255),
  id_seccion                bigint,
  defensa_id                bigint,
  constraint pk_documento_defensa primary key (id))
;

create table documento_representacion (
  id                        integer auto_increment not null,
  uuid_ecm                  varchar(255),
  path_ecm                  varchar(255),
  name_ecm                  varchar(255),
  content_type              varchar(255),
  id_algo                   bigint,
  defensa_id                bigint,
  constraint pk_documento_representacion primary key (id))
;

alter table documento_defensa add constraint fk_documento_defensa_defensa_1 foreign key (defensa_id) references defensa (id) on delete restrict on update restrict;
create index ix_documento_defensa_defensa_1 on documento_defensa (defensa_id);
alter table documento_representacion add constraint fk_documento_representacion_defensa_2 foreign key (defensa_id) references defensa (id) on delete restrict on update restrict;
create index ix_documento_representacion_defensa_2 on documento_representacion (defensa_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table defensa;

drop table documento_defensa;

drop table documento_representacion;

SET FOREIGN_KEY_CHECKS=1;

