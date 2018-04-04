#/bin/bash

build_dep()
{
    local DEP_NAME=$1
    local DEP_PATH=$2
    local REACT_NATIVE_LIB_DIR=$3
    local OUTPUT_DIR=$4
    local GRADLE_SCRIPT=$5

    echo ------------Begin build dependency----------------
    echo $DEP_NAME
    echo $DEP_PATH
    echo $REACT_NATIVE_LIB_DIR
    echo $OUTPUT_DIR
    echo $GRADLE_SCRIPT
    echo ==================================================

    cd $DEP_PATH

    sed -e "s/compileSdkVersion .*/compileSdkVersion 25/" -e "s/buildToolsVersion \".*\"/buildToolsVersion \"25.0.3\"/" build.gradle > build.gradle.new
	mv  build.gradle.new build.gradle

    echo "allprojects {
        repositories {
            maven {
                url \"$REACT_NATIVE_LIB_DIR\"
            }
            jcenter()
            mavenCentral()
            maven {
                url 'https://maven.google.com/'
                name 'Google'
            }
        }
    }" >> build.gradle

    echo "buildscript {
        repositories {
            jcenter()
            mavenCentral()
        }

        dependencies {
            classpath 'com.android.tools.build:gradle:2.3.3'
        }
    }" >> build.gradle

    gradle assembleRelease

    DIR_NAME=${PWD##*/}

    cp $(find build -name "*-release.aar") $OUTPUT_DIR/$DEP_NAME-release.aar

    echo "implementation(name:\"$DEP_NAME-release\", ext:'aar')" >> $GRADLE_SCRIPT
}

set -e

PROJECT_PATH=$(pwd)/$1

OUTPUT_DIR=$PROJECT_PATH/react-native-module
echo "Prepare output dir $OUTPUT_DIR..."
rm -rf $OUTPUT_DIR
mkdir -p $OUTPUT_DIR
GRADLE_SCRIPT=$OUTPUT_DIR/react-native.gradle

if [ "$3" == 'full' ]; then
    echo "Remove react-native node modules for $PROJECT_PATH and reinstall..."
    rm -rf $PROJECT_PATH/node_modules/react-native*
    cd ${PROJECT_PATH}
    npm install
    cd ..
    echo "Reinstall react-native node modules successfully"

    REACT_NATIVE_VERSION=$(npm list react-native | awk -F@ '/react-native/ {print $2}' | tr -d ' ')
    echo "React native version is $REACT_NATIVE_VERSION"

    echo "Analyse dependencies..."
    ANDROID_PROJECT=$(pwd)/$2
    EXTRA_DEPS=$(awk -F\' '/project.*projectDir/ {print $2":"$4}' $ANDROID_PROJECT/settings.gradle)
    echo "Dependencies list:"
    echo $EXTRA_DEPS

    cp $(pwd)/android_build_tasks/react-native.gradle $GRADLE_SCRIPT

    REACT_NATIVE_LIB_DIR="$PROJECT_PATH/node_modules/react-native/android"
    cp -r $REACT_NATIVE_LIB_DIR $OUTPUT_DIR/react-native
    mkdir $OUTPUT_DIR/react-native-deps

    echo "
    android {
        dependencies {
    " >> $GRADLE_SCRIPT

    echo "implementation \"com.facebook.react:react-native:$REACT_NATIVE_VERSION\"" >> $GRADLE_SCRIPT

    for DEP in $EXTRA_DEPS
    do
    DEP_NAME=$(echo $DEP | awk -F: '{print $2}')
    DEP_PATH=$(echo $DEP | awk -F: '{print $3}')

    build_dep $DEP_NAME "$PROJECT_PATH/node_modules/$DEP_PATH" $REACT_NATIVE_LIB_DIR $OUTPUT_DIR/react-native-deps $GRADLE_SCRIPT

    done
    echo "}}" >> $GRADLE_SCRIPT
fi
cd $PROJECT_PATH
echo "Package js files..."
react-native bundle\
  --platform android\
  --dev false\
  --verbose\
  --reset-cache\
  --entry-file App.js\
  --bundle-output $OUTPUT_DIR/index.android.bundle\
  --assets-dest $OUTPUT_DIR/res/
echo "Bundle is $OUTPUT_DIR/index.android.bundle"
exit 0
