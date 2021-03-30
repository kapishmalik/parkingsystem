# Parking System

### Project Requirements
 - Java 8
 - Maven 3.x

### Setup
##### LocalSetup
 - Install JDK 1.8. Follow - https://openjdk.java.net/install/
 - Install maven 3.6.3. Follow - https://maven.apache.org/install.html
 - Run following command to execute the code - ./parking_lot path_to_file

##### Dockerized setup
- Build using following command -> docker build -t parking-lot-take-home .
- Copy input file to the project directory
- Run using following command -> docker run -v <absolute path to project directory>:/assignment parking-lot-take-home parking_lot.sh relative_path_to_input_file
 ```Example:   docker run -v /Users/kapish.m/workspace/parkingsystem:/assignment parking-lot-take-home parking_lot.sh input.txt```

### Notes
  - Make sure path for java and maven are set
  - Make sure parking_lot is executable file. If not make it via command => chmod +x parking_lot
  - script parking_lot will also build and run the tests via maven and its output will be piped to file mvn-build-output.txt which will be made in current working directory
  - Dockerized setup may take some time to run as it downloads artifacts. Check mvn-build-output.txt file in the project directory, once you execute docker run command.
  - All source code is present in src/main directory and test in src/test directory

### Assumptions
- First command of input file will always be parking lot creation command
- If any command is invalid or incomplete, it will output "Invalid command"
- It prints `error code: error message` in following scenarios. 
    1. If parking lot is full
    2. If invalid slot number is passed for vacating
    3. If invalid age is passed in search commands (i.e. age <= 0)
 - Refer com.squadstack.parkingsystem.exception.ErrorCode for error codes