1. Tech stack: Java21, H2 database & spring boot : batch 5.
2. Sample text file & batch out file path : src/main/resources
3. Input file name : small.in and output file: sentence_out.xml and small-out.csv
4. ParserController.java
    a. handled request and jobLaunch of batch with below url,
       . url for text to xml : http://localhost:8080/parser/texttoxml
       . url for text to csv : http://localhost:8080/parser/texttocsv
5. ParserBatchConfig.java:
       . Execute job text to xml file.
       . read, processor, writer, steps and job are configed.
7. TextToCsvParserBatchConfig
       . Execute job text to csv file.
       . reader, processor, writer, steps and job are configed. 
------------------------------------------------------------------------------

1. Checkout this application in your local system and import as existing source project.
2. Run main class : TextParserApplication.java as spring boot applicaiton.
3. H2 database console opne in browser using link: http://localhost:8080/h2-console
       Jdbc url: jdbc:h2:mem:testdb
       username: admin
       password: amdin   
4. In application.propeties file mention database connetion details and spring job enabled as false.

   
------------------############################################ TEXT TO CSV NEW PROJECT LINK WORKING WITH "large.in" file ##############################################-----------------------

New project Access link : https://github.com/gurubirajdar/TextParserToCsv

1. Checkout this application in your local system ( https://github.com/gurubirajdar/TextParserToCsv.git )  and import as existing source project.
2. Run main class : TextParserToCsvApplication.java as spring boot applicaiton.
3. After successfully run spring boot application in browser hit below url,
    http://localhost:8080/batch/csv
4. large-out.csv file generated "src/main/resources/large-out.csv"
5. large.in file read, parse and write csv file larg-out.csv file. 

   
