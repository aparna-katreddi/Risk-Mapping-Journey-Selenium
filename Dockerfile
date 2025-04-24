# Use OpenJDK 23 as the base image
FROM openjdk:23

# Set the environment variables for JAVA_HOME and PATH
ENV JAVA_HOME=/usr/local/openjdk-23
ENV PATH=$JAVA_HOME/bin:$PATH

# Set the working directory for the project (adjust if needed)
WORKDIR /RISK-MAPPING-JOURNEY-SELENIUM

# Copy your project files into the container
COPY . /RISK-MAPPING-JOURNEY-SELENIUM

# Set the default command to run Maven build
CMD ["mvn", "clean", "test"]

