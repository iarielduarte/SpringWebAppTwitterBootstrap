'use strict';

/* Services */

angular.module('myApp.services', [])
	.service('articleService', ['$http', function($http) {
		this.searchByFilter = function (filters, page, size) {
			var url = 'rest/elastic/search/article/all?page='+page+'&size='+size+'&sort=publishedOnDT,desc';
		    return $http.post(url, filters,{ headers: { "Accept": "application/json", "Content-Type": "application/json" }});
		 }
	}])