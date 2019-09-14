ImageApp
=======

**Developed by:[Prashanth Ramakrishnan](prashanth_r03@yahoo.co.in)**

**Features**
- Uses MVP pattern, dagger for dependency injection and RxJava2
- I decided to use Java, although kotlin would have been nice as well (may be I will create a branch and convert this to Kotlin), ask
 me why I decided to use Java in case I get selected 😉
- Fetches the image details via the specified API endpoint from Shutterstock and shows the image list in a recycler 
view
- The image is loaded within a Constraint layout, whose width and height is set dynamically to maintain the aspect ratio.
You can see this in the presenter (ImageDetailsPresenter) class
- Includes unit tests for the presenter and **instrumentation** tests for the app flow. Run the instrumentation test via the gradle command
or via the helper that Android studio provides. I have written the instrumentation for both failure and success cases.
- Set up key signing and provide your relevant signing configs if you need to run the app in release mode, I haven't done this.

Refer [here](https://gist.github.com/jemshit/767ab25a9670eb0083bafa65f8d786bb) for proguard rules.

**Note**
- Assumption is that you are connected to the internet to use this app, I preferred not to write boiler plate code to check
connectivity.
- The app when opened by default searches for query as **music** under the **Abstract** category.
- Provide your client ID and client secret from your Shutterstock developer account in the top level build.gradle
- As discussed on the email, I decided not to implement pagination (which could have been achieved using the new Android Jetpack libraries).
- The API has a set category for Images to be "Abstract", and any query for search runs on this category. You can change the category
in the top level build.gradle
- The maximum number of items per page is also defined in the Constants class, right now it is 30(which is the maximum per page from Shutterstock)
- Styling and animations are minimum, I used Android CardView to show the list screen, everything is pretty straight-forward
- I decided not to invest too much on fancying the UI, the image is loaded using Glide, an helper library
- There is no database in this application, data is shown as is from the API calls!
- Tested on Motorola G4 running Android 7.0, not tested on the Android Emulator (I believe it eats up precious RAM on my Mac). I'm a person 
who tests on a actual device!
- Developed on Android Studio version 3.5
- Some example query strings that you can search under the Category **Abstract** are 
    - music
    - art
    - health
    - work
- Please note to give some time in between searches, since it is a free Shutterstock account, if an immediate search is given the server returns
a 500 error (strange).
- All errors are shown as a Snackbar message.

**Open source libaries used**
- **[Dagger2](https://github.com/google/dagger)**
- **[RxJava2](https://github.com/ReactiveX/RxJava)**
- **[RxAndroid](https://github.com/ReactiveX/RxAndroid)**
- **[Retrofit2](https://github.com/square/retrofit)**
- **[OkHttp3](https://github.com/square/okhttp)**
- **[Glide](https://github.com/bumptech/glide)**
- **[Gson](https://github.com/google/gson)**
- **[Timber](https://github.com/JakeWharton/timber)**
- **[Project Lombok](https://projectlombok.org)**
- **[ButterKnife](https://github.com/JakeWharton/butterknife)**
- **[Robotium](https://github.com/RobotiumTech/robotium)**
- **[MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)**
- **[Commons-io](https://commons.apache.org/proper/commons-io/)**

### License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.