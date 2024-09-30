docker-compose down

docker build -t backend-reservas_avaliacoes_api : latest ./backend

docker-compose up --build --force-recreate --remove-orphans