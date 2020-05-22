# ANDROID CODE CHALLEGE #

This README would normally document whatever steps are necessary to get your application up and running.

### How do I get set up? ###

* Requirements
    * Minimum Android Version: Nougat - 7

* Dependencies
  ```
    android {
      ...,
      compileOptions {
          sourceCompatibility JavaVersion.VERSION_1_8
          targetCompatibility JavaVersion.VERSION_1_8
      }
      useLibrary 'org.apache.http.legacy'
      packagingOptions {
          exclude 'META-INF/DEPENDENCIES'
          exclude 'META-INF/NOTICE'
          exclude 'META-INF/LICENSE'
          exclude 'META-INF/LICENSE.txt'
          exclude 'META-INF/NOTICE.txt'
    }
  ```

  ```
    dependencies {
      implementation fileTree(dir: 'libs', include: ['*.jar'])
      implementation 'androidx.appcompat:appcompat:1.0.2'
      implementation 'com.google.android.material:material:1.1.0'
      implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
      androidTestImplementation 'androidx.test.ext:junit:1.1.0'
      androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
      implementation 'org.apache.httpcomponents:httpmime:4.5.6'
      implementation 'org.apache.httpcomponents:httpcore:4.4.10'
      implementation 'com.squareup.picasso:picasso:2.71828'
    }
  ```

* Design Patterns
  * MVC
  * Facade Pattern
  * Repository Pattern
