## Status

[![Build Status](https://travis-ci.org/ggajos/ot-clean.svg?branch=master)](https://travis-ci.org/ggajos/ot-clean)

## Motivation

If you are working on multiple projects you probably encounter fact that lots
of garbage is produced. In many cases `clean` target from maven, sbt or any
other buld tool is not enough. This project want to target this issue. By simple
executing `ot-clean` you can get rid of all garbage.

## Usage

Unpack [latest release](https://github.com/ggajos/ot-clean/releases) and add it
to your `path`. You might want to read also [How to efficiently manage PATH variable in Windows](http://ggajos.com/environment-variables-management/).
[Have a look also at series of the blog posts](http://ggajos.com/ot-clean-cleanup-cleanall/).

### .clean.yml

You can create `.clean.yml` file and specify which files/dirs you would like to
delete. Similar to `.gitignore` concept but using well known `yml` file standard.

```
deletes:
 - target
 - subproject/target/logs
 - ../logs
```

#### Wildcards support

Under the hood .clean.yml is using [DirectoryScanner](https://maven.apache.org/shared/maven-shared-utils/apidocs/org/apache/maven/shared/utils/io/DirectoryScanner.html)
and is passing `deletes` list as inclusion pattern. You can specify paths with
wildcards, for example:

```
deletes:
 - "*.log"
 - "subproject/**/logs.txt"
 - "../logs"
```

[Please note that when using wildcards, double quote have to be used](http://ggajos.com/ot-clean-yaml-alias-nodes/)

#### Run

Go to directory you want to perform clean and type `ot-clean`. No worries,
by default it is going to display only information what could be cleaned up.
No data will be lost. In order to actually remove files you have to provide `-d`
argument:

```
ot-clean -d
```

#### Arguments

You can see list of all supported arguments for current version [here](https://github.com/ggajos/ot-clean/blob/master/src/main/resources/ot-clean/help.txt).

## Roadmap

### Versions

* ✓ 0.1 - Clean maven project in current directory without using maven
* ✓ 0.2 - Recurrence parameter `r`
* ✓ 0.3 - Add support to delete custom directories via .clean.yml
* ✓ 0.4 - Support wildcards

### Ideas

* Preprocess yaml file so double quotes are no longer needed in paths list.