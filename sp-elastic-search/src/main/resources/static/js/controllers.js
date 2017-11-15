'use strict';

/* Controllers */
angular.module('myApp.controllers', [])
  .controller('IndexCtrl', ['$filter', '$timeout', '$scope', '$log', 'articleService', function($filter, $timeout, $scope, $log, articleService) {
	  
	  	$scope.sizes = 
          [
              { name: "10 Articles", value: 10 },
              { name: "25 Articles", value: 25 },
              { name: "50 Articles", value: 50 },
              { name: "100 Articles", value: 100 },
              { name: "150 Articles", value: 150 }
          ];
	  	
	  	$scope.articles = [];
	  	
	  	$scope.status = [];
		$scope.aggStatus = [];
		$scope.aggCategories = [];
		$scope.aggOutlets = [];
		$scope.aggRegions = [];
		$scope.aggMedias = [];
		$scope.aggCustomers = [];
		$scope.aggGroups = [];
		$scope.cacheItemSelected = null;
		
  	
  		$scope.selectedOption = $scope.sizes[4];
  		$scope.itemsPerPage = 100
	  
    	var onInit = function () {
    		$scope.currentPage = 1;
    		$scope.total = 0;
    		
    		
    		$scope.applyFilter();
    	};      	  
    	
    	var filterStatusBody = function () {
            return {
                "dataGroupIds": [],
                "categories": [],
                "outlets": [],
                "statuses": [],
                "mediaIds": [],
                "countries": []
            };
        }
    	
    	$scope.rechecked = function () {
    		$scope.status = [];
    		if($scope.cacheItemSelected != null){
    			_.forEach($scope.cacheItemSelected, function (item) {  
    				var data = $filter('filter')($scope.aggStatus, { key: item.key })[0]; 
    				$scope.status.push(data) 
    			});
    		}
    		/*$timeout(function () { 
      			var idx = $scope.status.indexOf(item);
                if (idx > -1) 
                	$scope.status.push($scope.cacheItemSelected);
      			 
      		}, 2000); */
      		$log.info("rechecked:" + JSON.stringify($scope.status));
    	 }
    	
    	$scope.selectStatus = function (item, list) {
    		$scope.cacheItemSelected = $scope.status;
      		$scope.applyFilter();
      	};
    	
    	
    	
    	$scope.changeSizePage = function () {
    		$scope.currentPage = 1;
    		$scope.itemsPerPage = $scope.selectedOption.value
    		$scope.applyFilter();
    	 }
    	
    	$scope.previousPage = function () {
    		$scope.currentPage--;
    		$scope.applyFilter();
    	}
    	
    	$scope.nextPage = function () {
    		$scope.currentPage++;
    		$scope.applyFilter();
    	}
    	
    	$scope.filterSelected = function () {
    		$scope.filterBody = filterStatusBody();
    		var filter = [];
    		/*Status filters*/
    		
    		if($scope.status.length > 0){
    			filter = _($scope.status).filter('key').uniqBy('key').value();
    			_.forEach(filter, function (item) {  $scope.filterBody.statuses.push(item.key)  });
    		}
    			
    		
    		  		
    		return $scope.filterBody;
    	}
    	
    	$scope.applyFilter = function () {
    		articleService.searchByFilter($scope.filterSelected(), $scope.currentPage, $scope.itemsPerPage)
        	.then(function successCallback(response){
        		  $scope.articles = response.data.articles;
        		  $scope.total =  response.data.total;
        		  $scope.totalPages = Math.ceil($scope.total / $scope.itemsPerPage);
        		  $scope.aggStatus = response.data.aggregations.humanFiltered.statuses.buckets;
        		  $scope.aggCategories = response.data.aggregations.humanFiltered.categories.buckets;
        		  $scope.aggGroups = response.data.aggregations.humanFiltered.datagroups.buckets;
        		  $scope.aggOutlets = response.data.aggregations.outlets.buckets;
        		  $scope.aggRegions = response.data.aggregations.regions.buckets;
        		  $scope.aggMedias = response.data.aggregations.medias.buckets;
        		  $scope.rechecked();
        		 // $log.info("$scope.articles:" + JSON.stringify($scope.articles));
      		}, function errorCallback(response) {
    			$log.error("Error to get articles" + JSON.stringify(response));
            });
    	 }
    	
    	 
    	onInit();
  	
  }]);