[![License](https://img.shields.io/github/license/mashape/apistatus.svg)](https://choosealicense.com/licenses/mit/)
[![Build Status](https://travis-ci.org/baumato/lines-of-code.png?branch=master)](https://travis-ci.org/baumato/lines-of-code)

# lines-of-code
Counts the lines of code of all java files within one directory recursively.

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
> java -jar ./target/lines-of-code*-shaded.jar -d /path/to/your/directory
```
