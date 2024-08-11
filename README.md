
# Opti Routes 
This project is a web application designed for outdoor enthusiasts who enjoy creating, sharing, and exploring walking trails. Users can craft their own hiking routes, share them with the community, rate others' routes, and search for routes that meet their specific preferences. The application's main feature is its ability to optimize the order of locations to visit along a created route, minimizing the total time required to complete the trail. This optimization is achieved by solving the Asymmetric Traveling Salesman Problem (ATSP), ensuring that users have the most efficient path for their journey.

## Features
* **Create Custom Routes**: Users can design their own hiking routes, selecting various locations on map.
* **Share and Rate**: Share your created routes with the community and rate the trails made by other users. There are two types of rates, one is based on users rates, and second one is based on rates given by users with guide role (special verified type of user).
* **Search, Sort and Filter**: Find routes that match your personal preferences, such as length, duration, tags, date of creation, ranks.
* **Route Optimization**: Automatically optimize the order of locations to visit on your route, minimizing travel time solving ATSP.
  
## Tech Stack
* **Backend** Java 17, Spring Boot 3.0.5, MySQL 8.0, Grahhoper API, Google OR-Tools
* **Frontend** HTML, CSS, TypeScript 4.7.2, Angular 14.0.7, Mapbox

## Project Structure
* **Backend**
    * generate and validate JWT.
    * authorization and authentication.
    * send confirmation email with link to enable account.
    * functionalities related to users accounts.
    * functionalities related to creating routes (Grahhoper API).
    * solving Asymmetric Traveling Salesman Problem (Google OR-Tools).
* **Frontend**
    * UI for backend functionalities such as sign in and sing up pages, tables for pagination, sorting, route filtering, page for moderators and admins to manage users account.
    * map (Mapbox) to mark the locations you want to include in your route and to display the locations on your saved routes.
    
## Demo



https://github.com/user-attachments/assets/ff1146de-c9e7-455a-8538-e50310fb3d14

In this video there is functionality of creating route by first pressing the button to locate me, so that the map is centred on the geographical location where user is at the moment. Then the route is created by selecting a locations on the map, and filling in a form concerning the description of the route, selecting tags and checking the boxes whether the route should be publicly available and whether it should be optimised, if so, the specific location should be selected as a depot, i.e. the beginning and the end of the route.





https://github.com/user-attachments/assets/a9d28b4a-e3fa-4913-b8e6-85b70d3fc046

This video is a continuation of the previous one, showing the result of the route creation. The locations have new assigned numbers indicating the order to visit. This is the result of the optimisation - solving Asymmetric Traveling Salesman Problem. This combination is faster, than user's initial order of visiting location. The route has been calculated in terms of length and time. Route can be rated or saved as well.

## Test


![Screenshot 2024-07-21 at 17 59 59](https://github.com/user-attachments/assets/9985470f-b4f9-468a-a9ed-f7c9b5a0d6c9)

This is a comparison on specific route of distances between locations. Distances here are equal to distances generated on google maps, time is a little bit diffrent, because of a.o. assumed diffrent speed of hiking.



## Entity Relationship Diagram 

![whiteDiagram (1)](https://github.com/user-attachments/assets/c39d9fbe-cbe4-40f3-9ff1-9e340d94a3f7)




## Room for Improvment
While the application currently works locally, there are several areas for improvement:
1. Containerization: The first step towards enhancing the application's scalability and deployment is to containerize it using tools like Docker. 
2. Microservices Architecture: The next step is to refactor the application into a microservices architecture. This would involve breaking down this monolithic application into smaller, independent services, such as: User Service, Route Service, Email Service, Security Service.

