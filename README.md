[![Build Status](https://travis-ci.org/systelab/skillsbase.svg?branch=master)](https://travis-ci.org/systelab/skillsbase)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7ce4e563c45b4d09a975d61bed7d5d50)](https://www.codacy.com/app/alfonsserra/skillsbase?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=systelab/skillsbase&amp;utm_campaign=Badge_Grade)
[![Known Vulnerabilities](https://snyk.io/test/github/systelab/skillsbase/badge.svg?targetFile=pom.xml)](https://snyk.io/test/github/systelab/skillsbase?targetFile=pom.xml)

# Skills Base Systelab project

This project is a simple Skills Base application.


## Getting Started

To get you started you can simply clone the `skillsbase` repository and install the dependencies:

### Prerequisites

You need [git][git] to clone the `skillsbase` repository

You will need [Java™ SE Development Kit 8][jdk-download] and [Maven][maven].

### Clone `skillsbase`

Clone the `skillsbase` repository using git:

```bash
git clone https://github.com/systelab/skillsbase.git
cd skillsbase
```

### Install Dependencies

In order to install the dependencies and generate the Uber jar you must run:

```bash
mvn clean install
```

### Run

To launch the server, simply run with java -jar the generated jar file.

```bash
cd target
java -jar skillsbase-1.0.jar
```

## API

You will find the swagger UI at http://localhost:8080/swagger-ui.html

First generate a token by login as user Systelab and password Systelab. After that authorize Swagger by copying the bearer.


## Docker

### Build docker image

There is an Automated Build Task in Docker Cloud in order to build the Docker Image. 
This task, triggers a new build with every git push to your source code repository to create a 'latest' image.
There is another build rule to trigger a new tag and create a 'version-x.y.z' image

You can always manually create the image with the following command:

```bash
docker build -t systelab/skillsbase . 
```

### Run the container

```bash
docker run -p 8080:8080 systelab/skillsbase
```

The app will be available at http://localhost:8080/swagger-ui.html




[git]: https://git-scm.com/
[maven]: https://maven.apache.org/download.cgi
[jdk-download]: http://www.oracle.com/technetwork/java/javase/downloads


