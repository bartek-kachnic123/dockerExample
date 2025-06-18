

This Docker image is based on **Ubuntu 24.04** and includes the following software:

- **Python 3.10**
- **Java 8 (OpenJDK)**
- **Kotlin 2.1.20**
- **Gradle 8.13**

## Docker Hub Image

You can pull the image from Docker Hub using the following URL:

[**Docker Hub: ubuntu-python-java-kotlin-gradle**](https://hub.docker.com/r/bartekk123/image-python-java8-kotlin)

1. **Pull the Docker image from Docker Hub**:
   

   ```bash
   docker pull bartekk123/image-python-java8-kotlin:latest
2. **Check versions**:
   

   ```bash
   python3.10 --version
   java -version
   kotlin -version
   gradle -v
