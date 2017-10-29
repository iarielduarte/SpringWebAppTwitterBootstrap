'use strict';

/* Services */

angular.module('myApp.services', [])
	.service('bookService', ['$http', function($http) {
		this.getBookmarks = function () {
		    return $http.get("api/bookmarks/", { headers: {  "Accept": "application/json", "Content-Type": "application/json" }});
		 }
		
		this.addBookmark = function (data) {
		    return $http.post("api/bookmarks",data);
		 }
		
		this.getMarkers = function () {
		    return $http.get("api/markers/", { headers: {  "Accept": "application/json", "Content-Type": "application/json" }});
		 }
	}])