docker-compose down

docker build -t reservas_avaliacoes_api:lastest .

docker-compose up --build --force-recreate --remove-orphans