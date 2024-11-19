# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8084

# The application's jar file
ARG JAR_FILE=build/libs/example-project-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar
ENV GOOGLE_CREDENTIALS='{
                          "type": "service_account",
                          "project_id": "togethery-52df6",
                          "private_key_id": "28996956a94b4c9544352de06654e278c7dead2f",
                          "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDTp2j3GhRIQpJ0\nE7JcVxaME6Rz+24hCCAHTHrh5D38JKqcdWSTs9wrOlW/W1DsbrTEQmEcWL/om27k\nziTP5VIYP7dRUklfl+b8EQ+VdM8DFjkzRrtmS3k3TyDjafaJtIQH0R47UlM5gkxE\nk7mHiGWe/I7SDh/aIP5gUe6HnhVN3VyaumHsKb8ojA8jWyP2kXSKJsbY1E1ITt/B\n0egZhTc7P6i8gvIHkwAfcxeyciSCSkWcVLGNGvLUcu2G1xBDSV1shUmQx2SSb6Fg\nY9YnIBIgzGnWEmMQbhSCPnjV5fR0estr3bviWkXymZKEAw+Lok2TAqlkvPdXAK4f\nC/Qdqx6hAgMBAAECggEAHlq5FA86b26UwG7+un2G70n14OhjT/txVHqkePcZ6Kp9\niXjQltpJri1slMgM4Au0gx0d/knAJCT+j3P9FY/G6VFiAhiyCzKm3um3eXDKK6Ap\nTfBL+gzC8qq3hcXxpKgt800Lb+iLpQjJ1V/2ULjAEkAYX4nFCayHrtqxQePrHEJq\n93KRBrqw/Y8N4yOfqNb0Y5slwazS4jKAH6J+6hPLkHfB2UH326OK2kYgzq2E/LAt\n/+mc+Yny8HuhPXrBd6cseMELAWdzuQnQbC5Cql9Wu7sOEFUT1NJRPMpe8d8d5M76\ncx9VbHTL1vICDdMPzwSRcStNgeBvLGOfnOud8S2/zQKBgQD+ljA7I8A495n5SEnm\nSqaI0Ff067gcxEn9NcBUKyNQynKg+bay2rs+WKEy7Lzcozn3zPzDxbafE+WE8pYm\nXa/sQKKzvSWmHTlR5bPEyI9rxiEb4cCwdswbWAqiqXvp5Yi0VKLD4wFa9xa1k0sU\noq0qEqR20SyYb+ziJrlJp7gucwKBgQDU1DTxAlNcZyVcQglLDzvqz8z8OuIM+I40\nceeCUgzgTh6YqToy+9Un8okkPVnh+OgxQ1ssT40rZwlKJwiW0CV9s8+GpL6clsfa\nJITK4+PGKj9luL+DNeoKZyRmhc3e+zdKz5pkYQNU1Q6Ium6rE4u/FgPAo7CXNMoz\n1j5E5lZFmwKBgG25oXP7xp9G6A/uvhsJVZll4mLnLWUInLMLdadRKFlrx9fliH6x\nkz4dyOw/6G5X02Yf7VkbX8hUNPoF6/0BKA91FCrPfOiJc+j+TWqk3lue+yofNINZ\n0EKAwaS103hNu4utZrFyp2TjVeBQaW2tZ2DYxBWJteTFaJ1X/0flh5ZNAoGAQtsC\nYm8TlqREJS4NU0nOzZ1HTDiHCAIQUcMyMUrrBo8jp1sJkDhxpiHXGM447PZOoiMB\ngfLfarHsRiHq3c026bzRRP99bR0kXI86M63QwFBHKAIYDvSht3DdC2Oe3xvVK6f5\nXOzoqf5fmW3PE+iI7pbQyPPipi0ME6qKVwRwN18CgYEAmb0EoAa1XQDw5e9wemPr\na4tHHQi1npox4btHS9ewoDRW/4fRDJOOIsmbnuMzjV+PFOv77J6GaslRXk56tF/T\n+V8xrPc7WDzJj7AkPe9wtDYHonkLOZgwzDpM+/MSqwqf1Fn1yYXtdh3sD7vsuvGS\nBBs+Z7YPGVB4ZTmF+347DAg=\n-----END PRIVATE KEY-----\n",
                          "client_email": "firebase-adminsdk-6ydyl@togethery-52df6.iam.gserviceaccount.com",
                          "client_id": "113671199475732560013",
                          "auth_uri": "https://accounts.google.com/o/oauth2/auth",
                          "token_uri": "https://oauth2.googleapis.com/token",
                          "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
                          "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-6ydyl%40togethery-52df6.iam.gserviceaccount.com",
                          "universe_domain": "googleapis.com"
                        }'

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]