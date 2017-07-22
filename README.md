[![License](https://img.shields.io/github/license/mashape/apistatus.svg)](https://choosealicense.com/licenses/mit/)
[![Build Status](https://travis-ci.org/baumato/lines-of-code.png?branch=master)](https://travis-ci.org/baumato/lines-of-code)

# lines-of-code
Counts the lines of code of all java files within one directory recursively.

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

## Usage

```
Usage:
 -d (--directory) PATH           : The directory to be recursively scanned.
 -e (--excludeDirs) FOLDER_NAMES : Folder to be omitted
 -v (--verbose) BOOLEAN          : Verbose (default: false)

Example:
 -d (--directory) PATH -e (--excludeDirs) FOLDER_NAMES -v (--verbose) BOOLEAN
```

Example (on Linux):

```
> lines-of-code.jsh -d /path/to/your/directory -e target bin -v
```

Example (every system):

```
> java -jar ./target/lines-of-code.jar -d /path/to/your/directory -e target bin -v
```
