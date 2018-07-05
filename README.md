# Phonebook [![Build Status](https://travis-ci.org/ljd1987/phonebook.svg?branch=master)](https://travis-ci.org/ljd1987/phonebook)
simple phonebook REST API

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.ljd.hackajob%3Aphonebook&metric=coverage)](https://sonarcloud.io/component_measures?id=com.ljd.hackajob%3Aphonebook&metric=coverage)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=com.ljd.hackajob%3Aphonebook&metric=bugs)](https://sonarcloud.io/project/issues?id=com.ljd.hackajob%3Aphonebook&resolved=false&types=BUG)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.ljd.hackajob%3Aphonebook&metric=code_smells)](https://sonarcloud.io/project/issues?id=com.ljd.hackajob%3Aphonebook&resolved=false&types=CODE_SMELL)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=com.ljd.hackajob%3Aphonebook&metric=vulnerabilities)](https://sonarcloud.io/project/issues?id=com.ljd.hackajob%3Aphonebook&resolved=false&types=VULNERABILITY)

[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=com.ljd.hackajob%3Aphonebook&metric=sqale_rating)](https://sonarcloud.io/component_measures?id=com.ljd.hackajob%3Aphonebook&metric=Maintainability)
[![Reliability](https://sonarcloud.io/api/project_badges/measure?project=com.ljd.hackajob%3Aphonebook&metric=reliability_rating)](https://sonarcloud.io/component_measures?id=com.ljd.hackajob%3Aphonebook&metric=Reliability)
[![Security](https://sonarcloud.io/api/project_badges/measure?project=com.ljd.hackajob%3Aphonebook&metric=security_rating)](https://sonarcloud.io/component_measures?id=com.ljd.hackajob%3Aphonebook&metric=Security)

## Model

There are two resources in this simple phone book api: `Contact` and `PhoneNumber`.

A `Contact` has the following JSON representation:

```json
{
    "id" : "e1300cbc-e73f-4fb3-a0a8-298e8a577802",
    "firstName" : "John",
    "lastName" : "Smith",
    "links" : {
        "phoneNumbers" : "api/v1/contacts/e1300cbc-e73f-4fb3-a0a8-298e8a577802/phonenumbers"
    }
}
```

A `PhoneNumber` has the following JSON representation:

```json
{
    "type" : "iPhone",
    "number" : "07875236355",
    "links" : {
        "contact" : "api/v1/contacts/e1300cbc-e73f-4fb3-a0a8-298e8a577802"
    }
}
```

- A `Contact` is uniquely identified by `{contactId}` (its `id` field)
- Only the `firstName` field of a Contact is required.
- Both `firstName` and `lastName` of a `Contact` can be updated (but only the `lastName` can be removed)
- A `Contact` can have **0 or more `PhoneNumbers`**
- A `PhoneNumber` is uniquely identified by `{contactId}{type}`
- A `PhoneNumber` is associated with **exactly 1 `Contact`**
- Both the `type` field and `number` field are required on a `PhoneNumber`
- Only the `number` field of a `PhoneNumber` can be updated

## Build Pipeline

Commits will trigger a Travis CI build that:

- builds the `war` file and runs the `junit` tests
- integrates with SonarCloud for source code analysis
- builds a Docker image for the service
- starts a container using the built image, (as well as a mongoDB container and a test container) using `docker-compose`
- runs some functional tests against the running service container (using `newman` to execute a `postman` collection)
- if the tests pass, the Docker image for the service is pushed to Docker hub

## Requirements

To run the service locally, you will need `docker` and `docker-compose` available to your shell, and you need to be able to `docker pull` images from Docker Hub.

To build the project locally, you need to have `mvn` available to your shell.

## Starting the service

1. clone the project:

```bash
git clone https://github.com/ljd1987/phonebook.git
```

2. navigate into the project directory

```bash
cd phonebook
```

3. Use the `docker compose` file:

```bash
$ docker-compose up -d
Creating network "phonebook_default" with the default driver
Pulling mongo (mongo:3.4-jessie)...
3.4-jessie: Pulling from library/mongo
2caa28db99eb: Pull complete
42709a8e42f1: Pull complete
d3b637d986d4: Pull complete
87949d323702: Pull complete
a30cf3d091a1: Pull complete
135669413d91: Pull complete
20aa990ded0b: Pull complete
97e45da43fad: Pull complete
75407f6307ed: Pull complete
72a85b37456e: Pull complete
5ef47f4c82ff: Pull complete
Digest: sha256:96f1c9abf452bc22998ffab0cd21f4f4a2f20641e04d114eb07c49c36a2a6cc4
Status: Downloaded newer image for mongo:3.4-jessie
Pulling phonebook (ljd1987/phonebook:)...
latest: Pulling from ljd1987/phonebook
b234f539f7a1: Already exists
55172d420b43: Already exists
5ba5bbeb6b91: Already exists
43ae2841ad7a: Already exists
f6c9c6de4190: Already exists
8b9a5b216aca: Already exists
fae5f5b47aa4: Already exists
6079241900f4: Already exists
c37829784f5a: Already exists
7cf58521af04: Already exists
2d3168c4ef40: Already exists
26f921ca69d1: Already exists
52103b7b942c: Already exists
8f63dfad2021: Already exists
072bd8b5d83b: Already exists
66e6f4509fd9: Pull complete
099bb3e3c89b: Pull complete
9a5f269e5902: Pull complete
Digest: sha256:a55ca980ff0e176d7c16df2fca3c818255059a53f63db08995626379123562b4
Status: Downloaded newer image for ljd1987/phonebook:latest
Pulling tests (postman/newman_alpine33:)...
latest: Pulling from postman/newman_alpine33
53969ec691ff: Pull complete
5539b128bce1: Pull complete
Digest: sha256:78d8cd30c385990f05790e1b98ed2556f1b6a430ec39620bf9c7deef160ecbdb
Status: Downloaded newer image for postman/newman_alpine33:latest
Creating phonebook_mongo_1 ... done
Creating phonebook_phonebook_1 ... done
Creating phonebook_tests_1     ... done
```

4. After a few moments the api should be available.  I am using `docker-machine` and in my case the API documentation is now available from http://192.168.99.100/

![Image of Swagger UI](https://github.com/ljd1987/phonebook/blob/master/images/docs.png?raw=true)

5. You can use the Swagger UI to experiment with the API.  First, you will need to authorize by clicking on the green "Authorize" button in the top right.  The api supports basic authentication, and there is a single test user:

```
username: admin
password: admin
```

6. To use the Swagger UI to experiment with the API, expand the relevant method and click on the "Try it out" button to open a request panel, and then click on "Execute" once you are happy with the request details:

![try it out](https://github.com/ljd1987/phonebook/blob/master/images/tryitout.png?raw=true)

![execute](https://github.com/ljd1987/phonebook/blob/master/images/execute.png?raw=true)

![response](https://github.com/ljd1987/phonebook/blob/master/images/response.png?raw=true)