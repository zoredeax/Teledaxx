#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "ERROR: $*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

# For Cygwin, ensure paths are in UNIX format before anything is touched.
if ${cygwin} ; then
    [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
    [ -n "$GRADLE_HOME" ] && GRADLE_HOME=`cygpath --unix "$GRADLE_HOME"`
fi

# Attempt to find java
if [ -z "$JAVA_HOME" ] ; then
    JAVA_EXE=`which java 2>/dev/null`
    if [ -z "$JAVA_EXE" ] ; then
        die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVA_EXE="$JAVA_HOME/bin/java"
fi

# Read all command line arguments
ARGS="$@"

# Set script variables
GRADLE_WRAPPER_PROPERTIES_PATH="$APP_HOME/gradle/wrapper/gradle-wrapper.properties"
GRADLE_WRAPPER_JAR_PATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Read wrapper properties
if [ -f "$GRADLE_WRAPPER_PROPERTIES_PATH" ]; then
    while IFS= read -r line; do
        case "$line" in
            distributionUrl=*)
                distributionUrl="${line#*=}"
                ;;
        esac
    done < "$GRADLE_WRAPPER_PROPERTIES_PATH"
fi

# Construct GRADLE_USER_HOME
if [ -z "$GRADLE_USER_HOME" ]; then
    GRADLE_USER_HOME="$HOME/.gradle"
fi

# Construct the distribution path
if [ -n "$distributionUrl" ]; then
    # Extract the distribution base name and version
    distributionName=$(basename "${distributionUrl}")
    distributionNameWithoutExt="${distributionName%.*}"
    distributionVersion=$(echo "$distributionNameWithoutExt" | sed -n 's/.*-\([0-9].*\)/\1/p')
    distributionBaseName=$(echo "$distributionNameWithoutExt" | sed -n 's/\(.*\)-[0-9].*/\1/p')

    if [ -n "$distributionVersion" ] && [ -n "$distributionBaseName" ]; then
        # Create a hash of the distribution URL to use as a directory name
        if command -v md5sum >/dev/null 2>&1; then
            urlHash=$(echo "$distributionUrl" | md5sum | awk '{print $1}')
        elif command -v md5 >/dev/null 2>&1; then
            urlHash=$(echo "$distributionUrl" | md5)
        else
            # Fallback to a simple directory name if no md5 command is available
            urlHash="dist"
        fi
        GRADLE_DIST_PATH="$GRADLE_USER_HOME/wrapper/dists/$distributionNameWithoutExt/$urlHash"
    fi
fi

# Determine the Gradle home
if [ -z "$GRADLE_HOME" ]; then
    if [ -n "$GRADLE_DIST_PATH" ]; then
        GRADLE_HOME="$GRADLE_DIST_PATH"
    else
        # Fallback to a hardcoded path if distributionUrl is not set
        GRADLE_HOME="$APP_HOME"
    fi
fi

# Add the wrapper jar to the classpath
CLASSPATH="$GRADLE_WRAPPER_JAR_PATH"

# Prepend the Gradle launcher JAR to the classpath
if [ -n "$GRADLE_HOME" ]; then
    # Find the Gradle launcher JAR
    GRADLE_LAUNCHER_JAR=$(find "$GRADLE_HOME" -name "gradle-launcher-*.jar" | head -n 1)
    if [ -n "$GRADLE_LAUNCHER_JAR" ]; then
        CLASSPATH="$GRADLE_LAUNCHER_JAR:$CLASSPATH"
    fi
fi

# Execute Gradle
exec "$JAVA_EXE" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
