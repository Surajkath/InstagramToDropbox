# InstagramToDropbox
Stores Instagram files on DropBox

Here I have developed Rest Api's that Takes the flow from Instagram To DropBox.

Configuration Points.
You need to create your own Instagram and DropBox Configurations(basically registering your app).

Steps regarding the storage is as follows
1.Its starts with hitting localhost:8080/../login/signinToInstagram which takes the user to instagram and if gives the permission to your back
It hit back the callback Url you set in configuration(fot me it is localhost:8080/../login/callBackInstagram)

2.After that it hits the Instagram Api to get the user/feeds(You can decide what you want to collect By Extending UserData).

3.Then I am storing the user resource URL's in the session you can do it otherwise(Ideally rest services should be stateless)
And in order to scale you should go by service oriented architecture where you put Instagaram(User data) service and Dropbox(Storage)
service on different servers.

4.Then it redirects to Storage service which gets the resource URl's and starts storing them.


