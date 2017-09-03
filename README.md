# Free DNS dynamic IP updating

This program updates A record of a specified domain to currently recognized public IP. I use this script at my weekend location, where it is not possible to get static IP from the Internet provider. The script regulary checks if IP has changed (not the same as A record at freedns.si) and if it is new, it sets A record at the freedns.si accordingly.

## Installation

Run `mvn assembly:assembly`, which will generate you file `target/dynamic-freedns-1.0-jar-with-dependencies.jar`. Run this file or download it from releases.

## Running

Run the program as `java -jar dynamic-freedns-1.0-jar-with-dependencies.jar REPEAT_DELAY_MINUTES DOMAIN DOMAIN_EDIT_PAGE FREEDNS_USERNAME FREEDNS_PASSWORD`.

Parameters:

* repeatDelayMinutes - interval of checking, whether IP needs to be updated
* domain - domain for which you would like to change freedns record
* domainEditPage - URL of edit page at freedns.si for setting a new A record
* freednsUsername - your freedns username
* freednsPassword - your freedns password