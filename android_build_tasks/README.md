#How to Use

- Make sure you have install gradle in you path.

	Run `gradle --version` to check.

- Run `./android_build_tasks/generate_aar_libs.sh` from the root dir of ReactNative project.

- By default the script will only package the js bundle, to package all Android platform dependencies, run `./android_build_tasks/generate_aar_libs.sh full`

- The output dir is `react-native-module`, copy all the files in to Android project

	`cp -r react-native-module/* [Android-project]/app/react-native-module/`
