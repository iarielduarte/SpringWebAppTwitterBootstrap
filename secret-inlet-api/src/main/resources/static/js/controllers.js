'use strict';

/* Controllers */
angular.module('myApp.controllers', [])
  .controller('IndexCtrl', ['$scope', '$log', 'bookService', function($scope, $log, bookService) {
	  
    	var onInit = function () {
    		
//    		$scope.pStops = [];
//    		
//    		stops.lng = -27.451109;
//    		stops.lat = -58.986483;
//    		stops.message = "Test";
//    		$scope.pStops.push({one: stops})
//    		$log.info("json :" + JSON.stringify($scope.pStops[0]));
    		$scope.tadd = false;
    	    $scope.tlist= false;
    	    $scope.tmap= true;
    	    $scope.createdDT = new Date();
    	    $scope.tags = [];
    		$scope.bookmarks = [];
        	bookService.getBookmarks()
        	.then(function successCallback(response){
        		  $scope.bookmarks = response.data._embedded.bookmarks;
        		  //$log.info("Bookmarks List:" + JSON.stringify($scope.bookmarks));
      		}, function errorCallback(response) {
    			$log.error("Error to get bookmarks" + JSON.stringify(response));
            });
    	};  
    	
    	angular.extend($scope, {
	        icons: local_icons
	    });
	  
	  var local_icons = {
		        leaf_icon: {
		            iconUrl: 'img/marker-icon.png',
		            shadowUrl: 'img/marker-shadow.png'
		        }
		    };
	  
	  var json_stops = {
		  one: {
	            lat : 0,
	            lng : 0,
	            message : "",
	            icon: local_icons.leaf_icon
	        }
	  };
	  
	  var markers = [];
      markers.push(
    	{
          lat: -27.452228,
          lng: -58.987202,
          message : "Placa Juan B Busto",
          icon: { iconUrl: 'img/gym.png', iconSize: [50] }
      });
      markers.push(
    	{
          lat: -27.452170,
          lng: -58.986124,
          message : "Figura",
          icon: { iconUrl: 'img/marker-stops.png', iconSize:  [50] }
      });
	  
	  angular.extend($scope, {
		  osloCenter: {
			  zoom: 13,
              lat: -27.4606571,
              lng: -59.0303647
		    },
	        markers: markers
		});
    	
    	$scope.onSubmit = function () {
    		var body = {
    			    "name" : $scope.name,
    			    "author" : $scope.author,
    			    "description" : $scope.description,
    			    "url" : $scope.url,
    			    "type" : $scope.type,
    			    "code" : $scope.code,
    			    "image" : $scope.image,
    			    "category" : $scope.category,
    			    "tags" : $scope.tags,
    			    "createdDT" : $scope.createdDT
    			}
    		bookService.addBookmark(body)
    		.then(function successCallback(response){
      		  	$log.info("Saved Success!");
      		  	onInit();
    		}, function errorCallback(response) {
    			$log.error("Error to save" + JSON.stringify(response));
            });
    	};
    	
    	$scope.add = function(){
    		$scope.tadd = true;
    		$scope.tlist = false;
    	};
    	
    	$scope.hideAdd = function(){
    		$scope.tadd = !$scope.tadd;
    		$scope.tlist = false;
    	};
    	
    	$scope.hideList = function(){
    		$scope.tlist = !$scope.tlist;
    		$scope.tadd = false;
    	};
    	
    	$scope.hideMap = function(){
    		$scope.tmap = !$scope.tmap;
    	};
    	  
    	
    	onInit();
  	
  }]);