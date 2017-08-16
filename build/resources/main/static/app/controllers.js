(function(angular) {
  var AppController = function($scope, Todo) {

    // Initialize newTodo object with base properties
    $scope.newTodo = initTodoObj();

    // Get all the todo's stored in our DB
    Todo.query(function(response) {
      $scope.todoList = response ? response : [];
      console.log($scope.todoList);
    });

   // Function to help initialize todo model object
   function initTodoObj() {
    return {
        "message":"",
        "complete":false
    };
   };

    // Add object to internal DB and update UI
    $scope.addTodo = function(todo) {
      new Todo({
        message: todo.message,
        complete: false
      }).$save(function(todo) {
        // Push the object onto array to be displayed in UI
        $scope.todoList.push(todo);
      });
      $scope.newTodo = initTodoObj();
    };

    // Delete a todo
    $scope.deleteTodo = function(todo) {
      todo.$remove(function() {
        $scope.todoList.splice($scope.todoList.indexOf(todo), 1);
      });
    };
  };
  
  AppController.$inject = ['$scope', 'Todo'];
  angular.module("myApp.controllers").controller("AppController", AppController);
}(angular));