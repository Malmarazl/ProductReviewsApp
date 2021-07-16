# ProductReviewsApp

Application to show products, details products, and reviews for each product.

# About the application.
- The application use MVVM pattern for the project instead of using a normal MVP pattern, I think observers are a faster way to communicate between views and allow the developer to control all the logic data into the ViewModel to return clean data to the view.
- The application has a MainActivity that contains a frame layout where I show the fragments (HomeFragment and DetailFragment) in my opinion, work with one Activity and Fragments reduces the possibility of losing data. (Of course, there is a SplasScreen Activity too, but it only appears for a few seconds)
- The connections with the server work with Retrofit library and Gson for parsing, about this point, I had to use a static IP for the API calls, I have never work with docker before and that one was the best approach that I was able to find, that means that would be necessary to modify the paths for the API calls
(for reviews and products) otherwise, the app will show an error screen always. (In addition, I had to include a manifest param to allow "HTTP" calls)
- Added Glide to load images
- Each functionality was developed on an independent branch to be merged later into main.


