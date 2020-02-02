# Tell Me What Happen
Or in Indonesian, "Kenape nih anjirr!" <br>

**Lightweight library for catch error globally that cause force close your application and replace "stopping apps dialog" with informative activity**


| screenshot |
| --------- |
|![](https://i.ibb.co/JF8VBFN/art1.png) |

## Download
```groovy
implementation 'com.utsman.tellme:tellme:1.0.0' // not yet approved in jcenter (sabar njir)
```

## Usage
Attach in `onCreate` of activity or application global (setup in 'name' in manifest)
```kotlin
Tellme.setInstance(context)
```

## Customization
### With default tellme activity
```kotlin
Tellme.setInstance(this)
      .setEmail("kristin@gmail.com", "prita@outlook.com") // email address for send report error
      .setColorAccent(R.color.colorAccent) // setup color accent
```

### With custom activity
Create activity with base TellmeActivity()
```kotlin
class CustomExceptionActivity : TellmeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val messageError = getMessageError() // get message error
        val stackTrace = getStackTrace() // get full stacktrace
    }
}
```
And setup in tellme instance
```kotlin
Tellme.setInstance(this)
      .withActivity(CustomExceptionActivity::class.java)
```

## Sample
[sample](https://github.com/utsmannn/tellme/tree/master/app/src/main)

---
```
Copyright 2020 Muhammad Utsman

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
