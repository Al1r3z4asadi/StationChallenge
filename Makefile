.DEFAULT_GOAL := up

up:
	docker-compose up --build -d

down:
	docker-compose down

stop: down

logs:
	docker-compose logs -f

ps:
	docker ps

build:
	docker-compose build

clean:
	docker-compose down -v --rmi all

.PHONY: up down stop logs ps build clean