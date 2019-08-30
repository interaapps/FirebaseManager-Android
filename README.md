# FirebaseManager
Processes and simplifies Firebase actions like FirebaseAuth.
No more long classes for simple interactions, instead short code and clear activities

## Getting Started
Add jitpack.io to your root build.gradle
```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
        ...
    }
}
```
Then add the dependency in your project build.gradle

$latestVersion = [![](https://jitpack.io/v/Interaapps/FirebaseManager-Android.svg)](https://jitpack.io/#Interaapps/FirebaseManager-Android)

### All modules
```gradle
dependencies {
    ...
    implementation 'com.github.Interaapps:FirebaseManager-Android:$latestVersion'
    ...
}
```
### Only modules you need

**CORE is needed**

```gradle
dependencies {
    ...
    implementation 'com.github.Interaapps.FirebaseManager-Android:firebaseManager-core:$latestVersion'
    ...
}
```
Other modules (implement what you need)

**FirebaseAuth**

[Documentation](https://github.com/interaapps/FirebaseManager-Android/blob/master/firebaseManager-auth/README.md "Documentation")
```gradle
 implementation 'com.github.Interaapps.FirebaseManager-Android:firebaseManager-auth:$latestVersion'
```

## Usage
- declare FirebaseManager in your activity
- call FirebaseManager onStart method
```java
public class MainActivity extends AppCompatActivity {

    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        firebaseManager = new FirebaseManager(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseManager.onStart();
    }
}
```