[![License](https://img.shields.io/github/license/mashape/apistatus.svg)](https://choosealicense.com/licenses/mit/)
[![Build Status](https://travis-ci.org/baumato/lines-of-code.png?branch=master)](https://travis-ci.org/baumato/lines-of-code)
[![Sonar Quality Gate](https://sonarcloud.io/api/project_badges/quality_gate?project=de.baumato%3Alines-of-code&branch=master)](https://sonarcloud.io/dashboard/index/de.baumato%3Alines-of-code)

# lines-of-code
Counts the lines of code of all java files within one directory recursively.
This application either counts physical lines of code or the source lines of code (without the empty lines
and without the comments).
Before counting the lines the java files get parsed and formatted to ensure comparability between projects. 

## Requirements

Java 8

## Build

- Clone or [download this repository](https://github.com/baumato/lines-of-code/archive/master.zip).
- Build on Linux 

```
> mvnw clean package
```
- Or build on Windows

```
> mvnw.cmd clean package
```
## Download

Download .jsh (directly executable on linux) or .jar file from [release folder](https://git.io/v7kjZ).


## Usage

```
Usage:
 -X (--debug) BOOLEAN                  : Produce execution debug output (default: false)
 -cm (--calculation-mode) [LOC | SLOC] : Mode of calculation, either loc or sloc (w/o empty lines and comments) (default: LOC)
 -d (--directory) PATH                 : The directory to be recursively scanned.
 -e (--excludeDirs) FOLDER_NAMES       : Folder to be omitted
 -v (--verbose) BOOLEAN                : Verbose (default: false)

Example:
 -X (--debug) BOOLEAN -cm (--calculation-mode) [LOC | SLOC] -d (--directory) PATH -e (--excludeDirs) FOLDER_NAMES -v (--verbose) BOOLEAN
```

Example (on Linux):

```
> lines-of-code.jsh -d /path/to/your/directory -e target bin -cm sloc
```

Example (every system):

```
> java -jar ./target/lines-of-code.jar -d /path/to/your/directory -e target bin -cm sloc
```
