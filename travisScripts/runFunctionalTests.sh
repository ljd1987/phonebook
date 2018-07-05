#!/bin/bash

docker exec phonebook_tests_1 newman run "/etc/newman/phonebookTests.postman_collection.json"