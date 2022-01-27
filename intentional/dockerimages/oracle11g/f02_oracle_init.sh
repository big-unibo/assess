# expdp frenchelectricity/oracle@research directory=oracle_dump dumpfile=frenchelectricity.dmp SCHEMAS=frenchelectricity
# expdp frenchelectricityext/oracle@research directory=oracle_dump dumpfile=frenchelectricityext.dmp SCHEMAS=frenchelectricityext
# expdp covid_weekly/oracle@research directory=oracle_dump dumpfile=covid_weekly.dmp SCHEMAS=covid_weekly
# expdp foodmart/oracle@research directory=oracle_dump dumpfile=foodmart.dmp SCHEMAS=foodmart
# expdp ssb_flight/oracle@research directory=oracle_dump dumpfile=ssb_flight.dmp SCHEMAS=ssb_flight
chmod -R 777 /data
ls -las /data
impdp foodmart/oracle@127.0.0.1:1521/xe DIRECTORY=ORACLE_DUMP DUMPFILE=FOODMART.DMP SCHEMAS=foodmart
impdp covid_weekly/oracle@127.0.0.1:1521/xe DIRECTORY=ORACLE_DUMP DUMPFILE=COVID_WEEKLY.DMP SCHEMAS=covid_weekly
impdp frenchelectricity/oracle@127.0.0.1:1521/xe DIRECTORY=ORACLE_DUMP DUMPFILE=FRENCHELECTRICITY.DMP SCHEMAS=frenchelectricity
impdp frenchelectricityext/oracle@127.0.0.1:1521/xe DIRECTORY=ORACLE_DUMP DUMPFILE=FRENCHELECTRICITYEXT.DMP SCHEMAS=frenchelectricityext
impdp ssb_flight/oracle@research DIRECTORY=ORACLE_DUMP DUMPFILE=SSB_FLIGHT.DMP SCHEMAS=ssb_flight