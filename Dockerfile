FROM eclipse-temurin:21-jdk

LABEL maintainer="Pramod"

WORKDIR /app

# Install required tools
RUN apt-get update && \
    apt-get install -y \
    wget \
    gnupg \
    git \
    maven

# Install Google Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | \
    gpg --dearmor -o /usr/share/keyrings/google-linux.gpg && \
    echo "deb [signed-by=/usr/share/keyrings/google-linux.gpg] http://dl.google.com/linux/chrome/deb/ stable main" \
    > /etc/apt/sources.list.d/google.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable

# Copy pom first
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy project
COPY . .

# Default command
CMD ["mvn","clean","test"]