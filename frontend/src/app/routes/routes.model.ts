import { ITag } from "../routes-list/route";

export interface Route {
    description: string;
    is_public: boolean;
    tags: { name: string }[];
    locations_steps: { location: Location; step: number }[];
  }
  
  export interface Location {
    name: string;
    address: string;
    latitude: number;
    longitude: number;
  }

  export interface RouteRequest {
    description: string;
    is_public: boolean;
    is_optimized: boolean;
    locations_steps: { location: Location; step: number }[];
    depot: Location;
    tags: ITag[];
  }