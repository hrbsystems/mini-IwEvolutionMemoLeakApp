
    (function() {

        var app = angular.module('myApp', ['ngSanitize']);

        app.controller('IwHtmlMultiListFormCtrl', function($scope, $http){
             
            // Don't remove or change the line bellow.
            // It is used as template do inject the real variables Data Model.
            // The Data Model is a simple json with the pairs (variable name/ variable value).
            // All values are Strings.
            // All variable names have the prefix iwvar_
            // Ex: {ïwvar_eventdate: "10/10/2014", iwvar_quadroclinico:"" ...}
            $scope.variables = {};
            
            // this callback is better than other.
            // It update only result sub set of variables.
            $scope.callback = function(result) {
                for (var prop in result) {
                    if ($scope.variables.hasOwnProperty(prop)) {
                        $scope.variables[prop] = result[prop];
                    }
                }
                $scope.$apply();
            };

            $scope.javaCall = function () {
                alert("javaCall was called.");
                var win = window;
                win.javaCodeCaller($scope.variables, $scope.callback);
                alert("TESTE");
            };
            
            $scope.teste = function teste() {
                alert("TESTE ANGULAR JS");
            };
            
        });

    })();
    
    
    var getIwVariableValues = function() {
        var appElement = document.querySelector('[ng-app=myApp]');
        var scope = angular.element(appElement).scope();
        return scope.variables;
    };
    
    var getAngularScope = function() {
        var appElement = document.querySelector('[ng-app=myApp]');
        return angular.element(appElement).scope();
    };
    
    var setIwVariableValues = function (varMap) {
        var scope = getAngularScope();
        scope.$apply(function() {
            scope.variables = varMap;
        });
        scope.$apply();
    };
    
    
    
    
    
