GW=./gradlew $(FLAGS)
GW_PARALLEL=$(GW) --parallel

all: build

clean:
	$(GW_PARALLEL) clean

build:
	$(GW_PARALLEL) build

test:
	$(GW_PARALLEL) test

run:
	$(GW_PARALLEL) run -q -PmainClass="com.hasanguner.aoc2020.Day$${day:-20}"

.PHONY: clean build test run

