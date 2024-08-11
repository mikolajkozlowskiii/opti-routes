export interface ILocationInfo {
    name: string;
    address: string;
    latitude: number;
    longitude: number;
  }
  
 export interface ILocation {
    id: number;
    locationInfo: ILocationInfo;
    numOfLikes: number;
  }
  
 export interface IUserRating {
    rate: number;
    user_email: string;
    rated_at: string;
  }
  
 export interface IUserRatings {
    userRatingResponse: IUserRating[];
    average_rating: number;
  }
  
 export interface IStep {
    location: ILocation;
    step: number;
  }
  
 export interface IAuthor {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
    roles: string[];
  }
  
  export interface IRouteDetails {
    id: number;
    description: string;
    author: IAuthor;
    usersRatings: IUserRatings;
    guideUsersRatings: IUserRatings;
    steps: IStep[];
    tags: {name: String}[];
    created_at: string;
    distance_in_meters: number;
    time_in_mili_seconds: number;
    saved_times: number;
    is_public: boolean;
  }

  export interface ITag {
    name: string;
  }

  export interface IRouteRatedDetails {
    routeDetails: IRouteDetails;
    userWhoRated: IAuthor;
    rated_at: string;
    rate: number;
  }

  export interface IRouteSavedDetails {
    routeDetails: IRouteDetails;
    userWhoSaved: IAuthor;
    saved_at: string;
  }

