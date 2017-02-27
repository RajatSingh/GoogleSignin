# GoogleSignin
GoogleSignin is a library which makes working with Google signin easier.
Library is still in development so more features will be added soon.


<b>Features:</b>
<ul>
  <li>Google SignIn</li>
  <li>Fetch Person Details(Complete user profile information)</li>
  <li>Share on G+</li>
</ul>

<b>Getting started:</b><br>

<ul>
  <li>First of all, you need to register you application with google(https://console.developers.google.com/project)</li>
  <li>Add googlelibs libs to your project as module.</li>
  <li>Add below line to the bottom of app.gradle of your project<br>
<b>apply plugin: 'com.google.gms.google-services'</b></li>
<li> Add below line under <b>dependencies{..}</b> tag of app.gradle of your project
<br>
<b>compile project(path: ':googlelibs')</b></li>
<li>Implement GoogleSignCallback in your activity or fragment.</li>
<li>Intialize GoogleClient with line mentioned below<br>
<b>
GoogleSignInAI mGoogleSignInAI = GoogleSignInAI.getInstance(this,1001,this,GOOGLE_WEB_CLIENT_ID,GOOGLE_WEB_CLIENT_SECRET,GoogleSignInAI.LOGIN_PURPOSE.LOGIN_FETCH_CONTACT.ordinal());
<br>mGoogleSignInAI.doSignIn();// Call login method.
</b>
</li>
<li>Add <b>mGoogleSignInAI.onActivityResult(data);</b> to onActivityResult method of your activity.</li>
</ul>





