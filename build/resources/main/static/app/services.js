(function(angular) {

  var TodoFactory = function($resource) {
      return $resource('/todo/:id', {
        id: '@id'
      }, {
        remove: {
          method: "DELETE"
        }
      });
    };

  TodoFactory.$inject = ['$resource'];
  angular.module("myApp.services").factory("Todo", TodoFactory);
}(angular));