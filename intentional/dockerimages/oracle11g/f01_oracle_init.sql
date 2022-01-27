CREATE DIRECTORY ORACLE_DUMP as '/data';

create user foodmart identified by oracle;
create user frenchelectricity identified by oracle;
create user frenchelectricityext identified by oracle;
create user covid_weekly identified by oracle;
create user ssb_flight identified by oracle;

grant all privileges to foodmart;
grant all privileges to frenchelectricity;
grant all privileges to frenchelectricityext;
grant all privileges to covid_weekly;
grant all privileges to ssb_flight;

grant read, write on directory oracle_dump to system;
grant read, write on directory oracle_dump to foodmart;
grant read, write on directory oracle_dump to frenchelectricity;
grant read, write on directory oracle_dump to frenchelectricityext;
grant read, write on directory oracle_dump to covid_weekly;
grant read, write on directory oracle_dump to ssb_flight;