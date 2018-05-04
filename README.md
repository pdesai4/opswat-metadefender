### **Opswat Metadefender**

> Priyanka Desai
> https://github.com/pdesai4

------

#### Execution:

1. Create a `api-key.txt` file inthe project root directory which contains your api key value.
2. To build the executable jar:
   Run in root directory:
   `./gradlew fatJar` 
   The above command will create an executable jar "build/libs/opswat-metadefender-all-1.0-SNAPSHOT.jar"
3. To run above executable jar:
   `java -jar build/libs/opswat-metadefender-all-1.0-SNAPSHOT.jar [input-file-name]`

#### Environment:

Java 8 and Gradle

#### Working:

- Initially, read the value of the api key from api-key.txt using the `readApiKey()`

- The input file name is taken from the commandline arguement and validated whether the given file path is valid and the file exists or not. Once validated, the SHA-256 of the given file is calculated by `getFileHash()` in `Util.java`. 
- Further a hash lookup against metadefender.opswat.com is performed to see if there are previously cached results for the file by calling `performFileHashLookup()` which takes the file hash as input. The `performFileHashLookup()` sends an HTTP get request using a okhttp Client and on a successful response calls the `scanHashResult()` from `Util.java` to convert the String json response into required format as specified in `ScanHashResult.java`. In case of response failure, null is returned.
- If the hash lookup is successful, the previously scanned result of the file is returned and printed using `printInFormat()`. Otherwise, the file needs to be scanned and this is done by calling `scanFile()`which takes the input file as a parameter and makes a post request using the okhttp Client. On a successful response `jsonToScanResult()` is called from `Util.java` to convert the String json response into required format as specified in `ScanResult.java`. 
- To know when the file scan is completed, `retrieveScanProgress()` is called using the file's data_id and the scan's progress percentage is retrieved after every 1000ms time interval by sending a HTTP get request until the progress percentage is 100. To avoid a long or infinite loop, a `maxWaitTimeInSeconds` variable with a value of 60s is maintained and the value of variable `elapsedWaitTime` is incremented after every 1000ms. If the scan is completed before the value of `elapsedWaitTime` is greater than `maxWaitTimeInSeconds`, the result is printed using `printInFormat()` else the error message for time out is printed on the standard error stream.

