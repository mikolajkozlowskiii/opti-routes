//package com.wroclawroutes.routes.controllers;
//
//import com.wroclawroutes.app.dto.ApiResponse;
//import com.wroclawroutes.routes.service.UserSavedRoutesService;
//import com.wroclawroutes.security.userdetails.CurrentUser;
//import com.wroclawroutes.security.userdetails.UserDetailsImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/v1/users/")
//@RequiredArgsConstructor
//public class UserSavedRouteController {
//    private final UserSavedRoutesService userSavedRoutesService;
//
//    @GetMapping("/{email}/routes")
//    public ResponseEntity<ApiResponse> getSavedRoutes(@PathVariable Long routeId,
//                                                 @CurrentUser UserDetailsImpl currentUser){
//            return null;
//    }
//
//    @PutMapping("/{email}/routes-ratings/{routeId}")
//    public ResponseEntity<ApiResponse> saveRoute(@PathVariable Long routeId,
//                                                 @CurrentUser UserDetailsImpl currentUser){
//        return ResponseEntity.ok(userSavedRoutesService.save(currentUser, routeId));
//    }
// // TODO change it for put to like and dislike and based on that make like or unlike, bacause deletampping should be for deleting like or unlike
//    @DeleteMapping("/{email}/routes-ratings/{routeId}")
//    public ResponseEntity<ApiResponse> unSaveRoute(@PathVariable Long routeId,
//                                                 @CurrentUser UserDetailsImpl currentUser){
//        return ResponseEntity.ok(userSavedRoutesService.unSave(currentUser, routeId));
//    }
//}
