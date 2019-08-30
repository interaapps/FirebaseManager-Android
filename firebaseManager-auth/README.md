# FirebaseManager Auth
Handle FirebaseAuth functions easily

## Usage
- declare your wanted Auth System
- add it to the firebaseManager
- call login function or any other Auth methods

### Google Login Example
```java
public class MainActivity extends AppCompatActivity {

    private FirebaseManager firebaseManager;
    private GoogleAuth googleAuth;
    
    private AppCompatButton googleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseManager = new FirebaseManager(this);
        googleAuth = new GoogleAuth(getActivity(), getString(R.string.default_web_client_id));
        
        googleButton = findViewById(R.id.google_button);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { 
                googleAuth.startLogin(new GoogleAuth.LoginCallbacks() {
                    @Override
                    public void onLoginSuccessful(@Nullable AuthResult authResult) { 
                        Toast.makeText(getActivity(), "Welcome " + authResult.getUser().getDisplayName(), Toast.LENGTH_LONG).show(); 
                    }

                    @Override
                    public void onLoginFailed(Exception exception) { 
                        Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
                        
                        Log.e("GoogleAuth", exception.getMessage()); 
                    }
                }); 
            }
        }); 
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseManager.onStart();
    }
}
```