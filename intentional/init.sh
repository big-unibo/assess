cp .env.example .env
cp src/main/resources/config.example.yml src/main/resources/config.yml
cd ../..
P=$(pwd)
cd -
sed -i "s+\!HOME\!+${P}+g" src/main/resources/config.yml
sed -i "s+\!HOME\!+${P}+g" start.sh
