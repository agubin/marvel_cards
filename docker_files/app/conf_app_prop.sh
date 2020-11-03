#!/bin/bash

driver="postgresql"
db_host="db"
db_port="5432"
db_name="postgres"
db_user="postgres"
db_pswd="12345"
schema="https"
server_host="localhost"
server_port="8080"
files_path="\/home\/fileStorage\/"

item1="spring.jpa.hibernate.ddl-auto"
reg1="$item1=.*"
sub1="$item1=none"

# driver="postgresql"
# host="db"
# port="5432"
# db_name="postgres"

item2="spring.datasource.url"
reg2="$item2=jdbc:[a-z]*:\/\/[a-z]*:[0-9]*\/[a-z]*"
sub2="$item2=jdbc:$driver:\/\/$db_host:$db_port\/$db_name"

# db_user="postgres"

item3="spring.datasource.username"
reg3="$item3=.*"
sub3="$item3=$db_user"

# db_pswd="12345"

item4="spring.datasource.password"
reg4="$item4=.*"
sub4="$item4=$db_pswd"

# schema="https"
# host="localhost"
# port="8080"

item5="schema"
reg5="$item5=.*"
sub5="$item5=$schema"

item6="host"
reg6="$item6=.*"
sub6="$item6=$server_host"

item7="server.port"
reg7="$item7=.*"
sub7="$item7=$server_port"

item8="files.path"
reg8="$item8=.*"
sub8="$item8=$files_path"

sed -i "s/$reg1/$sub1/g" application.properties
sed -i "s/$reg2/$sub2/g" application.properties
sed -i "s/$reg3/$sub3/g" application.properties
sed -i "s/$reg4/$sub4/g" application.properties
sed -i "s/$reg5/$sub5/g" application.properties
sed -i "s/$reg6/$sub6/g" application.properties
sed -i "s/$reg7/$sub7/g" application.properties
sed -i "s/$reg8/$sub8/g" application.properties
