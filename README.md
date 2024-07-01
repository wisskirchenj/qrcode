# QR Code app

Implemented in the <b>Introduction to Spring Boot with Java</b> Track of hyperskill.org's JetBrain Academy.<br>

Project goal is to implement a QR-Code app that can produce QR codes — 2D barcodes that can store large amounts of data
and are easily read by smartphones. We generate QR Codes programmatically, and integrate them into a web service.

## Technology / External Libraries

- Java 22
- Spring Boot 3.3.1
- Actuator / Web / Validation / DevTools
- com.google.zxing ("Zebra-Crossing") 3.5.3
- Lombok
- Gradle 8.8

## Repository Contents

Sources for all project tasks (5 stages) with tests and configurations.

## Project progress

Project was completed on 01.07.24

19.04.24 Project started and stage 1 completed. Just the controller setup.

30.06.24 Stage 2 completed. Generate a white BufferedImage as QR-Code. Use Spring BufferedImageHttpMessageConverter.

01.07.24 Stage 3 completed. Add query parameters to `/qrcode` endpoint to parameterize image type and size of the image 
square. Use Spring MessageSource to resolve error messages with added validation.

01.07.24 Stage 4 completed. Use Google "Zebra-Crossing" (zxing) to encode QR Codes.

01.07.24 Final Stage 5 completed. Add correction tolerance parameter to QR-Code API.